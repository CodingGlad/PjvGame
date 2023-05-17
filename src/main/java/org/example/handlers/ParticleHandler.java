package org.example.handlers;

import org.example.entities.Player;
import org.example.particles.Hit;
import org.example.particles.Particle;
import org.example.utils.WorldCoordinates;
import org.example.views.ParticleView;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public class ParticleHandler {
    private static final Logger LOGGER = Logger.getLogger(Particle.class.getName());
    private List<Particle> displayedParticles;
    private final ParticleView view;

    public ParticleHandler() {
        displayedParticles = new LinkedList<>();
        this.view = new ParticleView();
    }

    public void addHitParticle(WorldCoordinates worldCoordinates) {
        displayedParticles.add(new Hit(worldCoordinates));
    }

    public void drawParticle(Graphics2D g2, Player player) {
        for (Particle particle: displayedParticles) {
            if (particle.getSpriteNumber() != 4) {
                view.drawParticles(g2, particle, player);
            }
        }
    }

    public void update() {
        List<Particle> particlesToRemove = new ArrayList<>();

        for (Particle particle: displayedParticles) {
            particle.incrementCounter();

            if (particle.getSpriteNumber() == 5) {
                particlesToRemove.add(particle);
            }
        }

        displayedParticles.removeAll(particlesToRemove);
    }
}
