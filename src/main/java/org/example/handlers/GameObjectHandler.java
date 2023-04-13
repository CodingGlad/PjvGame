package org.example.handlers;

import org.example.gameobjects.Chest;
import org.example.gameobjects.GameObject;
import org.example.gameobjects.Key;
import org.example.gameobjects.types.ChestType;
import org.example.gameobjects.types.KeyType;
import org.example.gameobjects.types.ObjectType;
import org.example.utils.WorldCoordinates;

import java.util.List;

import static org.example.handlers.PanelHandler.TILE_SIZE;

public class GameObjectHandler {
    public void setObjects(List<GameObject> objects) {
        objects.add(new Key(ObjectType.KEY, KeyType.GOLD, new WorldCoordinates(23 * TILE_SIZE, 40 * TILE_SIZE)));
        objects.add(new Chest(ObjectType.CHEST, ChestType.BIG, new WorldCoordinates(23 * TILE_SIZE, 25 * TILE_SIZE)));
        objects.add(new Chest(ObjectType.CHEST, ChestType.BIG, new WorldCoordinates(23 * TILE_SIZE, 7 * TILE_SIZE)));
    }
}
