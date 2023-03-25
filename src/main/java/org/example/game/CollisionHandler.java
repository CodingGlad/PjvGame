package org.example.game;

import org.example.entities.Entity;
import org.example.tiles.TileHandler;

import static org.example.game.GamePanel.TILE_SIZE;

public class CollisionHandler {

    private TileHandler tileHandler;

    public CollisionHandler(TileHandler tileHandler) {
        this.tileHandler = tileHandler;
    }

    public void checkCollisions(Entity entity) {
        switch (entity.getVerticalDirection()) {
            case TOP -> {
                int directionRow = (entity.getTopWorldY() - entity.getSpeed()) / TILE_SIZE;
                checkTilesInVerticalDirection(directionRow, entity.getTilesRowOrColumn(entity.getLeftWorldX()),
                        entity.getTilesRowOrColumn(entity.getRightWorldX()), entity);
            }
            case DOWN -> {
                int directionRow = (entity.getBottomWorldY() + entity.getSpeed()) / TILE_SIZE;
                checkTilesInVerticalDirection(directionRow, entity.getTilesRowOrColumn(entity.getLeftWorldX()),
                        entity.getTilesRowOrColumn(entity.getRightWorldX()), entity);
            }
            case NONE -> {
                switch (entity.getHorizontalDirection()) {
                    case LEFT -> {
                        int directionColumn = (entity.getLeftWorldX() - entity.getSpeed()) / TILE_SIZE;
                        checkTilesInHorizontalDirection(directionColumn, entity.getTilesRowOrColumn(entity.getTopWorldY()),
                                entity.getTilesRowOrColumn(entity.getBottomWorldY()), entity);
                    }
                    case RIGHT -> {
                        int directionColumn = (entity.getRightWorldX() + entity.getSpeed()) / TILE_SIZE;
                        checkTilesInHorizontalDirection(directionColumn, entity.getTilesRowOrColumn(entity.getTopWorldY()),
                                entity.getTilesRowOrColumn(entity.getBottomWorldY()), entity);
                    }
                }
            }
        }
    }

    private boolean doTilesHaveCollisions(int tilenum1, int tilenum2) {
        return (tileHandler.doesTileHaveCollision(tilenum1) || tileHandler.doesTileHaveCollision(tilenum2));
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
