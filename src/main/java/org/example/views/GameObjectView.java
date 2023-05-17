package org.example.views;

import org.example.entities.Player;
import org.example.gameobjects.GameObject;

import java.awt.*;

import static org.example.utils.GameConstants.TILE_SIZE;

/**
 * Handles the rendering of a game object on the screen.
 */
public class GameObjectView {
    /**
     * Draws the game object on the screen relative to the player's position.
     *
     * @param g2      The graphics context used for drawing.
     * @param player  The player object.
     * @param object  The game object to be drawn.
     */
    public void draw(Graphics2D g2, Player player, GameObject object) {
        final int screenX = object.getWorldX() - player.getWorldX() + player.getScreenX();
        final int screenY = object.getWorldY() - player.getWorldY() + player.getScreenY();

        if (shouldObjectBeRendered(object.getWorldX(), object.getWorldY(), player)) {
            g2.drawImage(object.getStaticImage(), screenX, screenY, TILE_SIZE, TILE_SIZE, null);
        }
    }

    /**
     * Checks if the object should be rendered based on its world coordinates and the player's position.
     *
     * @param worldX  The world x-coordinate of the object.
     * @param worldY  The world y-coordinate of the object.
     * @param player  The player object.
     * @return True if the object should be rendered, false otherwise.
     */
    private boolean shouldObjectBeRendered(int worldX, int worldY, Player player) {
        return isCoordinateWithinViewX(worldX, player) && isCoordinateWithinViewY(worldY, player);
    }

    /**
     * Checks if the x-coordinate is within the player's view.
     *
     * @param worldX  The world x-coordinate.
     * @param player  The player object.
     * @return True if the x-coordinate is within the player's view, false otherwise.
     */
    private boolean isCoordinateWithinViewX(int worldX, Player player) {
        return worldX + TILE_SIZE > (player.getWorldX() - player.getScreenX()) &&
                worldX - TILE_SIZE < (player.getWorldX() + player.getScreenX());
    }

    /**
     * Checks if the y-coordinate is within the player's view.
     *
     * @param worldY  The world y-coordinate.
     * @param player  The player object.
     * @return True if the y-coordinate is within the player's view, false otherwise.
     */
    private boolean isCoordinateWithinViewY(int worldY, Player player) {
        return worldY + TILE_SIZE > (player.getWorldY() - player.getScreenY()) &&
                worldY - TILE_SIZE < (player.getWorldY() + player.getScreenY());
    }
}
