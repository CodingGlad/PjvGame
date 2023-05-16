package org.example.handlers;

import org.example.entities.types.ActivityType;
import org.example.entities.types.HorizontalDirectionType;
import org.example.gameobjects.types.ParticleType;
import org.example.views.ParticleView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class ParticleHandler {
    private final Map<ParticleType, List<BufferedImage>> particleSprites;
    private final ParticleView view;

    public ParticleHandler() {
        particleSprites = new HashMap<>();
        setAllParticleSprites();
        this.view = new ParticleView();
    }

    private void setAllParticleSprites() {
        for (ParticleType type: ParticleType.values()) {
            setSpritesForParticleType(type);
        }
    }

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
