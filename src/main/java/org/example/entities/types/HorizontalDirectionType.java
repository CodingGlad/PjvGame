package org.example.entities.types;

/**
 * Represents the horizontal directions.
 * Each direction type has a corresponding string representation.
 */
public enum HorizontalDirectionType {
    /**
     * Represents the left direction.
     * The string representation is "left".
     */
    LEFT("left"),
    /**
     * Represents the right direction.
     * The string representation is "right".
     */
    RIGHT("right");

    private final String directionString;

    /**
     * Constructs a HorizontalDirectionType with the specified string representation.
     *
     * @param directionString the string representation of the direction
     */
    HorizontalDirectionType(String directionString) {
        this.directionString = directionString;
    }

    /**
     * Returns the string representation of the direction.
     *
     * @return the string representation of the direction
     */
    public String getDirectionString() {
        return directionString;
    }
}
