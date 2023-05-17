package org.example.entities;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.entities.types.ActivityType;
import org.example.entities.types.EntityType;
import org.example.utils.WorldCoordinates;

import java.awt.*;

public class Enemy extends Entity {
    public Enemy(EntityType entityType, WorldCoordinates worldCoordinates) {
        super(worldCoordinates, entityType);
    }

    public void update() {
        if (!getActivityType().equals(ActivityType.DYING)) {
            incrementCounters();
        } else {
            incrementDeathCounter();
        }
    }
    public boolean fightUpdate(Player player) {
        incrementCounters();
        if (attack()) {
            player.takeDamage(getAttackDamage());
            return true;
        }

        return false;
    }

    public void draw(Graphics2D g2, int coordX, int coordY) {
        super.drawEntity(g2, coordX, coordY);
    }

    public JsonObject serializeEnemy() {
        return super.serializeEntity();
    }

    public static Enemy deserializeAndSetEnemy(JsonObject json) {
        Enemy jsonEnemy = new Enemy(EntityType.valueOf((String) json.get("entitytype")), new WorldCoordinates(1, 1));
        jsonEnemy.deserializeAndSetEntity(json);

        return jsonEnemy;
    }
}
