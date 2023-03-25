package org.example.gameobjects.types;

public enum ChestStateType {
    OPENED("opened"),
    CLOSED("closed");

    private final String state;

    ChestStateType(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
