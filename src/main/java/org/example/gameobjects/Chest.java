package org.example.gameobjects;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.gameobjects.types.*;
import org.example.utils.WorldCoordinates;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Random;

/**
 * Represents a chest game object.
 */
public class Chest extends GameObject{
    private ChestType chestType;

    /**
     * Constructs a new Chest object with the specified object type, chest type, and world coordinates.
     *
     * @param objectType      the type of the chest object
     * @param chestType       the type of the chest
     * @param worldCoordinates the world coordinates of the chest
     * @throws IllegalStateException if the object type is not valid for a chest
     */
    public Chest(ObjectType objectType, ChestType chestType, WorldCoordinates worldCoordinates) {
        super(objectType, worldCoordinates);
        if (objectType.equals(ObjectType.CHEST_OPENED) || objectType.equals(ObjectType.CHEST_CLOSED)) {
            this.chestType = chestType;
            upsertChestClosedImage();
        } else {
            throw new IllegalStateException("Attempt to create object " + objectType.getName() + " as an instance of a chest.");
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

    /**
     * Returns the type of the chest.
     *
     * @return the type of the chest
     */
    public ChestType getChestType() {
        return chestType;
    }

    /**
     * Serializes the chest object to a JsonObject.
     *
     * @return the serialized JsonObject of the chest
     */
    public JsonObject serializeChest() {
        JsonObject json = super.serializeGameObject();

        json.put("chesttype", chestType.toString());

        return json;
    }

    /**
     * Deserializes a JsonObject and creates a Chest object.
     *
     * @param json the JsonObject containing the serialized chest data
     * @return the deserialized Chest object
     */
    public static Chest deserializeAndCreateChest(JsonObject json) {
        return new Chest(
                ObjectType.valueOf((String) json.get("objecttype")),
                ChestType.valueOf((String) json.get("chesttype")),
                new WorldCoordinates(json));
    }

    /**
     * Generates a random loot item based on the chest contents.
     *
     * @return a randomly generated loot item as a GameObject
     */
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

    /**
     * Generates a random weapon based on the specified random number generator.
     *
     * @param rand the random number generator
     * @return a randomly generated weapon as a GameObject
     */
    private GameObject generateWeapon(Random rand) {
        int randomSelection = Math.abs(rand.nextInt() % 3);
        if (randomSelection == 0) {
            return new Weapon(WeaponType.GOLDEN_SWORD, getWorldCoordinates());
        } else {
            return new Weapon(WeaponType.STEEL_SWORD, getWorldCoordinates());
        }
    }

    /**
     * Generates a random armor based on the specified random number generator.
     *
     * @param rand the random number generator
     * @return a randomly generated armor as a GameObject
     */
    private GameObject generateArmor(Random rand) {
        int randomSelection = Math.abs(rand.nextInt() % 4);
        if (randomSelection == 0) {
            return new Armor(ArmorType.STEEL, getWorldCoordinates());
        } else {
            return new Armor(ArmorType.COPPER, getWorldCoordinates());
        }
    }

}
