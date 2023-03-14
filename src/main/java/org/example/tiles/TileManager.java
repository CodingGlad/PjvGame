package org.example.tiles;

import org.example.tiles.types.TileType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.example.game.GamePanel.*;

public class TileManager {
    private static final String TILE_SPRITES_PATH = "/sprites/tiles/";
    private static final String MAP_LAYOUT_PATH = "/maps/";

    private Map<Integer, Tile> tileSprites;
    private int[][] mapTileNum;

    public TileManager() {
        tileSprites = new HashMap<>();
        mapTileNum = new int[MAX_SCREEN_ROWS][MAX_SCREEN_COLS];

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

    public void draw(Graphics2D g2) {
        for (int i = 0; i < mapTileNum.length; ++i) {
            for (int j = 0; j < mapTileNum[i].length; ++j) {
                g2.drawImage(
                        tileSprites.get(mapTileNum[i][j]).getImage(), TILE_SIZE * j, TILE_SIZE * i,
                        TILE_SIZE, TILE_SIZE, null
                );
            }
        }
    }

    private void loadMap() {
        InputStream is = getClass().getResourceAsStream(MAP_LAYOUT_PATH + "map01.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        for(int i = 0; i < MAX_SCREEN_ROWS; ++i) {
            try {
                String[] mapLine = br.readLine().split(" ");

                for(int j = 0; j < MAX_SCREEN_COLS; ++j) {
                    mapTileNum[i][j] = Integer.parseInt(mapLine[j]);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
