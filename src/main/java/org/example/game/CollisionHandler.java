package org.example.game;

import org.example.entities.Entity;
import org.example.entities.types.ActivityType;
import org.example.entities.types.EntityType;
import org.example.gameobjects.GameObject;
import org.example.tiles.TileHandler;

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

    //TODO iterator over objects and remove those, that have intersecting solid areas, video
    public void checkObject(Entity entity) {
        Iterator<GameObject> objectsIter = objects.iterator();

        while (objectsIter.hasNext()) {
            GameObject item = objectsIter.next();

            entity.setSolidAreaX(entity.getWorldX() + entity.getSolidArea().x);
            entity.setSolidAreaY(entity.getWorldY() + entity.getSolidArea().y);

            item.setSolidAreaX(item.getWorldX() + item.getSolidArea().x);
            item.setSolidAreaY(item.getWorldY() + item.getSolidArea().y);

            shiftSolidArea(entity);

            if (entity.getSolidArea().intersects(item.getSolidArea())) {
                if (item.hasCollisions()) {
                    entity.setCollisionsOn(true);
                }
                if (entity.getEntityType().equals(EntityType.HERO)) {
                    objectsIter.remove();
                    return;
                }
            }

            entity.setDefaultSolidArea();
            item.setDefaultSolidArea();
        }
    }

    public void shiftSolidArea(Entity entity) {
        switch (entity.getVerticalDirection()) {
            case UP ->  entity.setSolidAreaY(entity.getSolidArea().y - entity.getSpeed());
            case DOWN -> entity.setSolidAreaY(entity.getSolidArea().y + entity.getSpeed());
            case NONE -> {
                switch (entity.getHorizontalDirection()) {
                    case LEFT -> entity.setSolidAreaX(entity.getSolidArea().x - entity.getSpeed());
                    case RIGHT -> entity.setSolidAreaX(entity.getSolidArea().x + entity.getSpeed());
                }
            }
        }
    }
}
