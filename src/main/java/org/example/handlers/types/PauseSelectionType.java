package org.example.handlers.types;

/**
 * Represents the different types of selections in the pause menu.
 */
public enum PauseSelectionType {
    RESUME(80, 160),
    SAVE(80, 200),
    QUIT(80, 240);

    private final int x;
    private final int y;

    /**
     * Constructs a pause selection type with the specified coordinates.
     *
     * @param x The x-coordinate of the pause selection.
     * @param y The y-coordinate of the pause selection.
     */
    PauseSelectionType(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of the pause selection.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the pause selection.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }
}

