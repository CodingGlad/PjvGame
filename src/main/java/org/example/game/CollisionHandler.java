package org.example.game;

import org.example.entities.Entity;
import org.example.entities.types.ActivityType;
import org.example.entities.types.EntityType;
import org.example.gameobjects.Chest;
import org.example.gameobjects.GameObject;
import org.example.gameobjects.types.ChestStateType;
import org.example.gameobjects.types.ObjectType;
import org.example.tiles.TileHandler;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

import static org.example.entities.types.HorizontalDirectionType.*;
import static org.example.entities.types.VerticalDirectionType.*;

public class CollisionHandler {

    private TileHandler tileHandler;
    private List<GameObject> objects;

    public CollisionHandler(TileHandler tileHandler, List<GameObject> objects) {
        this.tileHandler = tileHandler;
        this.objects = objects;
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

            shiftSolidArea(entitySolidAreaWorld, entity);

            if (handleIntersection(entitySolidAreaWorld, entity, itemSolidAreaWorld, item)) {
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

    //TODO mby refactor fr object methods (different service?)
    public boolean canEntityPickUpThisObject(Entity entity, GameObject object) {
        if (entity.getEntityType().equals(EntityType.HERO)) {
            if (object.getObjectType().equals(ObjectType.KEY)) {
                return true;
            }

            if (object.getObjectType().equals(ObjectType.CHEST)) {
                if (((Chest) object).getStateType().equals(ChestStateType.CLOSED)) {
                    ((Chest) object).openChest();
                }
                return false; // TODO handle how to work with its different states and it being an object
            }
        }

        return false;
    }

    public void test(Chest bitch) {
        System.out.println("kkt");
    }

    public void shiftSolidArea(Rectangle entitySolidArea, Entity entity) {
        switch (entity.getVerticalDirection()) {
            case UP ->  entitySolidArea.y = (entitySolidArea.y - entity.getSpeed());
            case DOWN -> entitySolidArea.y = (entitySolidArea.y + entity.getSpeed());
            case NONE -> {
                switch (entity.getHorizontalDirection()) {
                    case LEFT -> entitySolidArea.x = (entitySolidArea.x - entity.getSpeed());
                    case RIGHT -> entitySolidArea.x = (entitySolidArea.x + entity.getSpeed());
                }
            }
        }
    }
}
