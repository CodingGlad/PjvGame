package org.example.gameobjects.types;

/**
 * Enumeration of particle types.
 */
public enum ParticleType {
    /**
     * Particle type for hit particles.
     */
    HIT(4);

    private final int numberOfSprites;

    /**
     * Constructs a ParticleType with the specified number of sprites.
     *
     * @param numberOfSprites the number of sprites for the particle type.
     */
    ParticleType(int numberOfSprites) {
        this.numberOfSprites = numberOfSprites;
    }

    /**
     * Returns the number of sprites for the particle type.
     *
     * @return the number of sprites.
     */
    public int getNumberOfSprites() {
        return numberOfSprites;
    }
}
