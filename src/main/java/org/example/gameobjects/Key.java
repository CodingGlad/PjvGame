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

    public Key(KeyType keyType, WorldCoordinates worldCoordinates) {
        super(ObjectType.KEY, worldCoordinates);
        this.keyType = keyType;
        setKeyImage();
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
