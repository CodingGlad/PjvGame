package org.example.entities;

import org.example.entities.types.ActivityType;
import org.example.entities.types.EntityType;
import org.example.entities.types.HorizontalDirectionType;
import org.example.entities.types.VerticalDirectionType;
import org.example.game.CollisionHandler;
import org.example.game.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.game.GamePanel.*;

public class Player extends Entity{

    private static final int SOLID_X = 8;
    private static final int SOLID_Y = 25;
    private static final int SOLID_WIDTH = 12;
    private static final int SOLID_HEIGHT = 4;

    private KeyHandler keyHandler;

    private final int screenX;
    private final int screenY;

    //TODO cost default values change
    public Player(KeyHandler keyHandler) {
        super(TILE_SIZE * 23, TILE_SIZE * 21, 4,
                EntityType.HERO, SOLID_X, SOLID_Y, SOLID_WIDTH, SOLID_HEIGHT);
        this.keyHandler = keyHandler;

        this.screenX = (SCREEN_WIDTH / 2) - (TILE_SIZE / 2);
        this.screenY = (SCREEN_HEIGHT / 2) - (TILE_SIZE / 2);
    }

    public void update(CollisionHandler collisionHandler) {
        if (keyHandler.isUpPressed()) {
            setVerticalDirection(VerticalDirectionType.TOP);
        } else if (keyHandler.isDownPressed()) {
            setVerticalDirection(VerticalDirectionType.DOWN);
        } else if (keyHandler.isLeftPressed()) {
            setVerticalDirection(VerticalDirectionType.NONE);
            setHorizontalDirection(HorizontalDirectionType.LEFT);
        } else if (keyHandler.isRightPressed()) {
            setVerticalDirection(VerticalDirectionType.NONE);
            setHorizontalDirection(HorizontalDirectionType.RIGHT);
        } else {
            setIdleActivity();
        }

        setCollisionsOn(false);
        collisionHandler.checkTile(this);

        if (!isCollisionsOn() && !getActivityType().equals(ActivityType.IDLE)) {
            move();
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

    private void move() {
        switch (getVerticalDirection()) {
            case TOP -> setWorldY(getWorldY() - getSpeed());
            case DOWN -> setWorldY(getWorldY() + getSpeed());
            case NONE -> {
                switch (getHorizontalDirection()) {
                    case LEFT -> setWorldX(getWorldX() - getSpeed());
                    case RIGHT -> setWorldX(getWorldX() + getSpeed());
                }
            }
        }
    }
}
