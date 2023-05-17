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
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

/**
 * Represents an abstract entity in the game.
 */
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

    /**
     * Constructs an Entity with the specified world coordinates and entity type.
     *
     * @param worldCoordinates the world coordinates of the entity
     * @param entityType       the type of the entity
     */
    protected Entity(WorldCoordinates worldCoordinates, EntityType entityType) {
        this.worldCoordinates = worldCoordinates;
        this.speed = entityType.getDefaultSpeed();
        this.attackSpeed = entityType.getDefaultAttackSpeed();
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

    /**
     * Retrieves all the images for the entity from the resources.
     */
    public void getAllImages() {
        for (HorizontalDirectionType dir : HorizontalDirectionType.values()) {
            for (ActivityType act : ActivityType.values()) {
                getActivityAndDirectionImages(act, dir);
            }
        }
    }

    /**
     * Retrieves and loads the images associated with the specified activity and direction for the entity.
     * The images are stored in the 'sprites' map.
     *
     * @param activity   The activity type of the entity.
     * @param direction  The horizontal direction of the entity.
     */
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

    /**
     * Constructs the string representation of the sprite resource path based on the specified activity and direction.
     *
     * @param activity   The activity type of the entity.
     * @param direction  The horizontal direction of the entity.
     * @return The sprite resource string.
     */
    private String getSpriteString(ActivityType activity, HorizontalDirectionType direction) {
        return "/sprites/" + entityType.getEntityString() + "/" + activity.getActivityString() + "-" + direction.getDirectionString();
    }

    /**
     * Constructs the map key string based on the specified activity and direction.
     * This key is used to store the corresponding sprite images in the 'sprites' map.
     *
     * @param activity   The activity type of the entity.
     * @param direction  The horizontal direction of the entity.
     * @return The map key string.
     */
    private String getMapKeyString(ActivityType activity, HorizontalDirectionType direction) {
        return activity.getActivityString() + direction.getDirectionString();
    }


    /**
     * Gets the current image of the entity based on its activity and direction.
     *
     * @return the current image of the entity
     */
    public BufferedImage getImage() {
        return sprites.get(getMapKeyString(this.activityType, horizontalDirection)).get(getImageIndex());
    }

    /**
     * Gets sprite image index from sprite counter.
     *
     * @return the sprite image index
     */
    private int getImageIndex() {
        return spriteCounter / 10;
    }

    /**
     * Gets the x-coordinate of the entity in the world.
     *
     * @return the x-coordinate
     */
    public int getWorldX() {
        return worldCoordinates.getWorldX();
    }

    /**
     * Sets the x-coordinate of the entity in the world.
     *
     * @param worldX the x-coordinate to set
     */
    public void setWorldX(int worldX) {
        worldCoordinates.setWorldX(worldX);
    }

    /**
     * Gets the y-coordinate of the entity in the world.
     *
     * @return the y-coordinate
     */
    public int getWorldY() {
        return worldCoordinates.getWorldY();
    }

    /**
     * Sets the y-coordinate of the entity in the world.
     *
     * @param worldY the y-coordinate to set
     */
    public void setWorldY(int worldY) {
        worldCoordinates.setWorldY(worldY);
    }

    /**
     * Gets the speed value of the entity.
     *
     * @return the speed value
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Gets the vertical direction of the entity.
     *
     * @return the vertical direction
     */
    public VerticalDirectionType getVerticalDirection() {
        return verticalDirection;
    }

    /**
     * Sets the vertical direction of the entity and sets the activity type to WALK.
     *
     * @param verticalDirection the vertical direction to set
     */
    public void setVerticalDirection(VerticalDirectionType verticalDirection) {
        this.verticalDirection = verticalDirection;
        this.activityType = ActivityType.WALK;
    }

    /**
     * Gets the horizontal direction of the entity.
     *
     * @return the horizontal direction
     */
    public HorizontalDirectionType getHorizontalDirection() {
        return horizontalDirection;
    }

    /**
     * Sets the horizontal direction of the entity.
     *
     * @param horizontalDirection the horizontal direction to set
     */
    public void setHorizontalDirection(HorizontalDirectionType horizontalDirection) {
        this.horizontalDirection = horizontalDirection;
    }

    /**
     * Increments the sprite and attack counters based on the entity's current state.
     */
    public void incrementCounters() {
        if (!activityType.equals(ActivityType.DYING) && ++spriteCounter == entityType.getSpritesNumber() * 10) {
            spriteCounter = 0;
            if (attackCounter < attackSpeed) {
                ++attackCounter;
            }
        }
    }

    /**
     * Increments the sprite counter and returns true if the entity's death animation is complete.
     *
     * @return true if the death animation is complete, false otherwise
     */
    public boolean incrementDeathCounter() {
        if (++spriteCounter == entityType.getSpritesNumber() * 10) {
            spriteCounter = entityType.getSpritesNumber() * 10 - 3;
            return true;
        }

        return false;
    }

    /**
     * Sets the activity type of the entity to IDLE.
     */
    public void setIdleActivity() {
        activityType = ActivityType.IDLE;
    }

    /**
     * Gets the current activity type of the entity.
     *
     * @return the activity type
     */
    public ActivityType getActivityType() {
        return activityType;
    }

    /**
     * Checks if collisions are enabled for the entity.
     *
     * @return true if collisions are enabled, false otherwise
     */
    public boolean isCollisionsOn() {
        return collisionsOn;
    }

    /**
     * Sets whether collisions are enabled for the entity.
     *
     * @param collisionsOn true to enable collisions, false to disable collisions
     */
    public void setCollisionsOn(boolean collisionsOn) {
        this.collisionsOn = collisionsOn;
    }

    /**
     * Gets the x-coordinate of the left side of the solid area in the world.
     *
     * @return the x-coordinate of the left side of the solid area
     */
    public int getSolidLeftWorldX() {
        return getWorldX() + solidArea.x;
    }

    /**
     * Calculates the world X-coordinate of the right side of the solid area.
     *
     * @return The world X-coordinate of the right side of the solid area.
     */
    public int getSolidRightWorldX() {
        return getSolidLeftWorldX() + solidArea.width;
    }

    /**
     * Calculates the world Y-coordinate of the top side of the solid area.
     *
     * @return The world Y-coordinate of the top side of the solid area.
     */
    public int getSolidTopWorldY() {
        return getWorldY() + solidArea.y;
    }

    /**
     * Calculates the world Y-coordinate of the bottom side of the solid area.
     *
     * @return The world Y-coordinate of the bottom side of the solid area.
     */
    public int getSolidBottomWorldY() {
        return getSolidTopWorldY() + solidArea.height;
    }

    /**
     * Retrieves the solid area of the entity.
     *
     * @return The solid area rectangle.
     */
    public Rectangle getSolidArea() {
        return solidArea;
    }

    /**
     * Retrieves the visible area of the entity.
     *
     * @return The visible area rectangle.
     */
    public Rectangle getVisibleArea() {
        return visibleArea;
    }

    /**
     * Moves the entity based on its current vertical and horizontal direction.
     * Updates the world coordinates accordingly.
     */
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

    /**
     * Retrieves the entity type of the entity.
     *
     * @return The entity type.
     */
    public EntityType getEntityType() {
        return entityType;
    }


    /**
     * Draws the entity on the graphics context at the specified coordinates.
     *
     * @param g2     the graphics context
     * @param coordX the x-coordinate of the entity
     * @param coordY the y-coordinate of the entity
     */
    protected void drawEntity(Graphics2D g2, int coordX, int coordY) {
        view.draw(g2, getImage(), coordX, coordY);
    }

    /**
     * Loads the world coordinates for the entity.
     *
     * @param worldX the x-coordinate of the entity in the world
     * @param worldY the y-coordinate of the entity in the world
     */
    public void loadCoordinations(int worldX, int worldY) {
        this.worldCoordinates = new WorldCoordinates(worldX, worldY);
    }

    /**
     * Gets the health value of the entity.
     *
     * @return the health value
     */
    public int getHealth() {
        return health;
    }

    /**
     * Performs an attack action and returns true if the entity can attack,
     * based on the attack speed.
     *
     * @return true if the entity can attack, false otherwise
     */
    public boolean attack() {
        if (attackCounter == attackSpeed) {
            attackCounter = 0;
            return true;
        }

        return false;
    }

    /**
     * Inflicts the specified amount of damage to the entity's health.
     *
     * @param damage the amount of damage to inflict
     */
    public void takeDamage(int damage) {
        if (health - damage >= 0) {
            health -= damage;
        } else {
            health = 0;
        }
    }

    /**
     * Sets the health value of the entity.
     *
     * @param health the health value to set
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Sets the activity type of the entity to DYING.
     */
    public void setDyingActivity() {
        activityType = ActivityType.DYING;
    }

    /**
     * Gets the attack damage value of the entity.
     *
     * @return the attack damage value
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Serializes the entity into a JSON object.
     *
     * @return the serialized JSON object
     */
    protected JsonObject serializeEntity() {
        JsonObject json = new JsonObject();

        json.put("worldx", getWorldX());
        json.put("worldy", getWorldY());
        json.put("entitytype", entityType.toString());
        json.put("health", health);
        json.put("horizontal", horizontalDirection.toString());
        json.put("activity", activityType.toString());

        return json;
    }

    /**
     * Deserializes a JSON object and sets the entity properties accordingly.
     *
     * @param json the JSON object to deserialize
     */
    protected void deserializeAndSetEntity(JsonObject json) {
        worldCoordinates = new WorldCoordinates(
                ((BigDecimal) json.get("worldx")).intValue(),
                ((BigDecimal) json.get("worldy")).intValue());
        health = ((BigDecimal) json.get("health")).intValue();
        horizontalDirection = HorizontalDirectionType.valueOf((String) json.get("horizontal"));
        activityType = ActivityType.valueOf((String) json.get("activity"));
    }

    /**
     * Restores the entity's health by the specified amount.
     *
     * @param health the amount of health to restore
     */
    protected void restoreHealth(int health) {
        this.health += health;
    }

    /**
     * Gets the world coordinates of the entity.
     *
     * @return the world coordinates
     */
    public WorldCoordinates getWorldCoordinates() {
        return worldCoordinates;
    }
}
