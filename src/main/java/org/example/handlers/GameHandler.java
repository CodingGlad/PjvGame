package org.example.handlers;

import org.example.entities.Player;
import org.example.gameobjects.GameObject;
import org.example.handlers.types.GameStateType;

import javax.swing.*;
import java.awt.*;

import static org.example.handlers.types.GameStateType.RUNNING;
import static org.example.handlers.types.GameStateType.MAIN_MENU;
import static org.example.handlers.types.GameStateType.PAUSE;

public class GameHandler extends JPanel implements Runnable {

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

    private Thread gameThread;
    private final GameStateHandler gameState;
    private final KeyHandler keyHandler;
    private final GameObjectHandler objectHandler;
    private final TileHandler tileHandler;
    private final Player player;
    private final CollisionHandler collisionHandler;
    private final UserInterfaceHandler userInterfaceHandler;

    public GameHandler() {
        gameState = new GameStateHandler();
        keyHandler = new KeyHandler(gameState); //TODO keyHandler switches the state to "saving" or something and save happens?
        objectHandler = new GameObjectHandler();
        tileHandler = new TileHandler();
        player = new Player(keyHandler); //TODO is keyhandler useful here?
        collisionHandler = new CollisionHandler(tileHandler, objectHandler.getDisplayedObjects());
        userInterfaceHandler = new UserInterfaceHandler();

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupWindow() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Wodecki Adventure Game");

        window.add(this);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void setupGame() {
        objectHandler.setDefaultObjects();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Code from RyiShow, zdroj
     */
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

    private void update() {
        if (gameState.getStateType().equals(GameStateType.RUNNING)) {
            player.update(collisionHandler);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        userInterfaceHandler.drawInterface(g2, gameState.getStateType(),
                gameState.getMenuCursorState(), gameState.getPauseCursorState(),
                player.getNumberOfKeys());

        if (!gameState.getStateType().equals(MAIN_MENU)) {
            tileHandler.drawTiles(g2, player);
            objectHandler.drawObjects(g2, player);
            player.drawPlayer(g2);
        }

        g2.dispose();
    }
}
