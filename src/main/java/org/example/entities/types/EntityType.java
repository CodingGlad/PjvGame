package org.example.entities.types;

public enum EntityType {
    HERO("hero", 4, 8, 100, 2, 4),
    DEMON("demon", 4, 13, 45, 2, 4);

    private final String entityString;
    private final int spritesNumber;
    private final int defaultDamage;
    private final int defaultHealth;
    private final int defaulAttackSpeed;
    private final int defaultSpeed;

    EntityType(String entityString, int spritesNumber, int defaultDamage,
               int defaultHealth, int defaulAttackSpeed, int defaultSpeed) {
        this.entityString = entityString;
        this.spritesNumber = spritesNumber;
        this.defaultDamage = defaultDamage;
        this.defaultHealth = defaultHealth;
        this.defaulAttackSpeed = defaulAttackSpeed;
        this.defaultSpeed = defaultSpeed;
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

    public int getDefaulAttackSpeed() {
        return defaulAttackSpeed;
    }

    public int getDefaultSpeed() {
        return defaultSpeed;
    }
}
