package org.example.gameobjects;

import org.example.gameobjects.types.ChestStateType;
import org.example.gameobjects.types.ChestType;
import org.example.gameobjects.types.ObjectType;
import org.example.utils.WorldCoordinates;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Chest extends GameObject{
    private ChestType chestType;
    private ChestStateType stateType;

    public Chest(ObjectType objectType, ChestType chestType,
                 WorldCoordinates worldCoordinates) {
        super(objectType, worldCoordinates);
        if (objectType.equals(ObjectType.CHEST)) {
            this.chestType = chestType;
            this.stateType = ChestStateType.CLOSED;
            upsertChestClosedImage();
        } else {
            throw new IllegalStateException("Attempted creating object " + objectType.getName() + " as an instance of a chest.");
        }
    }

    private void upsertChestClosedImage() {
        try {
            setStaticImage(ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/sprites/objects/chest/" + getSpriteName() + ".png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSpriteName() {
        return chestType.getName() + "-" + stateType.getState();
    }

    public ChestType getChestType() {
        return chestType;
    }

    public ChestStateType getStateType() {
        return stateType;
    }

    public void openChest() {
        stateType = ChestStateType.OPENED;

        upsertChestClosedImage();
    }
}
