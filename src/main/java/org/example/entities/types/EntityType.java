package org.example.entities.types;

/**
 Represents the types of entities in the game.
 Each entity type has a corresponding string representation and various default attributes.
 */
public enum EntityType {
    /**
     * Represents the hero entity type.
     * The string representation is "hero".
     * It has 4 sprites, default damage of 8, default health of 100,
     * default attack speed of 2, and default speed of 4.
     */
    HERO("hero", 4, 8, 100, 2, 4),
    /**
     * Represents the demon entity type.
     * The string representation is "demon".
     * It has 4 sprites, default damage of 13, default health of 45,
     * default attack speed of 2, and default speed of 4.
     */
    DEMON("demon", 4, 13, 45, 2, 4);

    private final String entityString;
    private final int spritesNumber;
    private final int defaultDamage;
    private final int defaultHealth;
    private final int defaulAttackSpeed;
    private final int defaultSpeed;

    /**
     * Constructs an EntityType with the specified attributes.
     *
     * @param entityString      the string representation of the entity type
     * @param spritesNumber     the number of sprites for the entity type
     * @param defaultDamage     the default damage of the entity type
     * @param defaultHealth     the default health of the entity type
     * @param defaulAttackSpeed the default attack speed of the entity type
     * @param defaultSpeed      the default speed of the entity type
     */
    EntityType(String entityString, int spritesNumber, int defaultDamage,
               int defaultHealth, int defaulAttackSpeed, int defaultSpeed) {
        this.entityString = entityString;
        this.spritesNumber = spritesNumber;
        this.defaultDamage = defaultDamage;
        this.defaultHealth = defaultHealth;
        this.defaulAttackSpeed = defaulAttackSpeed;
        this.defaultSpeed = defaultSpeed;
    }

    /**
     * Returns the string representation of the entity type.
     *
     * @return the string representation of the entity type
     */
    public String getEntityString() {
        return entityString;
    }

    /**
     * Returns the number of sprites for the entity type.
     *
     * @return the number of sprites
     */
    public int getSpritesNumber() {
        return spritesNumber;
    }

    /**
     * Returns the default damage of the entity type.
     *
     * @return the default damage
     */
    public int getDefaultDamage() {
        return defaultDamage;
    }

    /**
     * Returns the default health of the entity type.
     *
     * @return the default health
     */
    public int getDefaultHealth() {
        return defaultHealth;
    }

    /**
     * Returns the default attack speed of the entity type.
     *
     * @return the default attack speed
     */
    public int getDefaultAttackSpeed() {
        return defaulAttackSpeed;
    }

    /**
     * Returns the default speed of the entity type.
     *
     * @return the default speed
     */
    public int getDefaultSpeed() {
        return defaultSpeed;
    }
}
