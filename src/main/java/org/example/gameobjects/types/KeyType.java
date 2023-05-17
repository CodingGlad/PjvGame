package org.example.gameobjects.types;

/**
 * Enum representing different types of keys.
 */
public enum KeyType {
    BRONZE("bronze"),
    SILVER("silver"),
    GOLD("gold");

    private final String name;

    /**
     * Constructs a KeyType enum with the specified name.
     *
     * @param name The name of the key type.
     */
    KeyType(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the key type.
     *
     * @return The name of the key type.
     */
    public String getName() {
        return name;
    }
}

