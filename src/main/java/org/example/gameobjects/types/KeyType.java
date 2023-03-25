package org.example.gameobjects.types;

public enum KeyType {
    BRONZE("bronze"),
    SILVER("silver"),
    GOLD("gold");

    private final String name;

    KeyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
