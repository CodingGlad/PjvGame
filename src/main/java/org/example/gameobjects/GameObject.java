package org.example.gameobjects;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.gameobjects.types.ObjectType;
import org.example.utils.WorldCoordinates;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.utils.GameConstants.TILE_SIZE;

/**
 * The abstract class representing a game object.
 */
public abstract class GameObject {
    private static final int DEFAULT_SOLID_X = 0;
    private static final int DEFAULT_SOLID_Y = 0;

    private final ObjectType objectType;
    private BufferedImage staticImage;
    private WorldCoordinates worldCoordinates;
    private Rectangle solidArea = new Rectangle(DEFAULT_SOLID_X, DEFAULT_SOLID_Y, TILE_SIZE, TILE_SIZE);

    /**
     * Constructs a new GameObject with the specified object type and world coordinates.
     *
     * @param objectType      the type of the game object
     * @param worldCoordinates the world coordinates of the game object
     */
    protected GameObject(ObjectType objectType, WorldCoordinates worldCoordinates) {
        this.objectType = objectType;
        this.worldCoordinates = worldCoordinates;
    }

    /**
     * Sets the static image of the game object.
     *
     * @param staticImage the static image of the game object
     */
    public void setStaticImage(BufferedImage staticImage) {
        this.staticImage = staticImage;
    }

    /**
     * Returns the solid area of the game object.
     *
     * @return the solid area of the game object
     */
    public Rectangle getSolidArea() {
        return solidArea;
    }

    /**
     * Returns the world X coordinate of the game object.
     *
     * @return the world X coordinate of the game object
     */
    public int getWorldX() {
        return worldCoordinates.getWorldX();
    }

    /**
     * Returns the world Y coordinate of the game object.
     *
     * @return the world Y coordinate of the game object
     */
    public int getWorldY() {
        return worldCoordinates.getWorldY();
    }

    /**
     * Checks if the game object has collisions.
     *
     * @return true if the game object has collisions, false otherwise
     * @throws IllegalStateException if the object type is not found in the ObjectType enum
     */
    public boolean hasCollisions() {
        for (ObjectType type : ObjectType.values()) {
            if (type.equals(objectType)) {
                return type.hasCollisions();
            }
        }

        throw new IllegalStateException("Object type not found in enum: " + this.toString());
    }

    /**
     * Returns the object type of the game object.
     *
     * @return the object type of the game object
     */
    public ObjectType getObjectType() {
        return objectType;
    }

    /**
     * Returns the static image of the game object.
     *
     * @return the static image of the game object
     */
    public BufferedImage getStaticImage() {
        return staticImage;
    }

    /**
     * Sets the world coordinates of the game object.
     *
     * @param worldCoordinates the world coordinates to set
     */
    public void setWorldCoordinates(WorldCoordinates worldCoordinates) {
        this.worldCoordinates = worldCoordinates;
    }

    /**
     * Returns the world coordinates of the game object.
     *
     * @return the world coordinates of the game object
     */
    public WorldCoordinates getWorldCoordinates() {
        return worldCoordinates;
    }

    /**
     * Serializes the game object to a JsonObject.
     *
     * @return the serialized JsonObject of the game object
     */
    protected JsonObject serializeGameObject() {
        JsonObject json = new JsonObject();

        json.put("objecttype", objectType.toString());
        json.put("worldx", getWorldX());
        json.put("worldy", getWorldY());

        return json;
    }
}

