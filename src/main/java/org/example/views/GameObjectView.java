package org.example.views;

import org.example.entities.Player;
import org.example.gameobjects.GameObject;

import java.awt.*;

import static org.example.handlers.WindowHandler.TILE_SIZE;

public class GameObjectView {
    public void draw(Graphics2D g2, Player player, GameObject object) {
        final int screenX = object.getWorldX() - player.getWorldX() + player.getScreenX();
        final int screenY = object.getWorldY() - player.getWorldY() + player.getScreenY();

        if (shouldObjectBeRendered(object.getWorldX(), object.getWorldY(), player)) {
            g2.drawImage(object.getStaticImage(), screenX, screenY, TILE_SIZE, TILE_SIZE, null);
        }
    }

    private boolean shouldObjectBeRendered(int worldX, int worldY, Player player) {
        return isCoordinateWithinViewX(worldX, player) && isCoordinateWithinViewY(worldY, player);
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
