package org.example.handlers;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.entities.Player;
import org.example.gameobjects.*;
import org.example.gameobjects.types.*;
import org.example.utils.WorldCoordinates;
import org.example.views.GameObjectView;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameObjectHandler {
    private static final Logger LOGGER = Logger.getLogger(GameObjectHandler.class.getName());
    private List<GameObject> displayedObjects;
    private final GameObjectView view;

    public GameObjectHandler() {
        view = new GameObjectView();
        this.displayedObjects = new LinkedList<>();
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
        LOGGER.log(Level.INFO, "Serializing objects...");
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
        LOGGER.log(Level.INFO, "Deserializing objects...");
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
