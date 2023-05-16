package org.example.gameobjects.types;

public enum ParticleType {
    HIT(4);

    private final int numberOfSprites;

    ParticleType(int numberOfSprites) {
        this.numberOfSprites = numberOfSprites;
    }

    public int getNumberOfSprites() {
        return numberOfSprites;
    }
}
