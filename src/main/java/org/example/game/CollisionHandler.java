package org.example.game;

import org.example.entities.Entity;
import org.example.entities.types.ActivityType;
import org.example.entities.types.HorizontalDirectionType;
import org.example.entities.types.VerticalDirectionType;
import org.example.tiles.TileHandler;

public class CollisionHandler {

    private TileHandler tileHandler;

    public CollisionHandler(TileHandler tileHandler) {
        this.tileHandler = tileHandler;
    }

    public void checkCollisions(Entity entity) {
        if (entity.getActivityType().equals(ActivityType.WALK)) {
            if (entity.getVerticalDirection().equals(VerticalDirectionType.NONE)) {
                checkHorizontalCollisions(entity);
            } else {
                checkVerticalCollisions(entity);
            }
        }
    }

    private void checkVerticalCollisions(Entity entity) {
        int directionRow;

        if (entity.getVerticalDirection().equals(VerticalDirectionType.UP)) {
            directionRow = tileHandler.getRowOrColumnOfCoordinate(entity.getSolidTopWorldY() - entity.getSpeed());
        } else {
            directionRow = tileHandler.getRowOrColumnOfCoordinate(entity.getSolidBottomWorldY() + entity.getSpeed());
        }

        checkTilesInVerticalDirection(directionRow, tileHandler.getRowOrColumnOfCoordinate(entity.getSolidLeftWorldX()),
                tileHandler.getRowOrColumnOfCoordinate(entity.getSolidRightWorldX()), entity);
    }

    private void checkHorizontalCollisions(Entity entity) {
        int directionColumn;

        if (entity.getHorizontalDirection().equals(HorizontalDirectionType.LEFT)) {
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
}
