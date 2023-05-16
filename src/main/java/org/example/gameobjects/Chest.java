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
    private ChestStateType stateType;

    public Chest(ChestType chestType, ChestStateType chestState, WorldCoordinates worldCoordinates) {
        super(ObjectType.CHEST, worldCoordinates);
        this.chestType = chestType;
        this.stateType = chestState;
        upsertChestClosedImage();
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
        return chestType.getName() + "-" + stateType.getState();
    }

    public ChestType getChestType() {
        return chestType;
    }

    public ChestStateType getStateType() {
        return stateType;
    }

    public void openChest() {
        stateType = ChestStateType.OPENED;

        upsertChestClosedImage();
    }

    public JsonObject serializeChest() {
        JsonObject json = super.serializeGameObject();

        json.put("chesttype", chestType.toString());
        json.put("state", stateType.toString());

        return json;
    }

    public static Chest deserializeAndCreateChest(JsonObject json) {
        return new Chest(
                ChestType.valueOf((String) json.get("chesttype")),
                ChestStateType.valueOf((String) json.get("state")),
                new WorldCoordinates(json));
    }
}
