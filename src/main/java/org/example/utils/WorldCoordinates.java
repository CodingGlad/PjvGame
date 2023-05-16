package org.example.utils;

import com.github.cliftonlabs.json_simple.JsonObject;

import java.math.BigDecimal;

public class WorldCoordinates {
    private int worldX;
    private int worldY;

    public WorldCoordinates(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
    }

    public WorldCoordinates(JsonObject json) {
        this.worldX = ((BigDecimal) json.get("worldx")).intValue();
        this.worldY = ((BigDecimal) json.get("worldy")).intValue();
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }
}
