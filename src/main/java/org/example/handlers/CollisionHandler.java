package org.example.handlers;

import org.example.entities.Enemy;
import org.example.entities.Entity;
import org.example.entities.Player;
import org.example.entities.types.ActivityType;
import org.example.entities.types.EntityType;
import org.example.gameobjects.*;
import org.example.gameobjects.types.ObjectType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static org.example.entities.types.HorizontalDirectionType.*;
import static org.example.entities.types.VerticalDirectionType.*;

/**
 * The CollisionHandler class handles collisions between entities and objects in the game.
 */
public class CollisionHandler {

    private final TileHandler tileHandler;
    private final GameStateHandler gameState;
    private List<GameObject> objects;
    private List<GameObject> objectsToAppend;
    private List<Enemy> enemies;

    /**
     * Constructs a CollisionHandler object with the specified dependencies.
     *
     * @param tileHandler The TileHandler used to handle tile-related operations.
     * @param gameState   The GameStateHandler used to manage the game state.
     * @param objects     The list of GameObjects in the game.
     * @param enemies     The list of Enemies in the game.
     */
    public CollisionHandler(TileHandler tileHandler, GameStateHandler gameState, List<GameObject> objects, List<Enemy> enemies) {
        this.tileHandler = tileHandler;
        this.objects = objects;
        this.enemies = enemies;
        this.gameState = gameState;
        this.objectsToAppend = new ArrayList<>();
    }

    /**
     * Checks collisions for the given entity.
     *
     * @param entity The entity for which to check collisions.
     */
    public void checkCollisions(Entity entity) {
        if (entity.getActivityType().equals(ActivityType.WALK)) {
            if (entity.getVerticalDirection().equals(NONE)) {
                checkHorizontalCollisions(entity);
            } else {
                checkVerticalCollisions(entity);
            }
        }
    }

    /**
     * Checks for collisions in the vertical direction for the given entity.
     *
     * @param entity The entity for which to check vertical collisions.
     */
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

    /**
     * Checks for collisions in the horizontal direction for the given entity.
     *
     * @param entity The entity for which to check horizontal collisions.
     */
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

    /**
     * Checks if tiles in the specified directions have collisions.
     *
     * @param tileInDirection1 The tile number in the first direction.
     * @param tileInDirection2 The tile number in the second direction.
     * @return True if at least one of the tiles has collisions, false otherwise.
     */
    private boolean doTilesHaveCollisions(int tileInDirection1, int tileInDirection2) {
        return (tileHandler.doesTileHaveCollision(tileInDirection1) || tileHandler.doesTileHaveCollision(tileInDirection2));
    }

    /**
     * Checks for collisions of tiles in the vertical direction.
     *
     * @param verticalDirectionRow The row index of the vertical direction.
     * @param entityLeftCol        The column index of the entity's left side.
     * @param entityRightCol       The column index of the entity's right side.
     * @param entity               The entity for which to check collisions.
     */
    private void checkTilesInVerticalDirection(int verticalDirectionRow, int entityLeftCol, int entityRightCol, Entity entity) {
        int tileInDirection1 = tileHandler.getTileNumber(verticalDirectionRow, entityLeftCol);
        int tileInDirection2 = tileHandler.getTileNumber(verticalDirectionRow, entityRightCol);
        checkCollisionsOfTilesInDirection(tileInDirection1, tileInDirection2, entity);
    }

    /**
     * Checks for collisions of tiles in the horizontal direction.
     *
     * @param horizontalDirectionColumn The column index of the horizontal direction.
     * @param entityTopRow              The row index of the entity's top side.
     * @param entityBottomRow           The row index of the entity's bottom side.
     * @param entity                    The entity for which to check collisions.
     */
    private void checkTilesInHorizontalDirection(int horizontalDirectionColumn, int entityTopRow, int entityBottomRow, Entity entity) {
        int tileInDirection1 = tileHandler.getTileNumber(entityTopRow, horizontalDirectionColumn);
        int tileInDirection2 = tileHandler.getTileNumber(entityBottomRow, horizontalDirectionColumn);
        checkCollisionsOfTilesInDirection(tileInDirection1, tileInDirection2, entity);
    }

    /**
     * Checks collisions between tiles in a specific direction and updates the entity's collision state.
     *
     * @param tileInDirection1 The tile number in the first direction.
     * @param tileInDirection2 The tile number in the second direction.
     * @param entity           The entity for which to check collisions.
     */
    private void checkCollisionsOfTilesInDirection(int tileInDirection1, int tileInDirection2, Entity entity) {
        if (doTilesHaveCollisions(tileInDirection1, tileInDirection2)) {
            entity.setCollisionsOn(true);
        }
    }

    /**
     * Checks collisions between the player entity and objects in the game.
     *
     * @param entity              The player entity.
     * @param isEquipButtonPressed Indicates whether the equip button is pressed.
     * @param isUseButtonPressed   Indicates whether the use button is pressed.
     */
    public void checkObject(Player entity, boolean isEquipButtonPressed, boolean isUseButtonPressed) {
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

            if (handleIntersection(entitySolidAreaWorld, entity, itemSolidAreaWorld, item,
                    isEquipButtonPressed, isUseButtonPressed)) {
                if (entity.getEntityType().equals(EntityType.HERO) && item.getObjectType().equals(ObjectType.KEY)) {
                    (entity).incrementKeys();
                }

                objectsIter.remove();
            }
        }

        objects.addAll(objectsToAppend);
        objectsToAppend = new ArrayList<>();
    }

    /**
     * Checks for intersection between the solid area of an entity and an item.
     *
     * @param entitySolidAreaWorld  The solid area of the entity in the world.
     * @param entity               The entity involved in the intersection.
     * @param itemSolidAreaWorld   The solid area of the item in the world.
     * @param item                 The item involved in the intersection.
     * @param isEquipButtonPressed Indicates whether the equip button is pressed.
     * @param isUseButtonPressed   Indicates whether the use button is pressed.
     * @return True if there is an intersection and the entity can pick up the item, false otherwise.
     */
    public boolean handleIntersection(Rectangle entitySolidAreaWorld, Player entity, Rectangle itemSolidAreaWorld,
                                      GameObject item, boolean isEquipButtonPressed, boolean isUseButtonPressed) {
        if (entitySolidAreaWorld.intersects(itemSolidAreaWorld)) {
            if (item.hasCollisions()) {
                entity.setCollisionsOn(true);
            }

            return canEntityPickUpThisObject(entity, item, isEquipButtonPressed, isUseButtonPressed);
        }

        return false;
    }

    /**
     * Checks for intersection between the solid areas of a player and an enemy.
     *
     * @param playerSolidAreaWorld The solid area of the player in the world.
     * @param enemySolidAreaWorld  The solid area of the enemy in the world.
     * @return True if the solid areas intersect, false otherwise.
     */
    public boolean handleIntersection(Rectangle playerSolidAreaWorld, Rectangle enemySolidAreaWorld) {
        return playerSolidAreaWorld.intersects(enemySolidAreaWorld);
    }

    /**
     * Checks if the entity can pick up the given object based on its type and button presses.
     *
     * @param entity               The entity trying to pick up the object.
     * @param object               The object to be picked up.
     * @param isEquipButtonPressed Indicates whether the equip button is pressed.
     * @param isUseButtonPressed   Indicates whether the use button is pressed.
     * @return True if the entity can pick up the object, false otherwise.
     */
    public boolean canEntityPickUpThisObject(Player entity, GameObject object,
                                             boolean isEquipButtonPressed, boolean isUseButtonPressed) {
        switch (object.getObjectType()) {
            case KEY -> {
                return true;
            }

            case CHEST_CLOSED -> {
                if (object.getObjectType().equals(ObjectType.CHEST_CLOSED) &&
                        entity.getNumberOfKeys() > 0 && isUseButtonPressed) {

                    objectsToAppend.add(new Chest(
                            ObjectType.CHEST_OPENED,
                            ((Chest) object).getChestType(),
                            object.getWorldCoordinates()));

                    objectsToAppend.add(((Chest) object).generateLoot());

                    entity.decrementKeys();
                    return true;
                }
                return false;
            }

            case ARMOR -> {
                if (isEquipButtonPressed) {
                    Armor droppedArmor = entity.equipArmor((Armor) object);
                    if (Objects.nonNull(droppedArmor)) {
                        objectsToAppend.add(droppedArmor);
                    }
                    return true;
                }
            }

            case WEAPON -> {
                if (isEquipButtonPressed) {
                    Weapon droppedWeapon = entity.equipWeapon((Weapon) object);
                    if (Objects.nonNull(droppedWeapon)) {
                        objectsToAppend.add(droppedWeapon);
                    }
                    return true;
                }
            }

            case HEART -> {
                entity.restorePlayersHealth(((Heart) object).restoreHealth());
                return true;
            }
        }
        return false;
    }

    /**
     * Shifts the solid area of an entity based on its movement direction.
     *
     * @param entitySolidArea The solid area of the entity to be shifted.
     * @param entity          The entity whose solid area is being shifted.
     * @param scaleFactor    The scale factor determining the amount of shift.
     */
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

    /**
     * Checks for intersections between the player and enemies, and handles the appropriate game state changes.
     *
     * @param player The player entity.
     */
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
