package org.example.handlers;

import org.example.entities.Player;
import org.example.tiles.Tile;
import org.example.tiles.types.TileType;
import org.example.views.TileView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.example.utils.GameConstants.*;

public class TileHandler {
    private static final String TILE_SPRITES_PATH = "/sprites/tiles/";
    private static final String MAP_LAYOUT_PATH = "/maps/";

    private Map<Integer, Tile> tileSprites;
    private int[][] mapTileNum;

    private final TileView tileView;

    public TileHandler() {
        tileSprites = new HashMap<>();
        tileView = new TileView();
        mapTileNum = new int[MAX_WORLD_COL][MAX_WORLD_ROW];

        getTileImages();
        loadMap();
    }

    private void getTileImages() {
        try {
            for (TileType tile: TileType.values()) {
                tileSprites.put(
                        tile.getIntTileValue(),
                        new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                                TILE_SPRITES_PATH + tile.getSpriteName()))), tile)
                );
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadMap() {
        InputStream is = getClass().getResourceAsStream(MAP_LAYOUT_PATH + "map02.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        for(int i = 0; i < MAX_WORLD_ROW; ++i) {
            try {
                String[] mapLine = br.readLine().split(" ");

                for(int j = 0; j < MAX_WORLD_COL; ++j) {
                    mapTileNum[i][j] = Integer.parseInt(mapLine[j]);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getTileNumber(int row, int column) {
        if (mapTileNum.length > row && mapTileNum[row].length > column) {
            return mapTileNum[row][column];
        } else {
            throw new IllegalStateException("Player out of map bounds.");
        }
    }

    public boolean doesTileHaveCollision(int tilenum) {
        for (TileType type: TileType.values()) {
            if (type.getIntTileValue() == tilenum) {
                return type.isCollision();
            }
        }

        throw new IllegalStateException("Tile number not found.");
    }

    public int getRowOrColumnOfCoordinate(int worldCoordinate) {
        return worldCoordinate / TILE_SIZE;
    }

    public void drawTiles(Graphics2D g2, Player player) {
        tileView.draw(g2, tileSprites, mapTileNum, player);
    }
}
