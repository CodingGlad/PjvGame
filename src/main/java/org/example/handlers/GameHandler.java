package org.example.handlers;

import org.example.gameobjects.GameObject;
import org.example.handlers.types.GameStateType;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class GameHandler implements Runnable{

    private static final long NANOS_IN_SECONDS = 1000000000L;
    private static final int FPS = 60;
    private Thread gameThread;
    private WindowHandler windowHandler;
    private GameStateHandler gameState;
    private KeyHandler keyHandler;


    private GameObjectHandler objectHandler;
    private List<GameObject> displayedObjects;

    public GameHandler() {
        gameState = new GameStateHandler();
        windowHandler = new WindowHandler();
        keyHandler = new KeyHandler(gameState); //TODO keyHandler switches the state to "saving" or something and save happens?
        objectHandler = new GameObjectHandler();
        displayedObjects = new LinkedList<>(); //TODO get this into the object handler so its in one place

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
        objectHandler.setObjects(displayedObjects);
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
