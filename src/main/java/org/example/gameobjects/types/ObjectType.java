package org.example.gameobjects.types;

/**
 * Enum representing different types of game objects.
 */
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

    /**
     * Constructs an ObjectType enum with the specified name, collision property, and pickupable property.
     *
     * @param name       The name of the object type.
     * @param collision  Indicates if the object type has collision.
     * @param pickupable Indicates if the object type is pickupable.
     */
    ObjectType(String name, boolean collision, boolean pickupable) {
        this.name = name;
        this.collision = collision;
        this.pickupable = pickupable;
    }

    /**
     * Returns the name of the object type.
     *
     * @return The name of the object type.
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if the object type has collision.
     *
     * @return {@code true} if the object type has collision, {@code false} otherwise.
     */
    public boolean isCollision() {
        return collision;
    }

    /**
     * Checks if the object type is pickupable.
     *
     * @return {@code true} if the object type is pickupable, {@code false} otherwise.
     */
    public boolean isPickupable() {
        return pickupable;
    }

    /**
     * Checks if the object type has collisions.
     * This method is equivalent to calling {@link #isCollision()}.
     *
     * @return {@code true} if the object type has collisions, {@code false} otherwise.
     */
    public boolean hasCollisions() {
        return collision;
    }
}
