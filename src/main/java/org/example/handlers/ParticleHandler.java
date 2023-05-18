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

/**
 * The ParticleHandler class is responsible for managing and handling particles in the game.
 */
public class ParticleHandler {
    private List<Particle> displayedParticles;
    private final ParticleView view;

    /**
     * Constructs a ParticleHandler object and initializes the associated ParticleView.
     */
    public ParticleHandler() {
        displayedParticles = new LinkedList<>();
        this.view = new ParticleView();
    }

    /**
     * Adds a hit particle at the specified world coordinates.
     *
     * @param worldCoordinates The world coordinates at which the hit particle will be added.
     */
    public void addHitParticle(WorldCoordinates worldCoordinates) {
        displayedParticles.add(new Hit(worldCoordinates));
    }

    /**
     * Draws the particles on the screen.
     *
     * @param g2     The Graphics2D object used for drawing.
     * @param player The player entity.
     */
    public void drawParticle(Graphics2D g2, Player player) {
        for (Particle particle : displayedParticles) {
            if (particle.getSpriteNumber() != 4) {
                view.drawParticles(g2, particle, player);
            }
        }
    }

    /**
     * Updates the particles.
     * Removes particles that have reached their end state.
     */
    public void update() {
        List<Particle> particlesToRemove = new ArrayList<>();

        for (Particle particle : displayedParticles) {
            particle.incrementCounter();

            if (particle.getSpriteNumber() == 5) {
                particlesToRemove.add(particle);
            }
        }

        displayedParticles.removeAll(particlesToRemove);
    }
}

