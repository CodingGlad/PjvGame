package org.example.handlers;

import org.example.entities.Player;
import org.example.gameobjects.Key;
import org.example.gameobjects.types.KeyType;
import org.example.gameobjects.types.ObjectType;
import org.example.utils.WorldCoordinates;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.handlers.PanelHandler.TILE_SIZE;

public class UserInterfaceHandler {

    private Font gameFont;
    private final Player player;
    private final BufferedImage keyImage;

    public UserInterfaceHandler(Player player) {
        this.player = player;
        this.gameFont = new Font("Arial", Font.PLAIN, 20);
        //TODO change getting of key image
        this.keyImage = (new Key(ObjectType.KEY, KeyType.GOLD, new WorldCoordinates(0, 0))).getStaticImage();
    }

    public void draw(Graphics2D g2) {
        g2.setFont(gameFont);
        g2.setColor(Color.black);
        g2.drawImage(keyImage, TILE_SIZE/2, TILE_SIZE/2, TILE_SIZE, TILE_SIZE, null);
        g2.drawString("x " + player.getNumberOfKeys(), 50, 40);
    }

}
