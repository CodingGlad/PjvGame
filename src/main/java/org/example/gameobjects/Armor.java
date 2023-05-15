package org.example.gameobjects;

import org.example.gameobjects.types.ArmorType;
import org.example.gameobjects.types.ObjectType;
import org.example.utils.WorldCoordinates;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Armor extends GameObject {
    private ArmorType armorType;

    public Armor(ObjectType objectType, ArmorType armorType,
                 WorldCoordinates worldCoordinates) {
        super(objectType, worldCoordinates);
        if (objectType.equals(ObjectType.ARMOR)) {
            this.armorType = armorType;
            setArmorImage();
        } else {
            throw new IllegalStateException("Attempted creating object " + objectType.getName() + " as an instance of a armor.");
        }
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
}
