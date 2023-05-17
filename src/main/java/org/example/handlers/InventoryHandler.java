package org.example.handlers;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.gameobjects.Armor;
import org.example.gameobjects.Weapon;
import org.example.gameobjects.types.ArmorType;
import org.example.gameobjects.types.WeaponType;
import org.example.utils.WorldCoordinates;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InventoryHandler {
    private static final Logger LOGGER = Logger.getLogger(InventoryHandler.class.getName());
    private Weapon weaponEquipped;
    private Armor armorEquipped;

    public void equipWeapon(Weapon weapon) {
        this.weaponEquipped = weapon;
    }

    public void equipArmor(Armor armor) {
        this.armorEquipped = armor;
    }

    public Weapon getWeaponEquipped() {
        return weaponEquipped;
    }

    public Armor getArmorEquipped() {
        return armorEquipped;
    }

    public JsonObject serializeInventory() {
        JsonObject json = new JsonObject();

        LOGGER.log(Level.INFO, "Serializing players inventory.");

        if (Objects.nonNull(armorEquipped)) {
            json.put("armor", armorEquipped.getArmorType().toString());
        } else {
            json.put("armor", null);
        }

        if (Objects.nonNull(weaponEquipped)) {
            json.put("weapon", weaponEquipped.getWeaponType().toString());
        } else {
            json.put("weapon", null);
        }

        return json;
    }

    public void deserializeAndSetInventory(JsonObject json) {
        String armor = (String) json.get("armor");
        String weapon = (String) json.get("weapon");

        LOGGER.log(Level.INFO, "Deserializing players inventory.");

        if (Objects.nonNull(armor)) {
            armorEquipped = new Armor(ArmorType.valueOf(armor), new WorldCoordinates(1, 1));
        }

        if (Objects.nonNull(weapon)) {
            weaponEquipped = new Weapon(WeaponType.valueOf(weapon), new WorldCoordinates(1, 1));
        }
    }
}
