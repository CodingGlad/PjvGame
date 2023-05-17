package org.example.entities;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.example.entities.types.ActivityType;
import org.example.entities.types.EntityType;
import org.example.entities.types.HorizontalDirectionType;
import org.example.entities.types.VerticalDirectionType;
import org.example.gameobjects.Armor;
import org.example.gameobjects.Weapon;
import org.example.handlers.CollisionHandler;
import org.example.handlers.InventoryHandler;
import org.example.handlers.KeyHandler;
import org.example.utils.WorldCoordinates;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Objects;

import static org.example.utils.GameConstants.*;

/**
 * Represents the player character in the game.
 */
public class Player extends Entity {
    private final KeyHandler keyHandler;
    private final int screenX;
    private final int screenY;
    private int numberOfKeys;
    private final InventoryHandler inventory;

    /**
     * Constructs a new Player instance with the specified KeyHandler.
     *
     * @param keyHandler The KeyHandler used for player input.
     */
    public Player(KeyHandler keyHandler) {
        super(new WorldCoordinates(TILE_SIZE * 23, TILE_SIZE * 21), EntityType.HERO);
        this.keyHandler = keyHandler;
        this.inventory = new InventoryHandler();
        this.numberOfKeys = 0;

        this.screenX = (SCREEN_WIDTH / 2) - (TILE_SIZE / 2);
        this.screenY = (SCREEN_HEIGHT / 2) - (TILE_SIZE / 2);

    }

    /**
     * Updates the player's state and handles collisions with the provided CollisionHandler.
     *
     * @param collisionHandler The CollisionHandler used for collision detection.
     */
    public void update(CollisionHandler collisionHandler) {
        updateMovement();

        setCollisionsOn(false);
        collisionHandler.checkCollisions(this);
        collisionHandler.checkObject(this, keyHandler.isEquipButtonPressed(), keyHandler.isUseButtonPressed());
        keyHandler.setEquipButtonToFalse();
        keyHandler.setUseButtonToFalse();
        collisionHandler.checkEnemies(this);

        if (!isCollisionsOn() && !getActivityType().equals(ActivityType.IDLE)) {
            move();
        }

        incrementCounters();
    }

    /**
     * Updates the player's movement direction based on input from the KeyHandler.
     */
    private void updateMovement() {
        if (keyHandler.isUpPressed()) {
            setVerticalDirection(VerticalDirectionType.UP);
        } else if (keyHandler.isDownPressed()) {
            setVerticalDirection(VerticalDirectionType.DOWN);
        } else if (keyHandler.isLeftPressed()) {
            setVerticalDirection(VerticalDirectionType.NONE);
            setHorizontalDirection(HorizontalDirectionType.LEFT);
        } else if (keyHandler.isRightPressed()) {
            setVerticalDirection(VerticalDirectionType.NONE);
            setHorizontalDirection(HorizontalDirectionType.RIGHT);
        } else {
            setIdleActivity();
        }
    }

    /**
     * Updates the player's state during a fight with an enemy and returns whether the player successfully attacked.
     *
     * @param enemy The enemy to fight against.
     * @return {@code true} if the player successfully attacked the enemy, {@code false} otherwise.
     */
    public boolean fightUpdate(Enemy enemy) {
        incrementCounters();
        if (keyHandler.isSpacePressed() && attack()) {
            keyHandler.setSpacePressedToFalse();
            enemy.takeDamage(getPlayerAttackDamage());

            return true;
        }

        return false;
    }

    /**
     * Draws the player on the graphics context at the specified screen coordinates.
     *
     * @param g2 The Graphics2D context to draw on.
     */
    public void drawPlayer(Graphics2D g2) {
        super.drawEntity(g2, screenX, screenY);
    }

    /**
     * Returns the screen X coordinate of the player.
     *
     * @return The screen X coordinate.
     */
    public int getScreenX() {
        return screenX;
    }

    /**
     * Returns the screen Y coordinate of the player.
     *
     * @return The screen Y coordinate.
     */
    public int getScreenY() {
        return screenY;
    }

    /**
     * Returns the number of keys the player has.
     *
     * @return The number of keys.
     */
    public int getNumberOfKeys() {
        return numberOfKeys;
    }

    /**
     * Increments the number of keys by one.
     */
    public void incrementKeys() {
        ++numberOfKeys;
    }

    /**
     * Decrements the number of keys by one.
     */
    public void decrementKeys() {
        --numberOfKeys;
    }

    /**
     * Updates the player's state during death animation and returns whether the death animation is finished.
     *
     * @return {@code true} if the death animation is finished, {@code false} otherwise.
     */
    public boolean deathUpdate() {
        return incrementDeathCounter();
    }

    /**
     * Equips the given armor and returns the previously equipped armor, if any.
     *
     * @param armor The armor to equip.
     * @return The previously equipped armor, or {@code null} if there was none.
     */
    public Armor equipArmor(Armor armor) {
        if (Objects.nonNull(inventory.getArmorEquipped())) {
            Armor oldArmor = inventory.getArmorEquipped();
            oldArmor.setWorldCoordinates(armor.getWorldCoordinates());
            inventory.equipArmor(armor);

            return oldArmor;
        } else {
            inventory.equipArmor(armor);

            return null;
        }
    }

    /**
     * Equips the given weapon and returns the previously equipped weapon, if any.
     *
     * @param weapon The weapon to equip.
     * @return The previously equipped weapon, or {@code null} if there was none.
     */
    public Weapon equipWeapon(Weapon weapon) {
        if (Objects.nonNull(inventory.getWeaponEquipped())) {
            Weapon oldWeapon = inventory.getWeaponEquipped();
            oldWeapon.setWorldCoordinates(weapon.getWorldCoordinates());
            inventory.equipWeapon(weapon);

            return oldWeapon;
        } else {
            inventory.equipWeapon(weapon);

            return null;
        }
    }

    /**
     * Returns the total attack damage of the player, considering the equipped weapon.
     *
     * @return The player's attack damage.
     */
    public int getPlayerAttackDamage() {
        if (Objects.nonNull(inventory.getWeaponEquipped())) {
            return inventory.getWeaponEquipped().getWeaponType().getDamage() + super.getAttackDamage();
        } else {
            return super.getAttackDamage();
        }
    }

    /**
     * Reduces the damage taken by the player based on the equipped armor's damage reduction.
     * If no armor is equipped, the damage is taken as is.
     *
     * @param damage The damage to be taken.
     */
    @Override
    public void takeDamage(int damage) {
        if (Objects.nonNull(inventory.getArmorEquipped())) {
            super.takeDamage(
                    Math.round(damage * inventory.getArmorEquipped().getArmorType().getDamageReduction())
            );
        } else {
            super.takeDamage(damage);
        }
    }

    /**
     * Returns the player's inventory handler.
     *
     * @return The inventory handler.
     */
    public InventoryHandler getInventory() {
        return inventory;
    }

    /**
     * Serializes the player object to a JSON object.
     *
     * @return The serialized JSON object.
     */
    public JsonObject serializePlayer() {
        JsonObject json = serializeEntity();

        json.put("inventory", inventory.serializeInventory());
        json.put("numberofkeys", numberOfKeys);

        return json;
    }

    /**
     * Deserializes the JSON object and sets the player's attributes accordingly.
     *
     * @param json The JSON object containing the player data.
     */
    public void deserializeAndSetPlayer(JsonObject json) {
        super.deserializeAndSetEntity(json);

        numberOfKeys = ((BigDecimal) json.get("numberofkeys")).intValue();
        inventory.deserializeAndSetInventory((JsonObject) json.get("inventory"));
    }

    /**
     * Restores the player's health to the specified value.
     *
     * @param health The health value to restore.
     */
    public void restorePlayersHealth(int health) {
        super.restoreHealth(health);
    }
}
