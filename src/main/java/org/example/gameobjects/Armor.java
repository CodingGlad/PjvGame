package org.example.gameobjects;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.gameobjects.types.ArmorType;
import org.example.gameobjects.types.ObjectType;
import org.example.utils.WorldCoordinates;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Armor extends GameObject {
    private ArmorType armorType;

    public Armor(ArmorType armorType, WorldCoordinates worldCoordinates) {
        super(ObjectType.ARMOR, worldCoordinates);
        this.armorType = armorType;
        setArmorImage();
    }

    private void setArmorImage() {
        try {
            setStaticImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/sprites/objects/armor/" + armorType.toString().toLowerCase() + ".png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArmorType getArmorType() {
        return armorType;
    }

    public JsonObject serializeArmor() {
        JsonObject json = super.serializeGameObject();

        json.put("armortype", armorType.toString());

        return json;
    }
}
