package org.example.views;

import org.example.entities.Player;
import org.example.tiles.Tile;

import java.awt.*;
import java.util.Map;

import static org.example.utils.GameConstants.TILE_SIZE;

/**
 * Handles the rendering of tiles on the screen.
 */
public class TileView {
    /**
     * Draws the tiles on the screen based on the mapTileNum array.
     *
     * @param g2          The graphics context used for drawing.
     * @param tileSprites A map of tile IDs to Tile objects.
     * @param mapTileNum  A 2D array representing the tile IDs of the map.
     * @param player      The player object.
     */
    public void draw(Graphics2D g2, Map<Integer, Tile> tileSprites, int[][] mapTileNum, Player player) {
        for (int i = 0; i < mapTileNum.length; ++i) {
            for (int j = 0; j < mapTileNum[i].length; ++j) {
                final int worldX = j * TILE_SIZE;
                final int worldY = i * TILE_SIZE;
                final int screenX = worldX - player.getWorldX() + player.getScreenX();
                final int screenY = worldY - player.getWorldY() + player.getScreenY();

                if (shouldTileBeRendered(worldX, worldY, player)) {
                    g2.drawImage(
                            tileSprites.get(mapTileNum[i][j]).getImage(), screenX, screenY,
                            TILE_SIZE, TILE_SIZE, null
                    );
                }
            }
        }
    }

    /**
     * Checks if a tile should be rendered based on its world coordinates and the player's position.
     *
     * @param worldX The world X coordinate of the tile.
     * @param worldY The world Y coordinate of the tile.
     * @param player The player object.
     * @return True if the tile should be rendered, false otherwise.
     */
    private boolean shouldTileBeRendered(int worldX, int worldY, Player player) {
        return isCoordinateWithinViewX(worldX, player) && isCoordinateWithinViewY(worldY, player);
    }

    /**
     * Checks if a world X coordinate is within the view of the player.
     *
     * @param worldX The world X coordinate to check.
     * @param player The player object.
     * @return True if the world X coordinate is within the view, false otherwise.
     */
    private boolean isCoordinateWithinViewX(int worldX, Player player) {
        return worldX + TILE_SIZE > (player.getWorldX() - player.getScreenX()) &&
                worldX - TILE_SIZE < (player.getWorldX() + player.getScreenX());
    }

    /**
     * Checks if a world Y coordinate is within the view of the player.
     *
     * @param worldY The world Y coordinate to check.
     * @param player The player object.
     * @return True if the world Y coordinate is within the view, false otherwise.
     */
    private boolean isCoordinateWithinViewY(int worldY, Player player) {
        return worldY + TILE_SIZE > (player.getWorldY() - player.getScreenY()) &&
                worldY - TILE_SIZE < (player.getWorldY() + player.getScreenY());
    }
}
