package org.example.handlers;

import org.example.entities.Enemy;
import org.example.entities.Entity;
import org.example.entities.Player;
import org.example.entities.types.ActivityType;
import org.example.entities.types.EntityType;
import org.example.gameobjects.Chest;
import org.example.gameobjects.GameObject;
import org.example.gameobjects.types.ChestStateType;
import org.example.gameobjects.types.ObjectType;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

import static org.example.entities.types.HorizontalDirectionType.*;
import static org.example.entities.types.VerticalDirectionType.*;

public class CollisionHandler {

    private final TileHandler tileHandler;
    private final GameStateHandler gameState;
    private List<GameObject> objects;
    private List<Enemy> enemies;

    public CollisionHandler(TileHandler tileHandler, GameStateHandler gameState, List<GameObject> objects, List<Enemy> enemies) {
        this.tileHandler = tileHandler;
        this.objects = objects;
        this.enemies = enemies;
        this.gameState = gameState;
    }

    public void checkCollisions(Entity entity) {
        if (entity.getActivityType().equals(ActivityType.WALK)) {
            if (entity.getVerticalDirection().equals(NONE)) {
                checkHorizontalCollisions(entity);
            } else {
                checkVerticalCollisions(entity);
            }
        }
    }

    private void checkVerticalCollisions(Entity entity) {
        int directionRow;

        if (entity.getVerticalDirection().equals(UP)) {
            directionRow = tileHandler.getRowOrColumnOfCoordinate(entity.getSolidTopWorldY() - entity.getSpeed());
        } else {
            directionRow = tileHandler.getRowOrColumnOfCoordinate(entity.getSolidBottomWorldY() + entity.getSpeed());
        }

        checkTilesInVerticalDirection(directionRow, tileHandler.getRowOrColumnOfCoordinate(entity.getSolidLeftWorldX()),
                tileHandler.getRowOrColumnOfCoordinate(entity.getSolidRightWorldX()), entity);
    }

    private void checkHorizontalCollisions(Entity entity) {
        int directionColumn;

        if (entity.getHorizontalDirection().equals(LEFT)) {
            directionColumn = tileHandler.getRowOrColumnOfCoordinate(entity.getSolidLeftWorldX() - entity.getSpeed());
        } else {
            directionColumn = tileHandler.getRowOrColumnOfCoordinate(entity.getSolidRightWorldX() + entity.getSpeed());
        }

        checkTilesInHorizontalDirection(directionColumn, tileHandler.getRowOrColumnOfCoordinate(entity.getSolidTopWorldY()),
                tileHandler.getRowOrColumnOfCoordinate(entity.getSolidBottomWorldY()), entity);
    }

    private boolean doTilesHaveCollisions(int tileInDirection1, int tileInDirection2) {
        return (tileHandler.doesTileHaveCollision(tileInDirection1) || tileHandler.doesTileHaveCollision(tileInDirection2));
    }

    private void checkTilesInVerticalDirection(int verticalDirectionRow, int entityLeftCol, int entityRightCol, Entity entity) {
        int tileInDirection1 = tileHandler.getTileNumber(verticalDirectionRow, entityLeftCol);
        int tileInDirection2 = tileHandler.getTileNumber(verticalDirectionRow, entityRightCol);
        checkCollisionsOfTilesInDirection(tileInDirection1, tileInDirection2, entity);
    }

    private void checkTilesInHorizontalDirection(int horizontalDirectionColumn, int entityTopRow, int entityBottomRow, Entity entity) {
        int tileInDirection1 = tileHandler.getTileNumber(entityTopRow, horizontalDirectionColumn);
        int tileInDirection2 = tileHandler.getTileNumber(entityBottomRow, horizontalDirectionColumn);
        checkCollisionsOfTilesInDirection(tileInDirection1, tileInDirection2, entity);
    }

    private void checkCollisionsOfTilesInDirection(int tileInDirection1, int tileInDirection2, Entity entity) {
        if (doTilesHaveCollisions(tileInDirection1, tileInDirection2)) {
            entity.setCollisionsOn(true);
        }
    }

    public void checkObject(Entity entity) {
        Iterator<GameObject> objectsIter = objects.iterator();

        while (objectsIter.hasNext()) {
            GameObject item = objectsIter.next();

            Rectangle entitySolidAreaWorld = new Rectangle(
                    entity.getSolidArea().x + entity.getWorldX(),
                    entity.getSolidArea().y + entity.getWorldY(),
                    entity.getSolidArea().width, entity.getSolidArea().height);

            Rectangle itemSolidAreaWorld = new Rectangle(
                    item.getSolidArea().x + item.getWorldX(),
                    item.getSolidArea().y + item.getWorldY(),
                    item.getSolidArea().width, item.getSolidArea().height);

            shiftSolidArea(entitySolidAreaWorld, entity, 1);

            if (handleIntersection(entitySolidAreaWorld, entity, itemSolidAreaWorld, item)) {
                if (entity.getEntityType().equals(EntityType.HERO) && item.getObjectType().equals(ObjectType.KEY)) {
                    ((Player)entity).incrementKeys();
                }

                objectsIter.remove();
                return;
            }
        }
    }

    public boolean handleIntersection(Rectangle entitySolidAreaWorld, Entity entity, Rectangle itemSolidAreaWorld, GameObject item) {
        if (entitySolidAreaWorld.intersects(itemSolidAreaWorld)) {
            if (item.hasCollisions()) {
                entity.setCollisionsOn(true);
            }

            return canEntityPickUpThisObject(entity, item);
        }

        return false;
    }

    public boolean handleIntersection(Rectangle playerSolidAreaWorld, Rectangle enemySolidAreaWorld) {
        return playerSolidAreaWorld.intersects(enemySolidAreaWorld);
    }

    public boolean canEntityPickUpThisObject(Entity entity, GameObject object) {
        if (entity.getEntityType().equals(EntityType.HERO)) {
            if (object.getObjectType().equals(ObjectType.KEY)) {
                return true;
            } else if (object.getObjectType().equals(ObjectType.CHEST)) {
                if (((Chest) object).getStateType().equals(ChestStateType.CLOSED) &&
                        ((Player) entity).getNumberOfKeys() > 0) {
                    ((Chest) object).openChest();
                    ((Player) entity).decrementKeys();
                }
                return false;
            }
        }

        return false;
    }

    public void shiftSolidArea(Rectangle entitySolidArea, Entity entity, int scaleFactor) {
        switch (entity.getVerticalDirection()) {
            case UP ->  entitySolidArea.y = (entitySolidArea.y - entity.getSpeed() * scaleFactor);
            case DOWN -> entitySolidArea.y = (entitySolidArea.y + entity.getSpeed() * scaleFactor);
            case NONE -> {
                switch (entity.getHorizontalDirection()) {
                    case LEFT -> entitySolidArea.x = (entitySolidArea.x - entity.getSpeed() * scaleFactor);
                    case RIGHT -> entitySolidArea.x = (entitySolidArea.x + entity.getSpeed() * scaleFactor);
                }
            }
        }
    }

    public void checkEnemies(Entity player) {
        for (Enemy en: enemies) {
            Rectangle enemySolidAreaWorld = new Rectangle(
                    en.getVisibleArea().x + en.getWorldX(),
                    en.getVisibleArea().y + en.getWorldY(),
                    en.getVisibleArea().width, en.getVisibleArea().height);

            Rectangle playerSolidAreaWorld = new Rectangle(
                    player.getVisibleArea().x + player.getWorldX(),
                    player.getVisibleArea().y + player.getWorldY(),
                    player.getVisibleArea().width, player.getVisibleArea().height);

            shiftSolidArea(playerSolidAreaWorld, player, 3);

            if (handleIntersection(playerSolidAreaWorld, enemySolidAreaWorld)) {
                gameState.setFighting(en, player);
                player.setIdleActivity();
                en.setIdleActivity();
                return;
            }
        }
    }
}
