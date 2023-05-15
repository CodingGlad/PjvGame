package org.example.handlers;

import org.example.entities.Enemy;
import org.example.entities.Entity;
import org.example.entities.Player;
import org.example.entities.types.ActivityType;
import org.example.entities.types.EntityType;
import org.example.gameobjects.Armor;
import org.example.gameobjects.Chest;
import org.example.gameobjects.GameObject;
import org.example.gameobjects.Weapon;
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

    public void checkObject(Player entity, boolean isEquipButtonPressed) {
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

            if (handleIntersection(entitySolidAreaWorld, entity, itemSolidAreaWorld, item, isEquipButtonPressed)) {
                if (entity.getEntityType().equals(EntityType.HERO) && item.getObjectType().equals(ObjectType.KEY)) {
                    (entity).incrementKeys();
                }

                objectsIter.remove();
                return;
            }
        }
    }

    public boolean handleIntersection(Rectangle entitySolidAreaWorld, Player entity, Rectangle itemSolidAreaWorld,
                                      GameObject item, boolean isEquipButtonPressed) {
        if (entitySolidAreaWorld.intersects(itemSolidAreaWorld)) {
            if (item.hasCollisions()) {
                entity.setCollisionsOn(true);
            }

            return canEntityPickUpThisObject(entity, item, isEquipButtonPressed);
        }

        return false;
    }

    public boolean handleIntersection(Rectangle playerSolidAreaWorld, Rectangle enemySolidAreaWorld) {
        return playerSolidAreaWorld.intersects(enemySolidAreaWorld);
    }

    public boolean canEntityPickUpThisObject(Player entity, GameObject object, boolean isEquipButtonPressed) {
        switch (object.getObjectType()) {
            case KEY -> {
                return true;
            }
            case CHEST -> {
                if (((Chest) object).getStateType().equals(ChestStateType.CLOSED) &&
                        entity.getNumberOfKeys() > 0) {
                    ((Chest) object).openChest();
                    entity.decrementKeys();
                }
                return false;
            }
            case ARMOR -> {
                if (isEquipButtonPressed) {
                    entity.equipArmor((Armor) object);
                    return true;
                }
            }
            case WEAPON -> {
                if (isEquipButtonPressed) {
                    entity.equipWeapon((Weapon) object);
                }
            }
//              case HEART ->; TODO
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
        for (Enemy en: enemies.stream().filter(en -> !en.getActivityType().equals(ActivityType.DYING)).toList()) {
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
