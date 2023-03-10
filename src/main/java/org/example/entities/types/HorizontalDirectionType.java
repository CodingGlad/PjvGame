package org.example.entities.types;

public enum HorizontalDirectionType {
    LEFT("left"),
    RIGHT("right");

    private final String directionString;

    HorizontalDirectionType(String directionString) {
        this.directionString = directionString;
    }

    public String getDirectionString() {
        return directionString;
    }
}
