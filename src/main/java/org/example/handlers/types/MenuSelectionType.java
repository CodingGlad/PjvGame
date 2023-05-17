package org.example.handlers.types;

/**
 * Represents the different types of menu selections.
 */
public enum MenuSelectionType {
    NEW_GAME(80, 170),
    LOAD_GAME(80, 210),
    QUIT(80, 250);

    private final int x;
    private final int y;

    /**
     * Constructs a menu selection type with the specified coordinates.
     *
     * @param x The x-coordinate of the menu selection.
     * @param y The y-coordinate of the menu selection.
     */
    MenuSelectionType(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of the menu selection.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the menu selection.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }
}

