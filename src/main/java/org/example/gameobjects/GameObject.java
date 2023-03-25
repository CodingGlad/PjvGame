package org.example.gameobjects;

import org.example.entities.Player;
import org.example.gameobjects.types.ObjectType;
import org.example.utils.WorldCoordinates;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.game.GamePanel.TILE_SIZE;

public class GameObject {
    private final ObjectType objectType;
    private BufferedImage staticImage;
    private WorldCoordinates worldCoordinates;

    public GameObject(ObjectType objectType, WorldCoordinates worldCoordinates) {
        this.objectType = objectType;
        this.worldCoordinates = worldCoordinates;
    }

    public BufferedImage getStaticImage() {
        return staticImage;
    }

    public void setStaticImage(BufferedImage staticImage) {
        this.staticImage = staticImage;
    }

    public void draw(Graphics2D g2, Player player) {
        final int screenX = worldCoordinates.getWorldX() - player.getWorldX() + player.getScreenX();
        final int screenY = worldCoordinates.getWorldY() - player.getWorldY() + player.getScreenY();

        if (shouldTileBeRendered(worldCoordinates.getWorldX(), worldCoordinates.getWorldY(), player)) {
            g2.drawImage(staticImage, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
        }
    }

    //TODO same as tilehandler, mby own class?????
    private boolean shouldTileBeRendered(int worldX, int worldY, Player player) {
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
