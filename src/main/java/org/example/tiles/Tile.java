package org.example.tiles;

import org.example.tiles.types.TileType;

import java.awt.image.BufferedImage;

public class Tile {
    private final BufferedImage image;
    private final TileType tileType;

    public Tile(BufferedImage image, TileType tileType) {
        this.image = image;
        this.tileType = tileType;
    }

    public BufferedImage getImage() {
        return image;
    }

    public TileType getTileType() {
        return tileType;
    }
}
