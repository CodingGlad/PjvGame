package org.example.game;

import org.example.entities.Entity;
import org.example.tiles.TileHandler;

import static org.example.game.GamePanel.TILE_SIZE;

public class CollisionHandler {

    private TileHandler tileHandler;

    public CollisionHandler(TileHandler tileHandler) {
        this.tileHandler = tileHandler;
    }

    public void checkTile(Entity entity) {
        //SOLID AREA COORDS
        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int entityLeftCol = entityLeftWorldX/TILE_SIZE;
        int entityRightCol = entityRightWorldX/TILE_SIZE;
        int entityTopRow = entityTopWorldY/TILE_SIZE;
        int entityBottomRow = entityBottomWorldY/TILE_SIZE;

        int tilenum1, tilenum2;

        switch (entity.getVerticalDirection()) {
            case TOP -> {
                int directionRow = (entityTopWorldY - entity.getSpeed()) / TILE_SIZE;
                tilenum1 = tileHandler.getTileNumber(directionRow, entityLeftCol);
                tilenum2 = tileHandler.getTileNumber(directionRow, entityRightCol);
                System.out.println(tilenum1 + " " + tilenum2);
                if (doTilesHaveCollisions(tilenum1, tilenum2)) {
                    entity.setCollisionsOn(true);
                }
            }
            case DOWN -> {
                int directionRow = (entityBottomWorldY + entity.getSpeed()) / TILE_SIZE;
                tilenum1 = tileHandler.getTileNumber(directionRow, entityLeftCol);
                tilenum2 = tileHandler.getTileNumber(directionRow, entityRightCol);
                System.out.println(tilenum1 + " " + tilenum2);
                if (doTilesHaveCollisions(tilenum1, tilenum2)) {
                    entity.setCollisionsOn(true);
                }
            }
            case NONE -> {
                switch (entity.getHorizontalDirection()) {
                    case LEFT -> {
                        int directionColumn = (entityLeftWorldX - entity.getSpeed()) / TILE_SIZE;
                        tilenum1 = tileHandler.getTileNumber(entityTopRow, directionColumn);
                        tilenum2 = tileHandler.getTileNumber(entityBottomRow, directionColumn);
                        System.out.println(tilenum1 + " " + tilenum2);
                        if (doTilesHaveCollisions(tilenum1, tilenum2)) {
                            entity.setCollisionsOn(true);
                        }
                    }
                    case RIGHT -> {
                        int directionColumn = (entityRightWorldX + entity.getSpeed()) / TILE_SIZE;
                        tilenum1 = tileHandler.getTileNumber(entityTopRow, directionColumn);
                        tilenum2 = tileHandler.getTileNumber(entityBottomRow, directionColumn);
                        System.out.println(tilenum1 + " " + tilenum2);
                        if (doTilesHaveCollisions(tilenum1, tilenum2)) {
                            entity.setCollisionsOn(true);
                        }
                    }
                }
            }
        }
    }

    private boolean doTilesHaveCollisions(int tilenum1, int tilenum2) {
        return (tileHandler.doesTileHaveCollision(tilenum1) || tileHandler.doesTileHaveCollision(tilenum2));
    }
}
