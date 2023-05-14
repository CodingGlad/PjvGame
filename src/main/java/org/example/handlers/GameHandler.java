package org.example.handlers;

import org.example.entities.Player;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

import static org.example.handlers.types.GameStateType.*;
import static org.example.utils.GameConstants.SCREEN_HEIGHT;
import static org.example.utils.GameConstants.SCREEN_WIDTH;

public class GameHandler extends JPanel implements Runnable {

    private static final long NANOS_IN_SECONDS = 1000000000L;
    private static final int FPS = 60;

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
        keyHandler = new KeyHandler(gameState);
        objectHandler = new GameObjectHandler();
        tileHandler = new TileHandler();
        player = new Player(keyHandler);
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
        if (gameState.getStateType().equals(RUNNING)) {
            player.update(collisionHandler);
        } else if (gameState.getStateType().equals(SAVING)) {
            saveGame();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        if (!gameState.getStateType().equals(MAIN_MENU)) {
            tileHandler.drawTiles(g2, player);
            objectHandler.drawObjects(g2, player);
            player.drawPlayer(g2);
        }

        userInterfaceHandler.drawInterface(g2, gameState.getStateType(),
                gameState.getMenuCursorState(), gameState.getPauseCursorState(),
                player.getNumberOfKeys());

        g2.dispose();
    }


    private void saveGame() {
        try {
            FileWriter fw = new FileWriter("save.txt");

            fw.write(String.valueOf(player.getNumberOfKeys()));

            fw.close();

            gameState.setRunning();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
