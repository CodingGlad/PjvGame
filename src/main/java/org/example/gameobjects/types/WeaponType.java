package org.example.gameobjects.types;

public enum WeaponType {
    STEEL_SWORD(20);

    private final int damage;

    WeaponType(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
