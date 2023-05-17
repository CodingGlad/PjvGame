package org.example.tiles;

import org.example.tiles.types.TileType;

import java.awt.image.BufferedImage;

/**
 * Represents a tile in the game.
 */
public class Tile {
    private final BufferedImage image;
    private final TileType tileType;

    /**
     * Constructs a tile with the specified image and tile type.
     *
     * @param image     the image representing the tile
     * @param tileType  the type of the tile
     */
    public Tile(BufferedImage image, TileType tileType) {
        this.image = image;
        this.tileType = tileType;
    }

    /**
     * Gets the image of the tile.
     *
     * @return the image of the tile
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Gets the type of the tile.
     *
     * @return the type of the tile
     */
    public TileType getTileType() {
        return tileType;
    }
}

