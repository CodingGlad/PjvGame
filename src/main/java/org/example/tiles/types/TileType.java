package org.example.tiles.types;

/**
 * Represents the types of tiles in the game.
 */
public enum TileType {
    GRASS1(false, 0, "grass-1.png"),
    GRASS2(false, 1, "grass-2.png"),
    GRASS3(false, 2, "grass-2.png"),
    DIRT1(false, 3, "dirt-1.png"),
    DIRT2(false, 4, "dirt-2.png"),
    WATER(false, 5, "water-1.png"),
    WALL(true, 6, "wall-1.png");

    private final boolean collision;
    private final int intTileValue;
    private final String spriteName;

    /**
     * Constructs a tile type with the specified collision flag, integer value, and sprite name.
     *
     * @param collision     indicates whether the tile has collision
     * @param intTileValue  the integer value of the tile
     * @param spriteName    the name of the sprite associated with the tile
     */
    TileType(boolean collision, int intTileValue, String spriteName) {
        this.collision = collision;
        this.intTileValue = intTileValue;
        this.spriteName = spriteName;
    }

    /**
     * Gets the name of the sprite associated with the tile type.
     *
     * @return the sprite name
     */
    public String getSpriteName() {
        return spriteName;
    }

    /**
     * Gets the integer value of the tile type.
     *
     * @return the integer value
     */
    public int getIntTileValue() {
        return intTileValue;
    }

    /**
     * Checks if the tile type has collision.
     *
     * @return true if the tile has collision, false otherwise
     */
    public boolean isCollision() {
        return collision;
    }
}

