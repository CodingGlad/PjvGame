package org.example.handlers;

import org.example.gameobjects.Chest;
import org.example.gameobjects.GameObject;
import org.example.gameobjects.Key;
import org.example.gameobjects.types.ChestType;
import org.example.gameobjects.types.KeyType;
import org.example.gameobjects.types.ObjectType;
import org.example.utils.WorldCoordinates;

import java.util.LinkedList;
import java.util.List;

import static org.example.handlers.WindowHandler.TILE_SIZE;

public class GameObjectHandler {
    private List<GameObject> displayedObjects;

    public GameObjectHandler() {
        this.displayedObjects = new LinkedList<>();
    }

    public void setDefaultObjects() {
        displayedObjects.add(new Key(ObjectType.KEY, KeyType.GOLD, new WorldCoordinates(23 * TILE_SIZE, 40 * TILE_SIZE)));
        displayedObjects.add(new Chest(ObjectType.CHEST, ChestType.BIG, new WorldCoordinates(23 * TILE_SIZE, 25 * TILE_SIZE)));
        displayedObjects.add(new Chest(ObjectType.CHEST, ChestType.BIG, new WorldCoordinates(23 * TILE_SIZE, 7 * TILE_SIZE)));
    }

    public List<GameObject> getDisplayedObjects() {
        return displayedObjects;
    }
}
