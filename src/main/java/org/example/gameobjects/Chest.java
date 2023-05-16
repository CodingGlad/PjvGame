package org.example.gameobjects;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.gameobjects.types.ChestStateType;
import org.example.gameobjects.types.ChestType;
import org.example.gameobjects.types.ObjectType;
import org.example.utils.WorldCoordinates;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

public class Chest extends GameObject{
    private ChestType chestType;
//    private ChestStateType stateType;

    public Chest(ObjectType objectType, ChestType chestType, WorldCoordinates worldCoordinates) {
        super(objectType, worldCoordinates);
        if (objectType.equals(ObjectType.CHEST_OPENED) || objectType.equals(ObjectType.CHEST_CLOSED)) {
            this.chestType = chestType;
            upsertChestClosedImage();
        } else {
            throw new IllegalStateException("Attempt to crate object " + objectType.getName() + " as an instance of a chest.");
        }
    }

    private void upsertChestClosedImage() {
        try {
            setStaticImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/sprites/objects/chest/" + getSpriteName() + ".png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSpriteName() {
        return chestType.getName() + "-" + getObjectType().getName();
    }

    public ChestType getChestType() {
        return chestType;
    }

    @Deprecated
    public void openChest() {
//        stateType = ChestStateType.OPENED;

        upsertChestClosedImage();
    }

    public JsonObject serializeChest() {
        JsonObject json = super.serializeGameObject();

        json.put("chesttype", chestType.toString());

        return json;
    }

    public static Chest deserializeAndCreateChest(JsonObject json) {
        return new Chest(
                ObjectType.valueOf((String) json.get("objecttype")),
                ChestType.valueOf((String) json.get("chesttype")),
                new WorldCoordinates(json));
    }
}
