package org.example.handlers;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import org.example.entities.Player;
import org.example.utils.WorldCoordinates;
import org.example.views.EntityView;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    private final EnemiesHandler enemiesHandler;

    public GameHandler() {
        gameState = new GameStateHandler();
        keyHandler = new KeyHandler(gameState);
        objectHandler = new GameObjectHandler();
        tileHandler = new TileHandler();
        collisionHandler = new CollisionHandler(tileHandler, objectHandler.getDisplayedObjects());
        userInterfaceHandler = new UserInterfaceHandler();
        player = new Player(keyHandler);
        enemiesHandler = new EnemiesHandler();

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
        switch (gameState.getStateType()) {
            case RUNNING -> {
                player.update(collisionHandler);
                enemiesHandler.update();
            }
            case SAVING -> saveGame();
            case LOADING -> loadGame();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        if (!gameState.getStateType().equals(MAIN_MENU)) {
            tileHandler.drawTiles(g2, player);
            objectHandler.drawObjects(g2, player);
            enemiesHandler.drawEnemies(g2, player);
            player.drawPlayer(g2);
        }

        userInterfaceHandler.drawInterface(g2, gameState.getStateType(),
                gameState.getMenuCursorState(), gameState.getPauseCursorState(),
                player);

        g2.dispose();
    }


    private void saveGame() {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("save.json"));

            JsonObject playerJson = new JsonObject();

            playerJson.put("worldx", player.getWorldX());
            playerJson.put("worldy", player.getWorldY());
            playerJson.put("keys", player.getNumberOfKeys());

            JsonObject save = new JsonObject();

            save.put("player", playerJson);

            Jsoner.serialize(save, writer);

            writer.close();

            gameState.setRunning();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadGame() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get("save.json"));

            JsonObject parser = (JsonObject) Jsoner.deserialize(reader);

            JsonObject playerInfo = (JsonObject) parser.get("player");

            BigDecimal x = (BigDecimal) playerInfo.get("worldx");
            BigDecimal y = (BigDecimal) playerInfo.get("worldy");
            BigDecimal keys = (BigDecimal) playerInfo.get("keys");

            reader.close();

            player.loadCoordinations(x.intValue(), y.intValue());
            player.setNumberOfKeys(keys.intValue());

            gameState.setRunning();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
