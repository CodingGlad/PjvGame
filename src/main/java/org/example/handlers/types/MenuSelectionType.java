package org.example.handlers.types;

public enum MenuSelectionType {
    NEW_GAME(80, 170),
    LOAD_GAME(80, 210),
    QUIT(80, 250);

    private final int x;
    private final int y;

    MenuSelectionType(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
