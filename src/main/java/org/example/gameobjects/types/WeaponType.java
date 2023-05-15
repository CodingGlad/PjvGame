package org.example.gameobjects.types;

public enum WeaponType {
    STEEL_SWORD("steelsword", 8);

    private final String name;
    private final int damage;

    WeaponType(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }
}
