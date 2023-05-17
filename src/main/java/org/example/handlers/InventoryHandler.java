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

/**
 * The InventoryHandler class is responsible for managing the player's inventory, including equipped weapons and armor.
 */
public class InventoryHandler {
    private static final Logger LOGGER = Logger.getLogger(InventoryHandler.class.getName());
    private Weapon weaponEquipped;
    private Armor armorEquipped;

    /**
     * Equips the specified weapon.
     *
     * @param weapon The weapon to be equipped.
     */
    public void equipWeapon(Weapon weapon) {
        this.weaponEquipped = weapon;
    }

    /**
     * Equips the specified armor.
     *
     * @param armor The armor to be equipped.
     */
    public void equipArmor(Armor armor) {
        this.armorEquipped = armor;
    }

    /**
     * Retrieves the currently equipped weapon.
     *
     * @return The currently equipped weapon.
     */
    public Weapon getWeaponEquipped() {
        return weaponEquipped;
    }

    /**
     * Retrieves the currently equipped armor.
     *
     * @return The currently equipped armor.
     */
    public Armor getArmorEquipped() {
        return armorEquipped;
    }

    /**
     * Serializes the player's inventory to a JsonObject.
     *
     * @return The serialized JsonObject representing the player's inventory.
     */
    public JsonObject serializeInventory() {
        JsonObject json = new JsonObject();

        LOGGER.log(Level.INFO, "Serializing player's inventory.");

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

    /**
     * Deserializes the JsonObject and sets the player's inventory accordingly.
     *
     * @param json The JsonObject containing the serialized player's inventory.
     */
    public void deserializeAndSetInventory(JsonObject json) {
        String armor = (String) json.get("armor");
        String weapon = (String) json.get("weapon");

        LOGGER.log(Level.INFO, "Deserializing player's inventory.");

        if (Objects.nonNull(armor)) {
            armorEquipped = new Armor(ArmorType.valueOf(armor), new WorldCoordinates(1, 1));
        }

        if (Objects.nonNull(weapon)) {
            weaponEquipped = new Weapon(WeaponType.valueOf(weapon), new WorldCoordinates(1, 1));
        }
    }
}

