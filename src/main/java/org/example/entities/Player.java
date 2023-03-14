package org.example.entities;

import org.example.entities.types.EntityType;
import org.example.entities.types.HorizontalDirectionType;
import org.example.entities.types.VerticalDirectionType;
import org.example.game.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.game.GamePanel.*;

public class Player extends Entity{

    private KeyHandler keyHandler;

    private final int screenX;
    private final int screenY;

    //TODO cost default values change
    public Player(KeyHandler keyHandler) {
        super(TILE_SIZE * 23, TILE_SIZE * 21, 4, EntityType.HERO);
        this.keyHandler = keyHandler;

        this.screenX = (SCREEN_WIDTH / 2) - (TILE_SIZE / 2);
        this.screenY = (SCREEN_HEIGHT / 2) - (TILE_SIZE / 2);
    }

    public void update() {
        if (keyHandler.isUpPressed()) {
            setVerticalDirection(VerticalDirectionType.TOP);
            setWorldY(getWorldY() - getSpeed());
        } else if (keyHandler.isDownPressed()) {
            setVerticalDirection(VerticalDirectionType.DOWN);
            setWorldY(getWorldY() + getSpeed());
        } else if (keyHandler.isLeftPressed()) {
            setVerticalDirection(null);
            setHorizontalDirection(HorizontalDirectionType.LEFT);
            setWorldX(getWorldX() - getSpeed());
        } else if (keyHandler.isRightPressed()) {
            setVerticalDirection(null);
            setHorizontalDirection(HorizontalDirectionType.RIGHT);
            setWorldX(getWorldX() + getSpeed());
        } else {
            setIdleActivity();
        }
        incrementCounter();
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = getImage();
        g2.drawImage(image, screenX, screenY, TILE_SIZE, TILE_SIZE, null);
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }
}
