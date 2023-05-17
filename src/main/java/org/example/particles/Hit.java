package org.example.particles;

import org.example.gameobjects.types.ParticleType;
import org.example.utils.WorldCoordinates;

/**
 * Represents a hit particle in the game.
 * Extends the Particle class.
 */
public class Hit extends Particle {

    /**
     * Constructs a hit particle with the specified world coordinates.
     *
     * @param worldCoordinates the world coordinates of the hit particle
     */
    public Hit(WorldCoordinates worldCoordinates) {
        super(worldCoordinates, ParticleType.HIT);
    }
}
