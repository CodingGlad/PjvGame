package org.example.handlers;

import org.example.entities.Enemy;
import org.example.entities.Player;
import org.example.entities.types.EntityType;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static org.example.utils.GameConstants.TILE_SIZE;

public class EnemiesHandler {
    private List<Enemy> enemies;

    public EnemiesHandler() {
        enemies = new LinkedList<>();
        setDefaultEnemies();
    }

    private void setDefaultEnemies() {
        enemies.add(new Enemy(EntityType.DEMON));
    }

    public void update() {
        for (Enemy enemy: enemies) {
            enemy.update();
        }
    }

    public void drawEnemies(Graphics2D g2, Player player) {
        for (Enemy enemy: enemies) {
            final int screenX = enemy.getWorldX() - player.getWorldX() + player.getScreenX();
            final int screenY = enemy.getWorldY() - player.getWorldY() + player.getScreenY();

            if (shouldEnemyBeRendered(enemy.getWorldX(), enemy.getWorldY(), player)) {
                enemy.draw(g2, screenX, screenY);
            }
        }
    }

    private boolean shouldEnemyBeRendered(int worldX, int worldY, Player player) {
        return isCoordinateWithinViewX(worldX, player) && isCoordinateWithinViewY(worldY, player);
    }

    private boolean isCoordinateWithinViewX(int worldX, Player player) {
        return worldX + TILE_SIZE > (player.getWorldX() - player.getScreenX()) &&
                worldX - TILE_SIZE < (player.getWorldX() + player.getScreenX());
    }

    private boolean isCoordinateWithinViewY(int worldY, Player player) {
        return worldY + TILE_SIZE > (player.getWorldY() - player.getScreenY()) &&
                worldY - TILE_SIZE < (player.getWorldY() + player.getScreenY());
    }
}
