package org.example.particles;

import org.example.gameobjects.types.ParticleType;
import org.example.utils.WorldCoordinates;

/**
 * The abstract base class for particles in the game.
 */
public abstract class Particle {
    private final WorldCoordinates worldCoordinates;
    private ParticleType particleType;
    private int spriteCounter;

    /**
     * Constructs a particle with the specified world coordinates and particle type.
     *
     * @param worldCoordinates the world coordinates of the particle
     * @param particleType the type of the particle
     */
    public Particle(WorldCoordinates worldCoordinates, ParticleType particleType) {
        this.worldCoordinates = worldCoordinates;
        this.particleType = particleType;
        this.spriteCounter = 0;
    }

    /**
     * Increments the sprite counter.
     */
    public void incrementCounter() {
        ++spriteCounter;
    }

    /**
     * Returns the current sprite counter value.
     *
     * @return the sprite counter value
     */
    public int getSpriteCounter() {
        return spriteCounter;
    }

    /**
     * Returns the X coordinate of the particle in the world.
     *
     * @return the X coordinate of the particle
     */
    public int getWorldX() {
        return worldCoordinates.getWorldX();
    }

    /**
     * Returns the Y coordinate of the particle in the world.
     *
     * @return the Y coordinate of the particle
     */
    public int getWorldY() {
        return worldCoordinates.getWorldY();
    }

    /**
     * Returns the type of the particle.
     *
     * @return the particle type
     */
    public ParticleType getParticleType() {
        return particleType;
    }

    /**
     * Returns the sprite number based on the current sprite counter value.
     * The sprite number determines the appearance of the particle.
     *
     * @return the sprite number
     */
    public int getSpriteNumber() {
        if (spriteCounter < 5) {
            return 0;
        } else if (spriteCounter < 10) {
            return 1;
        } else if (spriteCounter < 15) {
            return 2;
        } else if (spriteCounter < 20) {
            return 3;
        } else {
            return 4;
        }
    }
}
