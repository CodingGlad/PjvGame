package org.example.handlers;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.entities.Player;
import org.example.gameobjects.*;
import org.example.gameobjects.types.*;
import org.example.views.GameObjectView;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles game objects and related operations.
 */
public class GameObjectHandler {
    private static final Logger LOGGER = Logger.getLogger(GameObjectHandler.class.getName());
    private List<GameObject> displayedObjects;
    private final GameObjectView view;

    /**
     * Constructs a new GameObjectHandler object.
     * Initializes the GameObjectView and sets an empty list of displayed objects.
     */
    public GameObjectHandler() {
        view = new GameObjectView();
        this.displayedObjects = new LinkedList<>();
    }

    /**
     * Returns the list of displayed game objects.
     *
     * @return The list of displayed game objects.
     */
    public List<GameObject> getDisplayedObjects() {
        return displayedObjects;
    }

    /**
     * Draws the game objects on the specified graphics context using the provided player entity.
     *
     * @param g2     The graphics context to draw on.
     * @param player The player entity.
     */
    public void drawObjects(Graphics2D g2, Player player) {
        for (GameObject object : displayedObjects) {
            view.draw(g2, player, object);
        }
    }

    /**
     * Serializes the displayed objects and returns an array of serialized objects.
     * @param isLoggerOn Whether logger is turned on.
     * @return An array of serialized objects.
     */
    public Object[] serializeObjects(boolean isLoggerOn) {
        if (isLoggerOn) {
            LOGGER.log(Level.INFO, "Serializing objects...");
        }
        return displayedObjects.stream().map(this::useObjectsSerializer).toArray();
    }

    /**
     * Serializes a game object based on its type and returns a JSON object representation.
     *
     * @param obj The game object to serialize.
     * @return The serialized JSON object.
     */
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

    /**
     * Deserializes the objects from the provided JSON array and adds them to the list of displayed objects.
     * @param isLoggerOn Whether logger is turned on.
     * @param jsonObjects The JSON array containing the serialized objects.
     */
    public void deserializeObjects(JsonArray jsonObjects, boolean isLoggerOn) {
        if (isLoggerOn) {
            LOGGER.log(Level.INFO, "Deserializing objects...");
        }
        jsonObjects.forEach(obj -> addDeserializedObject((JsonObject) obj));
    }

    /**
     * Adds a deserialized game object to the list of displayed objects based on the object type.
     *
     * @param json The JSON object representing the deserialized game object.
     */
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

