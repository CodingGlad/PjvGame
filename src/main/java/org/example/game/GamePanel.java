package org.example.game;

import org.example.entities.Player;
import org.example.tiles.TileManager;

import javax.swing.*;
import java.awt.*;

//TODO handle public constants
public class GamePanel extends JPanel implements Runnable{
    private static final long NANOS_IN_SECONDS = 1000000000L;
    private static final int FPS = 60;

    public static final int ORIGINAL_TILE_SIZE = 16;
    public static final int SCALE_FACTOR = 2;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE_FACTOR;

    public static final int MAX_SCREEN_COLS = 16;
    public static final int MAX_SCREEN_ROWS = 12;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLS;
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROWS;

    //TODO config file
    public static final int MAX_WORLD_COL = 50;
    public static final int MAX_WORLD_ROW = 50;
    public static final int WORLD_WIDTH = TILE_SIZE * MAX_WORLD_COL;
    public static final int WORLD_HEIGHT = TILE_SIZE * MAX_WORLD_ROW;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    Player player = new Player(keyHandler);
    TileManager tileManager = new TileManager(player);

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
        player.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        tileManager.draw(g2);
        player.draw(g2);

        g2.dispose();
    }
}
