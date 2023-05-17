package org.example.gameobjects;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.gameobjects.types.ArmorType;
import org.example.gameobjects.types.ObjectType;
import org.example.utils.WorldCoordinates;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents an armor object.
 */
public class Armor extends GameObject {
    private ArmorType armorType;

    /**
     * Constructs an Armor object with the specified armor type and world coordinates.
     *
     * @param armorType       the armor type of the armor object.
     * @param worldCoordinates the world coordinates of the armor object.
     */
    public Armor(ArmorType armorType, WorldCoordinates worldCoordinates) {
        super(ObjectType.ARMOR, worldCoordinates);
        this.armorType = armorType;
        setArmorImage();
    }

    /**
     * Sets the armor image based on the armor type.
     */
    private void setArmorImage() {
        try {
            setStaticImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/sprites/objects/armor/" + armorType.toString().toLowerCase() + ".png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the armor type of the armor.
     *
     * @return the armor type.
     */
    public ArmorType getArmorType() {
        return armorType;
    }

    /**
     * Serializes the armor object to JSON.
     *
     * @return the serialized JSON object.
     */
    public JsonObject serializeArmor() {
        JsonObject json = super.serializeGameObject();

        json.put("armortype", armorType.toString());

        return json;
    }

    /**
     * Deserializes and creates an Armor object from JSON.
     *
     * @param json the JSON object containing the armor data.
     * @return the created Armor object.
     */
    public static Armor deserializeAndCreateArmor(JsonObject json) {
        return new Armor(ArmorType.valueOf((String) json.get("armortype")),
                new WorldCoordinates(json)
        );
    }
}
