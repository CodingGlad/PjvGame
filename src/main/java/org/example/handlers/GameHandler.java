package org.example.handlers;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import org.example.entities.Enemy;
import org.example.entities.Player;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.handlers.types.GameStateType.*;
import static org.example.utils.GameConstants.SCREEN_HEIGHT;
import static org.example.utils.GameConstants.SCREEN_WIDTH;

/**
 * Main class that handles the whole game.
 */
public class GameHandler extends JPanel implements Runnable {

    private static final long NANOS_IN_SECONDS = 1000000000L;
    private static final int FPS = 60;
    private static final Logger LOGGER = Logger.getLogger(GameHandler.class.getName());

    private Thread gameThread;
    private final GameStateHandler gameState;
    private final KeyHandler keyHandler;
    private final GameObjectHandler objectHandler;
    private final TileHandler tileHandler;
    private final Player player;
    private final CollisionHandler collisionHandler;
    private final UserInterfaceHandler userInterfaceHandler;
    private final EnemiesHandler enemiesHandler;
    private final ParticleHandler particleHandler;

    /**
     * Default constructor that creates all mandatory handlers to control the game.
     */
    public GameHandler() {
        gameState = new GameStateHandler();
        keyHandler = new KeyHandler(gameState);
        objectHandler = new GameObjectHandler();
        tileHandler = new TileHandler();
        player = new Player(keyHandler);
        enemiesHandler = new EnemiesHandler();
        collisionHandler = new CollisionHandler(tileHandler, gameState,
                objectHandler.getDisplayedObjects(), enemiesHandler.getEnemies());
        userInterfaceHandler = new UserInterfaceHandler();
        particleHandler = new ParticleHandler();

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    /**
     * Sets up the game window.
     */
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

    /**
     * Starts the game thread.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * The game loop that handles game updates and rendering.
     * <br>
     * During the development, I have derived this game loop solution
     * from RyiSnow and his RPG game.
     */
    @Override
    public void run() {
        double drawInterval = (double)NANOS_IN_SECONDS / FPS;
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

    /**
     * Updates the game state based on the current state.
     */
    private void update() {
        switch (gameState.getStateType()) {
            case RUNNING -> {
                player.update(collisionHandler);
                enemiesHandler.update();
                particleHandler.update();
            }
            case SAVING -> saveGame();
            case LOADING -> loadGame();
            case FIGHTING -> fight();
            case DYING -> die();
            case QUIT -> System.exit(0);
            case STARTING -> startNewGame();
        }
    }

    /**
     * Paints the game components on the screen.
     *
     * @param g The Graphics object used for rendering.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (!gameState.getStateType().equals(MAIN_MENU)) {
            tileHandler.drawTiles(g2, player);
            enemiesHandler.drawEnemies(g2, player);
            objectHandler.drawObjects(g2, player);
            player.drawPlayer(g2);
            particleHandler.drawParticle(g2, player);
        }

        userInterfaceHandler.drawInterface(g2, gameState.getStateType(),
                gameState.getMenuCursorState(), gameState.getPauseCursorState(),
                player, gameState.getOpponent());

        g2.dispose();
    }

    /**
     * Saves the current game state to a file.
     */
    private void saveGame() {
        LOGGER.log(Level.INFO, "Saving game...");
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("save.json"));

            JsonObject save = new JsonObject();

            save.put("player", player.serializePlayer());
            save.put("enemies", enemiesHandler.serializeEnemies());
            save.put("objects", objectHandler.serializeObjects());

            Jsoner.serialize(save, writer);

            writer.close();

            LOGGER.log(Level.INFO, "Game has been saved.");
            gameState.setRunning();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Game couldn't be saved.");
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the saved game from a file.
     */
    private void loadGame() {
        LOGGER.log(Level.INFO, "Loading saved game.");
        try {
            Reader reader = Files.newBufferedReader(Paths.get("save.json"));

            deserializeAll(reader);

            LOGGER.log(Level.INFO, "Deserialization done.");

            reader.close();

            LOGGER.log(Level.INFO, "Game has been loaded.");
            gameState.setRunning();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Saved game couldn't be loaded.");
            throw new RuntimeException(e);
        }
    }

    /**
     * Performs the fight logic between the player and the enemy.
     */
    private void fight() {
        Enemy enemy = gameState.getOpponent();
        particleHandler.update();

        if (player.fightUpdate(enemy)) {
            particleHandler.addHitParticle(enemy.getWorldCoordinates());
        }

        if (enemy.fightUpdate(player)) {
            particleHandler.addHitParticle(player.getWorldCoordinates());
        }

        if (player.getHealth() <= 0) {
            gameState.setDying();
            player.setDyingActivity();
        }

        if (enemy.getHealth() <= 0) {
            enemy.setDyingActivity();
            gameState.setRunning();
        }
    }

    /**
     * Handles the player's death logic.
     */
    private void die() {
        if (player.deathUpdate()) {
            LOGGER.log(Level.INFO, "GAME OVER.");
            gameState.setEnd();
        }
    }

    /**
     * Starts a new game.
     */
    private void startNewGame() {
        LOGGER.log(Level.INFO, "Starting new game.");
        loadNewGame();
        gameState.setRunning();
    }

    /**
     * Loads a new game.
     */
    private void loadNewGame() {
        try {
            LOGGER.log(Level.INFO, "Loading new game...");
            Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/maps/map1/settings.json"));

            deserializeAll(reader);

            reader.close();

            gameState.setRunning();

            reader.close();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Map or its settings weren't found in the game files.");
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserializes the entire game data from a JSON object.
     *
     * @param reader The reader containing the JSON object.
     */
    public void deserializeAll(Reader reader) {
        LOGGER.log(Level.INFO, "Deserializing game file...");
        try {
            JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
            player.deserializeAndSetPlayer((JsonObject) parser.get("player"));
            enemiesHandler.deserializeEnemies((JsonArray) parser.get("enemies"));
            objectHandler.deserializeObjects((JsonArray) parser.get("objects"));
        } catch (JsonException e) {
            LOGGER.log(Level.SEVERE, "Game couldn't be deserialized.");
            throw new RuntimeException(e);
        }
    }

}
