package org.example.entities;

import org.example.entities.types.ActivityType;
import org.example.entities.types.EntityType;
import org.example.entities.types.HorizontalDirectionType;
import org.example.entities.types.VerticalDirectionType;
import org.example.handlers.CollisionHandler;
import org.example.handlers.KeyHandler;
import org.example.utils.WorldCoordinates;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.handlers.WindowHandler.*;

public class Player extends Entity{

    private static final int DEFAULT_SOLID_X = 8;
    private static final int DEFAULT_SOLID_Y = 25;
    private static final int DEFAULT_SOLID_WIDTH = 12;
    private static final int DEFAULT_SOLID_HEIGHT = 4;

    private KeyHandler keyHandler;

    private final int screenX;
    private final int screenY;
    private int numberOfKeys;

    //TODO cost default values change
    public Player(KeyHandler keyHandler) {
        super(new WorldCoordinates(TILE_SIZE * 23, TILE_SIZE * 21), 4,
                EntityType.HERO, DEFAULT_SOLID_X, DEFAULT_SOLID_Y, DEFAULT_SOLID_WIDTH, DEFAULT_SOLID_HEIGHT);
        this.keyHandler = keyHandler;

        this.screenX = (SCREEN_WIDTH / 2) - (TILE_SIZE / 2);
        this.screenY = (SCREEN_HEIGHT / 2) - (TILE_SIZE / 2);
    }

    //TODO refactor
    public void update(CollisionHandler collisionHandler) {
        if (keyHandler.isUpPressed()) {
            setVerticalDirection(VerticalDirectionType.UP);
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
        collisionHandler.checkCollisions(this);
        collisionHandler.checkObject(this);



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

    public int getNumberOfKeys() {
        return numberOfKeys;
    }

    public void incrementKeys() {
        ++numberOfKeys;
    }

    public void decrementKeys() {
        --numberOfKeys;
    }
}
