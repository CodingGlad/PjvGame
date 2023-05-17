package org.example.views;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.utils.GameConstants.TILE_SIZE;

public class EntityView {
    /**
     * Draws the entity on the screen using the provided graphics context and image.
     * @param g2 The graphics context used for drawing.
     * @param image The image representing the entity.
     * @param screenX The x-coordinate of the entity on the screen.
     * @param screenY The y-coordinate of the entity on the screen.
     */
    public void draw(Graphics2D g2, BufferedImage image, int screenX, int screenY) {
        g2.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
    }
}
