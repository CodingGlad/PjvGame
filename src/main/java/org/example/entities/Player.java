package org.example.entities;

import org.example.entities.types.ActivityType;
import org.example.entities.types.EntityType;
import org.example.entities.types.HorizontalDirectionType;
import org.example.entities.types.VerticalDirectionType;
import org.example.game.GamePanel;
import org.example.game.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.game.GamePanel.TILE_SIZE;

public class Player extends Entity{

    private GamePanel panel;
    private KeyHandler keyHandler;

    //TODO cost default values change
    public Player(GamePanel panel, KeyHandler keyHandler) {
        super(100, 100, 4, EntityType.HERO);
        this.panel = panel;
        this.keyHandler = keyHandler;
    }

    public void update() {
        if (keyHandler.isUpPressed()) {
            setVerticalDirection(VerticalDirectionType.TOP);
            setY(getY() - getSpeed());
        }
        if (keyHandler.isDownPressed()) {
            setVerticalDirection(VerticalDirectionType.DOWN);
            setY(getY() + getSpeed());
        }
        if (keyHandler.isLeftPressed()) {
            setVerticalDirection(null);
            setHorizontalDirection(HorizontalDirectionType.LEFT);
            setX(getX() - getSpeed());
        }
        if (keyHandler.isRightPressed()) {
            setVerticalDirection(null);
            setHorizontalDirection(HorizontalDirectionType.RIGHT);
            setX(getX() + getSpeed());
        }
    }

    //TODO handle activity
    public void draw(Graphics2D g2) {
//        g2.setColor(Color.WHITE);
//        g2.fillRect(getX(), getY(), TILE_SIZE, TILE_SIZE);

        BufferedImage image = getImage(ActivityType.WALK);
        g2.drawImage(image, getX(), getY(), TILE_SIZE, TILE_SIZE, null);
    }
}
