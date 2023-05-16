package org.example.particles;

import org.example.gameobjects.types.ParticleType;
import org.example.utils.WorldCoordinates;

public class Hit extends Particle {
    public Hit(WorldCoordinates worldCoordinates) {
        super(worldCoordinates, ParticleType.HIT);
    }
}
