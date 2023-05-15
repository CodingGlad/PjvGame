package org.example.entities;

import org.example.entities.types.ActivityType;
import org.example.entities.types.EntityType;
import org.example.utils.WorldCoordinates;

import java.awt.*;

import static org.example.utils.GameConstants.TILE_SIZE;

public class Enemy extends Entity {
    //TODO handle coords
    public Enemy(EntityType entityType) {
        super(new WorldCoordinates(TILE_SIZE * 24, TILE_SIZE * 22), 4, 2, entityType);
    }

    public void update() {
        if (!getActivityType().equals(ActivityType.DYING)) {
            incrementCounters();
        } else {
            incrementDeathCounter();
        }
    }
    public void fightUpdate(Player player) {
        incrementCounters();
        if (attack()) {
            player.takeDamage(getAttackDamage());
        }
    }

    public void draw(Graphics2D g2, int coordX, int coordY) {
        super.drawEntity(g2, coordX, coordY);
    }

}
