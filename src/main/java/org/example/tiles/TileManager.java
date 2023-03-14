package org.example.tiles;

import org.example.entities.Player;
import org.example.game.GamePanel;
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
    private Player player;

    public TileManager(Player player) {
        this.player = player;
        tileSprites = new HashMap<>();
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

    public void draw(Graphics2D g2) {
        for (int i = 0; i < mapTileNum.length; ++i) {
            for (int j = 0; j < mapTileNum[i].length; ++j) {
                final int worldX = j * TILE_SIZE;
                final int worldY = i * TILE_SIZE;
                final int screenX = worldX - player.getWorldX() + player.getScreenX();
                final int screenY = worldY - player.getWorldY() + player.getScreenY();

                g2.drawImage(
                        tileSprites.get(mapTileNum[i][j]).getImage(), screenX, screenY,
                        TILE_SIZE, TILE_SIZE, null
                );
            }
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
}
