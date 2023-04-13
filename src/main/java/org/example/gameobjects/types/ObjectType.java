package org.example.gameobjects.types;

public enum ObjectType {
    KEY("key", false),
    CHEST("chest", true);

    private final String name;
    private final boolean collision;

    ObjectType(String name, boolean collision) {
        this.name = name;
        this.collision = collision;
    }

    public String getName() {
        return name;
    }

    public boolean hasCollisions() {
        return collision;
    }
}
