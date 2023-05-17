package org.example.utils;

/**
 * Contains constant values used in the game.
 */
public class GameConstants {
    public static final int ORIGINAL_TILE_SIZE = 16;
    public static final int SCALE_FACTOR = 2;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE_FACTOR;
    public static final int MAX_SCREEN_COLS = 16;
    public static final int MAX_SCREEN_ROWS = 12;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLS;
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROWS;
    public static final int MAX_WORLD_COL = 50;
    public static final int MAX_WORLD_ROW = 50;
    public static final int WORLD_WIDTH = TILE_SIZE * MAX_WORLD_COL;
    public static final int WORLD_HEIGHT = TILE_SIZE * MAX_WORLD_ROW;
}
