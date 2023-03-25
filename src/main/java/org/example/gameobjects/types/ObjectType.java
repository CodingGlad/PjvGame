package org.example.gameobjects.types;

public enum ObjectType {
    KEY("key"),
    CHEST("chest");

    private final String name;

    ObjectType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
