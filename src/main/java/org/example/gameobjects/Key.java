package org.example.gameobjects;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.gameobjects.types.KeyType;
import org.example.gameobjects.types.ObjectType;
import org.example.gameobjects.types.WeaponType;
import org.example.utils.WorldCoordinates;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents a key object.
 */
public class Key extends GameObject {
    private final KeyType keyType;

    /**
     * Constructs a Key object with the specified key type and world coordinates.
     *
     * @param keyType          the type of key.
     * @param worldCoordinates the world coordinates of the key object.
     */
    public Key(KeyType keyType, WorldCoordinates worldCoordinates) {
        super(ObjectType.KEY, worldCoordinates);
        this.keyType = keyType;
        setKeyImage();
    }

    /**
     * Sets the key image.
     */
    private void setKeyImage() {
        try {
            setStaticImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/sprites/objects/key/" + keyType.getName() + ".png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Serializes the key object to JSON.
     *
     * @return the serialized JSON object.
     */
    public JsonObject serializeKey() {
        JsonObject json = super.serializeGameObject();

        json.put("keytype", keyType.toString());

        return json;
    }

    /**
     * Deserializes and creates a Key object from JSON.
     *
     * @param json the JSON object containing the key data.
     * @return the created Key object.
     */
    public static Key deserializeAndCreateKey(JsonObject json) {
        return new Key(KeyType.valueOf((String) json.get("keytype")),
                new WorldCoordinates(json)
        );
    }
}

