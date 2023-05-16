package org.example.gameobjects;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.gameobjects.types.KeyType;
import org.example.gameobjects.types.ObjectType;
import org.example.utils.WorldCoordinates;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Key extends GameObject {
    private final KeyType keyType;

    public Key(ObjectType objectType, KeyType keyType, WorldCoordinates worldCoordinates) {
        super(objectType, worldCoordinates);
        if (objectType.equals(ObjectType.KEY)) {
            this.keyType = keyType;
            setKeyImage();
        } else {
            throw new IllegalStateException("Attempted creating object " + objectType.getName() + " as an instance of a key.");
        }
    }

    private void setKeyImage() {
        try {
            setStaticImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/sprites/objects/key/" + keyType.getName() + ".png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject serializeKey() {
        JsonObject json = super.serializeGameObject();

        json.put("keytype", keyType.toString());

        return json;
    }
}
