package org.example.entities.types;

public enum EntityType {
    HERO("hero");

    private final String entityString;

    EntityType(String entityString) {
        this.entityString = entityString;
    }

    public String getEntityString() {
        return entityString;
    }
}
