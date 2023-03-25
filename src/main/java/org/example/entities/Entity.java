package org.example.entities;

import org.example.entities.types.ActivityType;
import org.example.entities.types.EntityType;
import org.example.entities.types.HorizontalDirectionType;
import org.example.entities.types.VerticalDirectionType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Entity {
    private int worldX;
    private int worldY;
    private int speed;
    private final EntityType entityType;

    private final Map<String, List<BufferedImage>> sprites;

    private ActivityType activityType;
    private VerticalDirectionType verticalDirection;
    private HorizontalDirectionType horizontalDirection;

    private int spriteCounter;
    private final Rectangle solidArea;
    private boolean collisionsOn;

    //TODO less constructor parameters??
    public Entity(int worldX, int worldY, int speed, EntityType entityType,
                  int solidAreaX, int solidAreaY, int solidWidth, int solidHeight) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.speed = speed;
        this.entityType = entityType;
        this.activityType = ActivityType.IDLE;
        this.sprites = new HashMap<>();
        this.verticalDirection = VerticalDirectionType.NONE;
        this.horizontalDirection = HorizontalDirectionType.LEFT;
        this.spriteCounter = 0;
        this.collisionsOn = false;
        this.solidArea = new Rectangle(solidAreaX, solidAreaY, solidWidth, solidHeight);
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
        return "/sprites/" + entityType.getEntityString() + "/" + activity.getActivityString() + "-" + direction.getDirectionString();
    }

    private String getMapKeyString(ActivityType activity, HorizontalDirectionType direction) {
        return activity.getActivityString() + direction.getDirectionString();
    }

    public BufferedImage getImage() {
        return sprites.get(getMapKeyString(this.activityType, horizontalDirection)).get(getImageIndex());
    }

    //TODO mby change speed of sprites of different activites
    //TODO probably just add another value to activity or entity enum
    private int getImageIndex() {
        return spriteCounter / 10;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
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

    public ActivityType getActivityType() {
        return activityType;
    }

    public boolean isCollisionsOn() {
        return collisionsOn;
    }

    public void setCollisionsOn(boolean collisionsOn) {
        this.collisionsOn = collisionsOn;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public int getLeftWorldX() {
        return worldX + solidArea.x;
    }

    public int getRightWorldX() {
        return getLeftWorldX() + solidArea.width;
    }

    public int getTopWorldY() {
        return worldY + solidArea.y;
    }

    public int getBottomWorldY() {
        return getTopWorldY() + solidArea.height;
    }
}
