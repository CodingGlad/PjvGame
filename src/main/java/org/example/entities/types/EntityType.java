package org.example.entities.types;

public enum EntityType {
    HERO("hero", 4, 8),
    DEMON("demon", 4, 13);

    private final String entityString;
    private final int spritesNumber;
    private final int defaultDamage;

    EntityType(String entityString, int spritesNumber, int defaultDamage) {
        this.entityString = entityString;
        this.spritesNumber = spritesNumber;
        this.defaultDamage = defaultDamage;
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
}
