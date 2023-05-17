package org.example.gameobjects.types;

/**
 * Represents different types of armor.
 */
public enum ArmorType {
    STEEL(0.65f),
    COPPER(0.8f);

    private final float damageReduction;

    /**
     * Constructs an ArmorType enum with the specified damage reduction.
     *
     * @param damageReduction the damage reduction value
     */
    ArmorType(float damageReduction) {
        this.damageReduction = damageReduction;
    }

    /**
     * Returns the damage reduction value of the armor type.
     *
     * @return the damage reduction value
     */
    public float getDamageReduction() {
        return damageReduction;
    }
}

