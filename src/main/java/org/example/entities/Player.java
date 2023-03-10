package org.example.entities;

import org.example.entities.types.EntityType;
import org.example.entities.types.HorizontalDirectionType;
import org.example.entities.types.VerticalDirectionType;
import org.example.game.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.game.GamePanel.TILE_SIZE;

public class Player extends Entity{

    private KeyHandler keyHandler;

    //TODO cost default values change
    public Player(KeyHandler keyHandler) {
        super(100, 100, 4, EntityType.HERO);
        this.keyHandler = keyHandler;
    }

    public void update() {
        if (keyHandler.isUpPressed()) {
            setVerticalDirection(VerticalDirectionType.TOP);
            setY(getY() - getSpeed());
        } else if (keyHandler.isDownPressed()) {
            setVerticalDirection(VerticalDirectionType.DOWN);
            setY(getY() + getSpeed());
        } else if (keyHandler.isLeftPressed()) {
            setVerticalDirection(null);
            setHorizontalDirection(HorizontalDirectionType.LEFT);
            setX(getX() - getSpeed());
        } else if (keyHandler.isRightPressed()) {
            setVerticalDirection(null);
            setHorizontalDirection(HorizontalDirectionType.RIGHT);
            setX(getX() + getSpeed());
        } else {
            setIdleActivity();
        }
        incrementCounter();
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = getImage();
        g2.drawImage(image, getX(), getY(), TILE_SIZE, TILE_SIZE, null);
    }
}
