package org.example.entities;

import org.example.entities.types.ActivityType;
import org.example.entities.types.EntityType;
import org.example.entities.types.HorizontalDirectionType;
import org.example.entities.types.VerticalDirectionType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Entity {
    private int x;
    private int y;
    private int speed;
    private final EntityType entityType;

    private final Map<String, List<BufferedImage>> sprites;

    private ActivityType activityType;
    private VerticalDirectionType verticalDirection;
    private HorizontalDirectionType horizontalDirection;

    private int spriteCounter;

    public Entity(int x, int y, int speed, EntityType entityType) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.entityType = entityType;
        this.activityType = ActivityType.IDLE;
        this.sprites = new HashMap<>();
        this.verticalDirection = null;
        this.horizontalDirection = HorizontalDirectionType.LEFT;
        this.spriteCounter = 0;
        getAllImages();
    }

    public void getAllImages() {
        for (HorizontalDirectionType dir: HorizontalDirectionType.values()) {
            for (ActivityType act: ActivityType.values()) {
                getActivityAndDirectionImages(act, dir);
            }
        }
    }

    private void getActivityAndDirectionImages(ActivityType activity, HorizontalDirectionType direction) {
        try {
            List<BufferedImage> tmpSprites = new ArrayList<>();
            final String resourceString = getSpriteString(activity, direction);

            for (int i = 1; i < entityType.getSpritesNumber() + 1; ++i) {
                tmpSprites.add(ImageIO.read(
                        Objects.requireNonNull(getClass().getResourceAsStream(resourceString + "-" + i + ".png"))
                ));
            }

            sprites.put(getMapKeyString(activity, direction), tmpSprites);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getSpriteString(ActivityType activity, HorizontalDirectionType direction) {
        return "/assets/" + entityType.getEntityString() + "/" + activity.getActivityString() + "-" + direction.getDirectionString();
    }

    private String getMapKeyString(ActivityType activity, HorizontalDirectionType direction) {
        return activity.getActivityString() + direction.getDirectionString();
    }

    public BufferedImage getImage() {
        return sprites.get(getMapKeyString(this.activityType, horizontalDirection)).get(getImageIndex());
    }

    private int getImageIndex() {
        return spriteCounter / 10;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public VerticalDirectionType getVerticalDirection() {
        return verticalDirection;
    }

    public void setVerticalDirection(VerticalDirectionType verticalDirection) {
        this.verticalDirection = verticalDirection;
        this.activityType = ActivityType.WALK;
    }

    public HorizontalDirectionType getHorizontalDirection() {
        return horizontalDirection;
    }

    public void setHorizontalDirection(HorizontalDirectionType horizontalDirection) {
        this.horizontalDirection = horizontalDirection;
    }

    public void incrementCounter() {
        if (++spriteCounter == entityType.getSpritesNumber() * 10) {
            spriteCounter = 0;
        }
    }

    public void setIdleActivity() {
        activityType = ActivityType.IDLE;
    }
}
