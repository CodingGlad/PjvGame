package org.example.game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    private static final long NANOS_IN_SECONDS = 1000000000L;
    private static final int FPS = 60;

    private static final int ORIGINAL_TILE_SIZE = 16;
    private static final int SCALE_FACTOR = 3;
    private static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE_FACTOR;

    private static final int MAX_SCREEN_COLS = 16;
    private static final int MAX_SCREEN_ROWS = 12;
    private static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLS;
    private static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROWS;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 5;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double)NANOS_IN_SECONDS/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        if (keyHandler.isUpPressed()) {
            playerY -= playerSpeed;
        }
        if (keyHandler.isDownPressed()) {
            playerY += playerSpeed;
        }
        if (keyHandler.isLeftPressed()) {
            playerX -= playerSpeed;
        }
        if (keyHandler.isRightPressed()) {
            playerX += playerSpeed;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.WHITE);
        g2.fillRect(playerX, playerY, TILE_SIZE, TILE_SIZE);
        g2.dispose();
    }
}
