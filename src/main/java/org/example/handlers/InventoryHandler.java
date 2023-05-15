package org.example.handlers;

import org.example.gameobjects.Armor;
import org.example.gameobjects.GameObject;
import org.example.gameobjects.Weapon;

import java.util.HashMap;
import java.util.Map;

public class InventoryHandler {
    private static int DEFAULT_INVENTORY_SIZE = 5;
    private static int MAXIMUM_INVENTORY_SIZE = 15;
    private int inventorySize;
    private Weapon weaponEquipped;
    private Armor armorEquipped;
    private Map<Integer, GameObject> content;

    public InventoryHandler() {
        this.inventorySize = DEFAULT_INVENTORY_SIZE;
        content = new HashMap<>();
    }

    public InventoryHandler(int inventorySize) {
        this.inventorySize = inventorySize;
        content = new HashMap<>();
    }

    public void addItem(GameObject item) {

    }

    public void equipWeapon(Weapon weapon) {

    }

    public void equipArmor(Armor armor) {

    }
}
