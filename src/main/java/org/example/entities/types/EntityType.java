package org.example.entities.types;

public enum EntityType {
    HERO("hero", 4),
    DEMON("demon", 4);

    private final String entityString;
    private final int spritesNumber;

    EntityType(String entityString, int spritesNumber) {
        this.entityString = entityString;
        this.spritesNumber = spritesNumber;
    }

    public String getEntityString() {
        return entityString;
    }

    public int getSpritesNumber() {
        return spritesNumber;
    }
}
