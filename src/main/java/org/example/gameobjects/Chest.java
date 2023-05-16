package org.example.gameobjects;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.gameobjects.types.*;
import org.example.utils.WorldCoordinates;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;

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

    public GameObject generateLoot() {
        Random rand = new Random();

        int randomSelection = Math.abs(rand.nextInt() % 3);

        if (randomSelection == 0) {
            return generateWeapon(rand);
        } else if (randomSelection == 2) {
            return generateArmor(rand);
        } else {
            return new Heart(getWorldCoordinates());
        }
    }

    private GameObject generateWeapon(Random rand) {
        int randomSelection = Math.abs(rand.nextInt() % 3);
        if (randomSelection == 0) {
            return new Weapon(WeaponType.GOLDEN_SWORD, getWorldCoordinates());
        } else {
            return new Weapon(WeaponType.STEEL_SWORD, getWorldCoordinates());
        }
    }

    private GameObject generateArmor(Random rand) {
        int randomSelection = Math.abs(rand.nextInt() % 4);
        if (randomSelection == 0) {
            return new Armor(ArmorType.STEEL, getWorldCoordinates());
        } else {
            return new Armor(ArmorType.COPPER, getWorldCoordinates());
        }
    }
}
