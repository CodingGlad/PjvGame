package org.example.gameobjects.types;

/**
 * Enumeration of weapon types.
 */
public enum WeaponType {
    /**
     * Weapon type for a steel sword.
     */
    STEEL_SWORD("steelsword", 8),

    /**
     * Weapon type for a golden sword.
     */
    GOLDEN_SWORD("goldensword", 13);

    private final String name;
    private final int damage;

    /**
     * Constructs a WeaponType with the specified name and damage.
     *
     * @param name   the name of the weapon type.
     * @param damage the damage value of the weapon type.
     */
    WeaponType(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    /**
     * Returns the damage value of the weapon type.
     *
     * @return the damage value.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Returns the name of the weapon type.
     *
     * @return the name of the weapon type.
     */
    public String getName() {
        return name;
    }
}

