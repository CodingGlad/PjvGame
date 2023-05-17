package org.example.entities;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.entities.types.ActivityType;
import org.example.entities.types.EntityType;
import org.example.utils.WorldCoordinates;

import java.awt.*;

/**
 * Represents an enemy entity that extends the Entity class.
 */
public class Enemy extends Entity {
    /**
     * Represents an enemy entity that extends the Entity class.
     */
    public Enemy(EntityType entityType, WorldCoordinates worldCoordinates) {
        super(worldCoordinates, entityType);
    }

    /**
     * Represents an enemy entity that extends the Entity class.
     */
    public void update() {
        if (!getActivityType().equals(ActivityType.DYING)) {
            incrementCounters();
        } else {
            incrementDeathCounter();
        }
    }

    /**
     * Updates the enemy during a fight with the player.
     * Increment counters and check if the enemy can attack.
     * If the enemy successfully attacks, the player takes damage.
     *
     * @param player the player entity
     * @return true if the enemy successfully attacked, false otherwise
     */
    public boolean fightUpdate(Player player) {
        incrementCounters();
        if (attack()) {
            player.takeDamage(getAttackDamage());
            return true;
        }

        return false;
    }

    /**
     * Draws the enemy entity on the specified graphics context.
     *
     * @param g2     the graphics context
     * @param coordX the x-coordinate for drawing
     * @param coordY the y-coordinate for drawing
     */
    public void draw(Graphics2D g2, int coordX, int coordY) {
        super.drawEntity(g2, coordX, coordY);
    }

    /**
     * Serializes the enemy entity to a JSON object.
     *
     * @return the serialized JSON object representing the enemy entity
     */
    public JsonObject serializeEnemy() {
        return super.serializeEntity();
    }

    /**
     * Deserializes a JSON object and creates an enemy entity with the specified values.
     *
     * @param json the JSON object containing the enemy entity data
     * @return the deserialized enemy entity
     */
    public static Enemy deserializeAndSetEnemy(JsonObject json) {
        Enemy jsonEnemy = new Enemy(EntityType.valueOf((String) json.get("entitytype")), new WorldCoordinates(1, 1));
        jsonEnemy.deserializeAndSetEntity(json);

        return jsonEnemy;
    }
}
