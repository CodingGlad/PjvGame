package org.example.gameobjects;

import org.example.gameobjects.types.ObjectType;
import org.example.gameobjects.types.WeaponType;
import org.example.utils.WorldCoordinates;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Weapon extends GameObject {
    private WeaponType weaponType;

    public Weapon(ObjectType objectType, WeaponType weaponType, WorldCoordinates worldCoordinates) {
        super(objectType, worldCoordinates);
        if (objectType.equals(ObjectType.WEAPON)) {
            this.weaponType = weaponType;
            setWeaponImage();
        } else {
            throw new IllegalStateException("Attempted creating object " + objectType.getName() + " as an instance of a weapon.");
        }
    }

    private void setWeaponImage() {
        try {
            setStaticImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/sprites/objects/weapon/" + weaponType.getName() + ".png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }
}
