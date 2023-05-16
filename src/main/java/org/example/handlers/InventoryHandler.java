package org.example.handlers;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.gameobjects.Armor;
import org.example.gameobjects.GameObject;
import org.example.gameobjects.Weapon;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InventoryHandler {
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
}
