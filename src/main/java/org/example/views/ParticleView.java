package org.example.views;

import org.example.entities.Player;
import org.example.gameobjects.types.ParticleType;
import org.example.particles.Particle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static org.example.utils.GameConstants.TILE_SIZE;

/**
 * Handles the rendering of particles on the screen.
 */
public class ParticleView {
    private final Map<ParticleType, List<BufferedImage>> particleSprites;

    /**
     * Constructs a new ParticleView object and initializes the particle sprites.
     */
    public ParticleView() {
        particleSprites = new EnumMap<>(ParticleType.class);
        setAllParticleSprites();
    }

    /**
     * Draws the particle on the screen relative to the player's position.
     *
     * @param g2      The graphics context used for drawing.
     * @param particle The particle to be drawn.
     * @param player  The player object.
     */
    public void drawParticles(Graphics2D g2, Particle particle, Player player) {
        final int screenX = particle.getWorldX() - player.getWorldX() + player.getScreenX();
        final int screenY = particle.getWorldY() - player.getWorldY() + player.getScreenY();

        g2.drawImage(particleSprites.get(particle.getParticleType()).get(particle.getSpriteNumber()),
                screenX, screenY, TILE_SIZE, TILE_SIZE, null);
    }

    /**
     * Sets the sprite images for all particle types.
     */
    private void setAllParticleSprites() {
        for (ParticleType type : ParticleType.values()) {
            setSpritesForParticleType(type);
        }
    }

    /**
     * Sets the sprite images for a specific particle type.
     *
     * @param type The particle type.
     */
    private void setSpritesForParticleType(ParticleType type) {
        try {
            List<BufferedImage> tmpSprites = new ArrayList<>();

            for (int i = 1; i < type.getNumberOfSprites() + 1; ++i) {
                tmpSprites.add(ImageIO.read(
                        Objects.requireNonNull(getClass().getResourceAsStream(
                                "/sprites/particles/" + type.toString().toLowerCase() + "/" +
                                        type.toString().toLowerCase() + "-" + i + ".png"))
                ));
            }

            particleSprites.put(type, tmpSprites);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
