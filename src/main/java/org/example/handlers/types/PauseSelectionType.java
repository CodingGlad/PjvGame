package org.example.handlers.types;

public enum PauseSelectionType {
    RESUME(80, 160),
    SAVE(80, 200),
    QUIT(80, 240);

    private final int x;
    private final int y;

    PauseSelectionType(int x, int y) {
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
