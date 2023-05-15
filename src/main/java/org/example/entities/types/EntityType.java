package org.example.entities.types;

public enum EntityType {
    HERO("hero", 4, 8, 100),
    DEMON("demon", 4, 13, 45);

    private final String entityString;
    private final int spritesNumber;
    private final int defaultDamage;
    private final int defaultHealth;

    EntityType(String entityString, int spritesNumber, int defaultDamage, int defaultHealth) {
        this.entityString = entityString;
        this.spritesNumber = spritesNumber;
        this.defaultDamage = defaultDamage;
        this.defaultHealth = defaultHealth;
    }

    public String getEntityString() {
        return entityString;
    }

    public int getSpritesNumber() {
        return spritesNumber;
    }

    public int getDefaultDamage() {
        return defaultDamage;
    }

    public int getDefaultHealth() {
        return defaultHealth;
    }
}
