package org.example.gameobjects;

import org.example.entities.Player;
import org.example.gameobjects.types.ObjectType;
import org.example.utils.WorldCoordinates;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.handlers.WindowHandler.TILE_SIZE;

public abstract class GameObject {
    private static final int DEFAULT_SOLID_X = 0;
    private static final int DEFAULT_SOLID_Y = 0;

    private final ObjectType objectType;
    private BufferedImage staticImage;
    private WorldCoordinates worldCoordinates;
    private Rectangle solidArea = new Rectangle(DEFAULT_SOLID_X, DEFAULT_SOLID_Y, TILE_SIZE, TILE_SIZE);

    protected GameObject(ObjectType objectType, WorldCoordinates worldCoordinates) {
        this.objectType = objectType;
        this.worldCoordinates = worldCoordinates;
    }

    public void setStaticImage(BufferedImage staticImage) {
        this.staticImage = staticImage;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public int getWorldX() {
        return worldCoordinates.getWorldX();
    }

    public int getWorldY() {
        return worldCoordinates.getWorldY();
    }

    public boolean hasCollisions() {
        for (ObjectType type: ObjectType.values()) {
            if (type.equals(objectType)) {
                return type.hasCollisions();
            }
        }

        throw new IllegalStateException("Object type not found in enum> " + this.toString());
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public BufferedImage getStaticImage() {
        return staticImage;
    }
}
