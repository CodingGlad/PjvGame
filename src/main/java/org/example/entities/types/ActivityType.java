package org.example.entities.types;

/**
 Represents the types of activities of entities.
 Each activity type has a corresponding string representation.
 */
public enum ActivityType {
    /**
     * Represents the idle activity.
     * The string representation is "idle".
     */
    IDLE("idle"),
    /**
     * Represents the walking activity.
     * The string representation is "walk".
     */
    WALK("walk"),

    /**
     * Represents the dying activity.
     * The string representation is "dying".
     */
    DYING("dying");

    private final String activityString;

    /**
     * Constructs an ActivityType with the specified string representation.
     *
     * @param activityString the string representation of the activity
     */
    ActivityType(String activityString) {
        this.activityString = activityString;
    }

    /**
     * Returns the string representation of the activity.
     *
     * @return the string representation of the activity
     */
    public String getActivityString() {
        return activityString;
    }
}
