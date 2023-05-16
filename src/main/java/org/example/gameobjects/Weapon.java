package org.example.gameobjects;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.gameobjects.types.ArmorType;
import org.example.gameobjects.types.ObjectType;
import org.example.gameobjects.types.WeaponType;
import org.example.utils.WorldCoordinates;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Weapon extends GameObject {
    private WeaponType weaponType;

    public Weapon(WeaponType weaponType, WorldCoordinates worldCoordinates) {
        super(ObjectType.WEAPON, worldCoordinates);
        this.weaponType = weaponType;
        setWeaponImage();
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

    public JsonObject serializeWeapon() {
        JsonObject json = super.serializeGameObject();

        json.put("weapontype", weaponType.toString());

        return json;
    }

    public static Weapon deserializeAndCreateWeapon(JsonObject json) {
        return new Weapon(WeaponType.valueOf((String) json.get("weapontype")),
                new WorldCoordinates(json)
        );
    }
}
