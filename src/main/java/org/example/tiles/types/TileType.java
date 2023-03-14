package org.example.tiles.types;

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
