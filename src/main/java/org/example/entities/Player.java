package org.example.entities;

import org.example.game.GamePanel;
import org.example.game.KeyHandler;

import java.awt.*;

import static org.example.game.GamePanel.TILE_SIZE;

public class Player extends Entity{

    private GamePanel panel;
    private KeyHandler keyHandler;

    public Player(GamePanel panel, KeyHandler keyHandler) {
        this.panel = panel;
        this.keyHandler = keyHandler;

        setDefault();
    }

    public void setDefault() {
        setX(100);
        setY(100);
        setSpeed(4);
    }

    public void update() {
        if (keyHandler.isUpPressed()) {
            setY(getY() - getSpeed());
        }
        if (keyHandler.isDownPressed()) {
            setY(getY() + getSpeed());
        }
        if (keyHandler.isLeftPressed()) {
            setX(getX() - getSpeed());
        }
        if (keyHandler.isRightPressed()) {
            setX(getX() + getSpeed());
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(getX(), getY(), TILE_SIZE, TILE_SIZE);
    }
}
