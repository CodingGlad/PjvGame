package org.example.tiles.types;

public enum TileType {
    GRASS(false, 0, "grass-1.png"),
    DIRT(false, 1, "dirt-1.png"),
    WATER(false, 2, "water-1.png"),
    WALL(true, 3, "wall-1.png");

    private final boolean collision;
    private final int intTileValue;
    private final String spriteName;

    TileType(boolean collision, int intTileValue, String spriteName) {
        this.collision = collision;
        this.intTileValue = intTileValue;
        this.spriteName = spriteName;
    }

    public String getSpriteName() {
        return spriteName;
    }

    public int getIntTileValue() {
        return intTileValue;
    }
}
