package org.example.gameobjects.types;

/**
 * Represents different states of a chest.
 */
public enum ChestStateType {
    OPENED("opened"),
    CLOSED("closed");

    private final String state;

    /**
     * Constructs a ChestStateType enum with the specified state.
     *
     * @param state the state of the chest
     */
    ChestStateType(String state) {
        this.state = state;
    }

    /**
     * Returns the state of the chest.
     *
     * @return the state of the chest
     */
    public String getState() {
        return state;
    }
}

