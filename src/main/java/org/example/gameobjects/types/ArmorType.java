package org.example.gameobjects.types;

public enum ArmorType {
    STEEL(0.65f),
    COPPER(0.8f);

    private final float damageReduction;

    ArmorType(float damageReduction) {
        this.damageReduction = damageReduction;
    }

    public float getDamageReduction() {
        return damageReduction;
    }
}
