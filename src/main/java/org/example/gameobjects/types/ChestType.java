package org.example.gameobjects.types;

public enum ChestType {
    SMALL("small"),
    BIG("big");

    private final String name;

    ChestType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
