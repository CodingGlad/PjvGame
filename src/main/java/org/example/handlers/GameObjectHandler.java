package org.example.handlers;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.entities.Enemy;
import org.example.entities.Player;
import org.example.gameobjects.*;
import org.example.gameobjects.types.*;
import org.example.handlers.types.GameStateType;
import org.example.utils.WorldCoordinates;
import org.example.views.GameObjectView;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static org.example.utils.GameConstants.TILE_SIZE;

public class GameObjectHandler {
    private List<GameObject> displayedObjects;
    private final GameObjectView view;

    public GameObjectHandler() {
        view = new GameObjectView();
        this.displayedObjects = new LinkedList<>();
    }

    public void setDefaultObjects() {
        displayedObjects.add(new Key(KeyType.GOLD, new WorldCoordinates(23 * TILE_SIZE, 40 * TILE_SIZE)));
        displayedObjects.add(new Chest(ObjectType.CHEST_CLOSED, ChestType.BIG, new WorldCoordinates(23 * TILE_SIZE, 25 * TILE_SIZE)));
        displayedObjects.add(new Chest(ObjectType.CHEST_CLOSED, ChestType.BIG, new WorldCoordinates(23 * TILE_SIZE, 7 * TILE_SIZE)));
        displayedObjects.add(new Armor(ArmorType.STEEL, new WorldCoordinates(25 * TILE_SIZE, 10 * TILE_SIZE)));
        displayedObjects.add(new Armor(ArmorType.COPPER, new WorldCoordinates(25 * TILE_SIZE, 11 * TILE_SIZE)));
        displayedObjects.add(new Weapon(WeaponType.STEEL_SWORD, new WorldCoordinates(25 * TILE_SIZE, 13 * TILE_SIZE)));
        displayedObjects.add(new Heart(new WorldCoordinates(25 * TILE_SIZE, 12 * TILE_SIZE)));
        displayedObjects.add(new Heart(new WorldCoordinates(26 * TILE_SIZE, 12 * TILE_SIZE)));
        displayedObjects.add(new Heart(new WorldCoordinates(27 * TILE_SIZE, 12 * TILE_SIZE)));
        displayedObjects.add(new Heart(new WorldCoordinates(28 * TILE_SIZE, 12 * TILE_SIZE)));
        displayedObjects.add(new Heart(new WorldCoordinates(29 * TILE_SIZE, 12 * TILE_SIZE)));
        displayedObjects.add(new Heart(new WorldCoordinates(30 * TILE_SIZE, 12 * TILE_SIZE)));
    }

    public List<GameObject> getDisplayedObjects() {
        return displayedObjects;
    }

    public void drawObjects(Graphics2D g2, Player player) {
        for (GameObject object: displayedObjects) {
            view.draw(g2, player, object);
        }
    }

    public Object[] serializeObjects() {
        return displayedObjects.stream().map(this::useObjectsSerializer).toArray();
    }

    public JsonObject useObjectsSerializer(GameObject obj) {
        switch (obj.getObjectType()) {
            case KEY -> {
                return ((Key) obj).serializeKey();
            }
            case ARMOR -> {
                return ((Armor) obj).serializeArmor();
            }
            case CHEST_OPENED, CHEST_CLOSED -> {
                return ((Chest) obj).serializeChest();
            }
            case HEART -> {
                return ((Heart) obj).serializeHeart();
            }
            case WEAPON -> {
                return ((Weapon) obj).serializeWeapon();
            }
        }

        return null;
    }

    public void deserializeObjects(JsonArray jsonObjects) {
        jsonObjects.forEach(obj -> addDeserializedObject((JsonObject) obj));
    }

    private void addDeserializedObject(JsonObject json) {
        switch (ObjectType.valueOf((String) json.get("objecttype"))) {
            case WEAPON -> displayedObjects.add(Weapon.deserializeAndCreateWeapon(json));
            case HEART -> displayedObjects.add(Heart.deserializeAndCreateHeart(json));
            case CHEST_CLOSED, CHEST_OPENED -> displayedObjects.add(Chest.deserializeAndCreateChest(json));
            case ARMOR -> displayedObjects.add(Armor.deserializeAndCreateArmor(json));
            case KEY -> displayedObjects.add(Key.deserializeAndCreateKey(json));
        }

    }
}
