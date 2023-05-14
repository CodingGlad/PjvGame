package org.example.views;

import org.example.entities.Player;
import org.example.tiles.Tile;

import java.awt.*;
import java.util.Map;

import static org.example.handlers.WindowHandler.TILE_SIZE;

public class TileView{
    //TODO replace player coords somehow during rendering
    public void draw(Graphics2D g2, Map<Integer, Tile> tileSprites,
                     int[][] mapTileNum, Player player) {
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

    private boolean shouldTileBeRendered(int worldX, int worldY, Player player) {
        return isCoordinateWithinViewX(worldX, player) &&
                isCoordinateWithinViewY(worldY, player);
    }

    private boolean isCoordinateWithinViewX(int worldX, Player player) {
        return worldX + TILE_SIZE > (player.getWorldX() - player.getScreenX()) &&
                worldX - TILE_SIZE < (player.getWorldX() + player.getScreenX());
    }

    private boolean isCoordinateWithinViewY(int worldY, Player player) {
        return worldY + TILE_SIZE > (player.getWorldY() - player.getScreenY()) &&
                worldY - TILE_SIZE < (player.getWorldY() + player.getScreenY());
    }
}
