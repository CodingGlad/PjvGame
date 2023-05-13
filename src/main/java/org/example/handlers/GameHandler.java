package org.example.handlers;

import org.example.entities.Player;
import org.example.gameobjects.GameObject;
import org.example.handlers.types.GameStateType;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class GameHandler implements Runnable{

    private static final long NANOS_IN_SECONDS = 1000000000L;
    private static final int FPS = 60;
    private Thread gameThread;
    private final WindowHandler windowHandler;
    private final GameStateHandler gameState;
    private final KeyHandler keyHandler;
    private final GameObjectHandler objectHandler;
    private final TileHandler tileHandler;
    private final Player player;
    private final CollisionHandler collisionHandler;

    public GameHandler() {
        gameState = new GameStateHandler();
        keyHandler = new KeyHandler(gameState); //TODO keyHandler switches the state to "saving" or something and save happens?
        windowHandler = new WindowHandler(keyHandler);
        objectHandler = new GameObjectHandler();
        tileHandler = new TileHandler();
        player = new Player(keyHandler); //TODO is keyhandler useful here?
        collisionHandler = new CollisionHandler(tileHandler, objectHandler.getDisplayedObjects());

    }

    public void setupWindow() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Wodecki Adventure Game");

        window.add(windowHandler);
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

    public void update() {
        if (gameState.getStateType().equals(GameStateType.RUNNING)) {
            player.update(collisionHandler);
        }
    }
}
