package org.example.handlers;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.entities.Enemy;
import org.example.entities.Player;
import org.example.entities.types.EntityType;
import org.example.utils.WorldCoordinates;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.utils.GameConstants.TILE_SIZE;

/**
 * The EnemiesHandler class manages the collection of enemies in the game.
 * It provides methods to add, update, and draw enemies, as well as serialize and deserialize enemy data.
 */
public class EnemiesHandler {

    private static final Logger LOGGER = Logger.getLogger(EnemiesHandler.class.getName());
    private List<Enemy> enemies;

    /**
     * Constructs a new EnemiesHandler object with an empty list of enemies.
     */
    public EnemiesHandler() {
        enemies = new LinkedList<>();
    }

    /**
     * Sets the default enemies for the game.
     * Adds a default enemy of type EntityType.DEMON with predefined world coordinates to the list of enemies.
     */
    public void setDefaultEnemies() {
        enemies.add(new Enemy(EntityType.DEMON, new WorldCoordinates(TILE_SIZE * 23, TILE_SIZE * 39)));
    }

    /**
     * Updates all the enemies in the list.
     * This method should be called in the game's update loop to update the enemies' states.
     */
    public void update() {
        for (Enemy enemy : enemies) {
            enemy.update();
        }
    }

    /**
     * Draws the enemies on the screen based on the player's position.
     * Only the enemies that should be rendered within the view are drawn.
     *
     * @param g2     The Graphics2D object used for drawing.
     * @param player The player object representing the player's position.
     */
    public void drawEnemies(Graphics2D g2, Player player) {
        for (Enemy enemy : enemies) {
            final int screenX = enemy.getWorldX() - player.getWorldX() + player.getScreenX();
            final int screenY = enemy.getWorldY() - player.getWorldY() + player.getScreenY();

            if (shouldEnemyBeRendered(enemy.getWorldX(), enemy.getWorldY(), player)) {
                enemy.draw(g2, screenX, screenY);
            }
        }
    }

    private boolean shouldEnemyBeRendered(int worldX, int worldY, Player player) {
        // Check if the enemy's world coordinates are within the player's view
        return isCoordinateWithinViewX(worldX, player) && isCoordinateWithinViewY(worldY, player);
    }

    private boolean isCoordinateWithinViewX(int worldX, Player player) {
        // Check if the enemy's X coordinate is within the player's view range
        return worldX + TILE_SIZE > (player.getWorldX() - player.getScreenX()) &&
                worldX - TILE_SIZE < (player.getWorldX() + player.getScreenX());
    }

    private boolean isCoordinateWithinViewY(int worldY, Player player) {
        // Check if the enemy's Y coordinate is within the player's view range
        return worldY + TILE_SIZE > (player.getWorldY() - player.getScreenY()) &&
                worldY - TILE_SIZE < (player.getWorldY() + player.getScreenY());
    }

    /**
     * Returns the list of enemies.
     *
     * @return The list of enemies.
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Serializes the enemies into an array of objects.
     *
     * @return An array of serialized enemy objects.
     */
    public Object[] serializeEnemies() {
        LOGGER.log(Level.INFO, "Serializing enemies...");
        return enemies.stream().map(Enemy::serializeEnemy).toArray();
    }

    /**
     * Deserializes the enemy data from the given JSON array and adds the enemies to the list.
     *
     * @param jsonEnemies The JSON array containing the serialized enemy objects.
     */
    public void deserializeEnemies(JsonArray jsonEnemies) {
        LOGGER.log(Level.INFO, "Deserializing enemies...");
        jsonEnemies.forEach(en -> addDeserializedEnemy((JsonObject) en));
    }

    /**
     * Deserializes the enemy data from the given JSON object and adds the enemy to the list.
     *
     * @param json The JSON object containing the serialized enemy data.
     */
    private void addDeserializedEnemy(JsonObject json) {
        enemies.add(Enemy.deserializeAndSetEnemy(json));
    }
}
