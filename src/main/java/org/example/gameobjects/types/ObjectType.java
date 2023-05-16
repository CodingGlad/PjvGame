package org.example.gameobjects.types;

public enum ObjectType {
    KEY("key", false, true),
    CHEST_OPENED("opened", false, false),
    CHEST_CLOSED("closed", true, false),
    HEART("heart", false, true),
    WEAPON("weapon", false, true),
    ARMOR("armor", false, true);

    private final String name;
    private final boolean collision;
    private final boolean pickupable;

    ObjectType(String name, boolean collision, boolean pickupable) {
        this.name = name;
        this.collision = collision;
        this.pickupable = pickupable;
    }

    public String getName() {
        return name;
    }

    public boolean isCollision() {
        return collision;
    }

    public boolean isPickupable() {
        return pickupable;
    }

    public boolean hasCollisions() {
        return collision;
    }
}
