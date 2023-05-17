package org.example.gameobjects;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.gameobjects.types.ObjectType;
import org.example.utils.WorldCoordinates;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

/**
 * Represents a heart object.
 */
public class Heart extends GameObject {
    /**
     * Constructs a Heart object with the specified world coordinates.
     *
     * @param worldCoordinates the world coordinates of the heart object.
     */
    public Heart(WorldCoordinates worldCoordinates) {
        super(ObjectType.HEART, worldCoordinates);
        setHeartImage();
    }

    /**
     * Sets the heart image.
     */
    private void setHeartImage() {
        try {
            setStaticImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/sprites/objects/heart/heart.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Serializes the heart object to JSON.
     *
     * @return the serialized JSON object.
     */
    public JsonObject serializeHeart() {
        return super.serializeGameObject();
    }

    /**
     * Restores the player's health by a random amount.
     *
     * @return the amount of health restored.
     */
    public int restoreHealth() {
        Random rand = new Random();

        return Math.abs(rand.nextInt() % 30) + 10;
    }

    /**
     * Deserializes and creates a Heart object from JSON.
     *
     * @param json the JSON object containing the heart data.
     * @return the created Heart object.
     */
    public static Heart deserializeAndCreateHeart(JsonObject json) {
        return new Heart(new WorldCoordinates(json));
    }
}

