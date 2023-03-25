package org.example.game;

import org.example.gameobjects.Chest;
import org.example.gameobjects.GameObject;
import org.example.gameobjects.Key;
import org.example.gameobjects.types.ChestType;
import org.example.gameobjects.types.KeyType;
import org.example.gameobjects.types.ObjectType;
import org.example.utils.WorldCoordinates;

import java.util.List;

import static org.example.game.GamePanel.TILE_SIZE;

public class ObjectHandler {
    public void setObjects(List<GameObject> objects) {
        objects.add(new Key(ObjectType.KEY, KeyType.GOLD, new WorldCoordinates(23 * TILE_SIZE, 40 * TILE_SIZE)));
        objects.add(new Chest(ObjectType.CHEST, ChestType.BIG, new WorldCoordinates(23 * TILE_SIZE, 7 * TILE_SIZE)));
    }
}
