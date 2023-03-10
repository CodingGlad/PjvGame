package org.example.entities.types;

public enum ActivityType {
    IDLE("idle"),
    WALK("walk");

    private final String activityString;

    ActivityType(String activityString) {
        this.activityString = activityString;
    }

    public String getActivityString() {
        return activityString;
    }
}
