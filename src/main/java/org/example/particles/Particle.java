package org.example.particles;

import org.example.gameobjects.types.ParticleType;
import org.example.utils.WorldCoordinates;

public abstract class Particle {
    private final WorldCoordinates worldCoordinates;
    private ParticleType particleType;
    private int spriteCounter;

    public Particle(WorldCoordinates worldCoordinates, ParticleType particleType) {
        this.worldCoordinates = worldCoordinates;
        this.particleType = particleType;
        this.spriteCounter = 0;
    }

    public void incrementCounter() {
        ++spriteCounter;
    }

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public int getWorldX() {
        return worldCoordinates.getWorldX();
    }

    public int getWorldY() {
        return worldCoordinates.getWorldY();
    }

    public ParticleType getParticleType() {
        return particleType;
    }

    public int getSpriteNumber() {
        if (spriteCounter < 5) {
            return 0;
        } else if (spriteCounter < 10) {
            return 1;
        } else if (spriteCounter < 15) {
            return 2;
        } else if (spriteCounter < 20){
            return 3;
        } else {
            return 4;
        }
    }
}
