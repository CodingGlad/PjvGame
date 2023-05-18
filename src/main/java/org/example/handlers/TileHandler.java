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
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.utils.GameConstants.*;

/**
 * The TileHandler class is responsible for managing tiles, loading maps, and handling tile-related operations.
 */
public class TileHandler {
    private static final Logger LOGGER = Logger.getLogger(TileHandler.class.getName());
    private static final String TILE_SPRITES_PATH = "/sprites/tiles/";
    private static final String MAP_LAYOUT_PATH = "/maps/map1/";

    private Map<Integer, Tile> tileSprites;
    private int[][] mapTileNum;

    private final TileView tileView;

    /**
     * Constructs a new TileHandler object.
     */
    public TileHandler() {
        tileSprites = new HashMap<>();
        tileView = new TileView();
        mapTileNum = new int[MAX_WORLD_COL][MAX_WORLD_ROW];

        getTileImages();
    }

    /**
     * Loads the tile images from the resources.
     */
    private void getTileImages() {
        try {
            for (TileType tile : TileType.values()) {
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

    /**
     * Loads the map layout from a file.
     * @param isLoggerOn Whether logger is turned on.
     */
    public void loadMap(boolean isLoggerOn) {
        InputStream is = getClass().getResourceAsStream(MAP_LAYOUT_PATH + "map01.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        for (int i = 0; i < MAX_WORLD_ROW; ++i) {
            try {
                String[] mapLine = br.readLine().split(" ");

                for (int j = 0; j < MAX_WORLD_COL; ++j) {
                    mapTileNum[i][j] = Integer.parseInt(mapLine[j]);
                }
            } catch (IOException e) {
                if (isLoggerOn) {
                    LOGGER.log(Level.SEVERE, "Map couldn't be loaded: " + e.getMessage());
                }
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Retrieves the tile number at the specified row and column.
     *
     * @param row    The row index of the tile.
     * @param column The column index of the tile.
     * @param isLoggerOn Whether logger is turned on.
     * @return The tile number.
     * @throws IllegalStateException if the player is out of map bounds.
     */
    public int getTileNumber(int row, int column, boolean isLoggerOn) {
        if (mapTileNum.length > row && mapTileNum[row].length > column) {
            return mapTileNum[row][column];
        } else {
            if (isLoggerOn) {
                LOGGER.log(Level.SEVERE, "Player has exited map bounds");
            }
            throw new IllegalStateException("Player out of map bounds.");
        }
    }

    /**
     * Checks if the tile with the specified tile number has a collision.
     *
     * @param tilenum The tile number to check.
     * @return true if the tile has a collision, false otherwise.
     * @throws IllegalStateException if the tile number is not found.
     */
    public boolean doesTileHaveCollision(int tilenum) {
        for (TileType type : TileType.values()) {
            if (type.getIntTileValue() == tilenum) {
                return type.isCollision();
            }
        }

        throw new IllegalStateException("Tile number not found.");
    }

    /**
     * Calculates the row or column index of the given world coordinate.
     *
     * @param worldCoordinate The world coordinate.
     * @return The corresponding row or column index.
     */
    public int getRowOrColumnOfCoordinate(int worldCoordinate) {
        return worldCoordinate / TILE_SIZE;
    }

    /**
     * Draws the tiles on the screen.
     */
    public void drawTiles(Graphics2D g2, Player player) {
        tileView.draw(g2, tileSprites, mapTileNum, player);
    }
}
