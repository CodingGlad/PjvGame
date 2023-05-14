package org.example.gameobjects;

import org.example.gameobjects.types.ObjectType;
import org.example.utils.WorldCoordinates;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Heart extends GameObject {
    public Heart(ObjectType objectType, WorldCoordinates worldCoordinates) {
        super(objectType, worldCoordinates);
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
}
