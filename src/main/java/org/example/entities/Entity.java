package org.example.entities;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.entities.types.ActivityType;
import org.example.entities.types.EntityType;
import org.example.entities.types.HorizontalDirectionType;
import org.example.entities.types.VerticalDirectionType;
import org.example.utils.WorldCoordinates;
import org.example.views.EntityView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public abstract class Entity {
    protected static final int DEFAULT_SOLID_X = 8;
    protected static final int DEFAULT_SOLID_Y = 25;
    protected static final int DEFAULT_SOLID_WIDTH = 12;
    protected static final int DEFAULT_SOLID_HEIGHT = 4;
    protected static final int DEFAULT_VISIBLE_HEIGHT = 16;
    private WorldCoordinates worldCoordinates;
    private final int speed;
    private final EntityType entityType;

    private final Map<String, List<BufferedImage>> sprites;

    private ActivityType activityType;
    private VerticalDirectionType verticalDirection;
    private HorizontalDirectionType horizontalDirection;

    private int spriteCounter;
    private int attackSpeed;
    private int attackCounter;
    private int attackDamage;
    protected Rectangle solidArea;
    protected Rectangle visibleArea;
    private boolean collisionsOn;
    private int health;
    private final EntityView view;

    protected Entity(WorldCoordinates worldCoordinates, EntityType entityType) {
        this.worldCoordinates = worldCoordinates;
        this.speed = entityType.getDefaultSpeed();
        this.attackSpeed = entityType.getDefaulAttackSpeed();
        this.entityType = entityType;
        this.activityType = ActivityType.IDLE;
        this.sprites = new HashMap<>();
        this.verticalDirection = VerticalDirectionType.NONE;
        this.horizontalDirection = HorizontalDirectionType.LEFT;
        this.spriteCounter = 0;
        this.attackCounter = 0;
        this.health = entityType.getDefaultHealth();
        this.collisionsOn = false;
        this.solidArea = new Rectangle(DEFAULT_SOLID_X, DEFAULT_SOLID_Y, DEFAULT_SOLID_WIDTH, DEFAULT_SOLID_HEIGHT);
        this.visibleArea = new Rectangle(DEFAULT_SOLID_X, DEFAULT_SOLID_Y, DEFAULT_SOLID_WIDTH, DEFAULT_VISIBLE_HEIGHT);
        this.view = new EntityView();
        this.attackDamage = this.entityType.getDefaultDamage();
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

    private int getImageIndex() {
        return spriteCounter / 10;
    }

    public int getWorldX() {
        return worldCoordinates.getWorldX();
    }

    public void setWorldX(int worldX) {
        worldCoordinates.setWorldX(worldX);
    }

    public int getWorldY() {
        return worldCoordinates.getWorldY();
    }

    public void setWorldY(int worldY) {
        worldCoordinates.setWorldY(worldY);
    }

    public int getSpeed() {
        return speed;
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

    public void incrementCounters() {
        if (!activityType.equals(ActivityType.DYING) && ++spriteCounter == entityType.getSpritesNumber() * 10) {
            spriteCounter = 0;
            if (attackCounter < attackSpeed) {
                ++attackCounter;
            }
        }
    }

    public boolean incrementDeathCounter() {
        if (++spriteCounter == entityType.getSpritesNumber() * 10) {
            spriteCounter = entityType.getSpritesNumber() * 10 - 3;
            return true;
        }

        return false;
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

    public int getSolidLeftWorldX() {
        return getWorldX() + solidArea.x;
    }

    public int getSolidRightWorldX() {
        return getSolidLeftWorldX() + solidArea.width;
    }

    public int getSolidTopWorldY() {
        return getWorldY() + solidArea.y;
    }

    public int getSolidBottomWorldY() {
        return getSolidTopWorldY() + solidArea.height;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public Rectangle getVisibleArea() {
        return visibleArea;
    }

    public void move() {
        switch (getVerticalDirection()) {
            case UP -> setWorldY(getWorldY() - getSpeed());
            case DOWN -> setWorldY(getWorldY() + getSpeed());
            case NONE -> {
                switch (getHorizontalDirection()) {
                    case LEFT -> setWorldX(getWorldX() - getSpeed());
                    case RIGHT -> setWorldX(getWorldX() + getSpeed());
                }
            }
        }
    }

    public EntityType getEntityType() {
        return entityType;
    }

    protected void drawEntity(Graphics2D g2, int coordX, int coordY) {
        view.draw(g2, getImage(), coordX, coordY);
    }

    public void loadCoordinations(int worldX, int worldY) {
        this.worldCoordinates = new WorldCoordinates(worldX, worldY);
    }

    public int getHealth() {
        return health;
    }

    public boolean attack() {
        if (attackCounter == attackSpeed) {
            attackCounter = 0;
            return true;
        }

        return false;
    }

    public void takeDamage(int damage) {
        if (health - damage >= 0) {
            health -= damage;
        } else {
            health = 0;
        }
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDyingActivity() {
        activityType = ActivityType.DYING;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public JsonObject serializeEntity() {
        JsonObject json = new JsonObject();

        json.put("worldx", getWorldX());
        json.put("worldy", getWorldY());
        json.put("entitytype", entityType.toString());
        json.put("health", health);
        json.put("horizontal", horizontalDirection.toString());
        json.put("activity", activityType.toString());

        return json;
    }
}
