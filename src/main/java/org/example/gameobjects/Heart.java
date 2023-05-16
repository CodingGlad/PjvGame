package org.example.gameobjects;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.gameobjects.types.ObjectType;
import org.example.utils.WorldCoordinates;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Heart extends GameObject {
    public Heart(WorldCoordinates worldCoordinates) {
        super(ObjectType.HEART, worldCoordinates);
        setHeartImage();
    }

    private void setHeartImage() {
        try {
            setStaticImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/sprites/objects/heart/heart.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject serializeHeart() {
        return super.serializeGameObject();
    }

    public int restoreHealth() {
        Random rand = new Random();

        return Math.abs(rand.nextInt() % 30) + 10;
    }

    public static Heart deserializeAndCreateHeart(JsonObject json) {
        return new Heart(new WorldCoordinates(json));
    }
}
