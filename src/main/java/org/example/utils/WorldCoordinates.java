package org.example.utils;

import com.github.cliftonlabs.json_simple.JsonObject;

import java.math.BigDecimal;

/**
 * Represents the coordinates of a location in the game world.
 */
public class WorldCoordinates {
    private int worldX;
    private int worldY;

    /**
     * Constructs WorldCoordinates with the specified x and y coordinates.
     *
     * @param worldX the x-coordinate in the game world
     * @param worldY the y-coordinate in the game world
     */
    public WorldCoordinates(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
    }

    /**
     * Constructs WorldCoordinates from a JSON object.
     *
     * @param json the JSON object representing the world coordinates
     */
    public WorldCoordinates(JsonObject json) {
        this.worldX = ((BigDecimal) json.get("worldx")).intValue();
        this.worldY = ((BigDecimal) json.get("worldy")).intValue();
    }

    /**
     * Gets the x-coordinate in the game world.
     *
     * @return the x-coordinate
     */
    public int getWorldX() {
        return worldX;
    }

    /**
     * Sets the x-coordinate in the game world.
     *
     * @param worldX the new x-coordinate
     */
    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    /**
     * Gets the y-coordinate in the game world.
     *
     * @return the y-coordinate
     */
    public int getWorldY() {
        return worldY;
    }

    /**
     * Sets the y-coordinate in the game world.
     *
     * @param worldY the new y-coordinate
     */
    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }
}

