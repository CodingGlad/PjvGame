package org.example.gameobjects.types;

/**
 * Represents different types of chests.
 */
public enum ChestType {
    SMALL("small"),
    BIG("big");

    private final String name;

    /**
     * Constructs a ChestType enum with the specified name.
     *
     * @param name the name of the chest type
     */
    ChestType(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the chest type.
     *
     * @return the name of the chest type
     */
    public String getName() {
        return name;
    }
}
