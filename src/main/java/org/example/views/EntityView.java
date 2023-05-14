package org.example.views;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.handlers.WindowHandler.TILE_SIZE;

public class EntityView {
    public void draw(Graphics2D g2, BufferedImage image,
                     int screenX, int screenY) {
        g2.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
    }
}
