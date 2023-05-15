package org.example.entities;

import org.example.entities.types.ActivityType;
import org.example.entities.types.EntityType;
import org.example.entities.types.HorizontalDirectionType;
import org.example.entities.types.VerticalDirectionType;
import org.example.gameobjects.Armor;
import org.example.gameobjects.Weapon;
import org.example.gameobjects.types.ArmorType;
import org.example.handlers.CollisionHandler;
import org.example.handlers.InventoryHandler;
import org.example.handlers.KeyHandler;
import org.example.utils.WorldCoordinates;

import java.awt.*;
import java.util.Objects;

import static org.example.utils.GameConstants.*;

public class Player extends Entity {
    private final KeyHandler keyHandler;
    private final int screenX;
    private final int screenY;
    private int numberOfKeys;
    private final InventoryHandler inventory;

    //TODO cost default values change
    public Player(KeyHandler keyHandler) {
        super(new WorldCoordinates(TILE_SIZE * 23, TILE_SIZE * 21), 4, 2, EntityType.HERO);
        this.keyHandler = keyHandler;
        this.inventory = new InventoryHandler();

        this.screenX = (SCREEN_WIDTH / 2) - (TILE_SIZE / 2);
        this.screenY = (SCREEN_HEIGHT / 2) - (TILE_SIZE / 2);
    }

    public void update(CollisionHandler collisionHandler) {
        updateMovement();

        setCollisionsOn(false);
        collisionHandler.checkCollisions(this);
        collisionHandler.checkObject(this, keyHandler.isEquipButtonPressed());
        collisionHandler.checkEnemies(this);

        if (!isCollisionsOn() && !getActivityType().equals(ActivityType.IDLE)) {
            move();
        }

        incrementCounters();
    }

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

    public void fightUpdate(Enemy enemy) {
        incrementCounters();
        if (keyHandler.isSpacePressed() && attack()) {
            keyHandler.setSpacePressedToFalse();
            enemy.takeDamage(getAttackDamage());
        }
    }

    public void drawPlayer(Graphics2D g2) {
        super.drawEntity(g2, screenX, screenY);
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public int getNumberOfKeys() {
        return numberOfKeys;
    }

    public void setNumberOfKeys(int numberOfKeys) {
        this.numberOfKeys = numberOfKeys;
    }

    public void incrementKeys() {
        ++numberOfKeys;
    }

    public void decrementKeys() {
        --numberOfKeys;
    }

    public boolean deathUpdate() {
        return incrementDeathCounter();
    }

    public void equipArmor(Armor armor) {
        inventory.equipArmor(armor);
    }

    public void equipWeapon(Weapon weapon) {
        inventory.equipWeapon(weapon);
    }

    public int getAttackDamage() {
        if (Objects.nonNull(inventory.getWeaponEquipped())) {
            return inventory.getWeaponEquipped().getWeaponType().getDamage() + super.getAttackDamage();
        } else {
            return super.getAttackDamage();
        }
    }

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

    public InventoryHandler getInventory() {
        return inventory;
    }
}
