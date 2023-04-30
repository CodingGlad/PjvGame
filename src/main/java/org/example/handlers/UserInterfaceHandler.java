package org.example.handlers;

import org.example.entities.Player;
import org.example.gameobjects.Key;
import org.example.gameobjects.types.KeyType;
import org.example.gameobjects.types.ObjectType;
import org.example.handlers.types.CursorStateType;
import org.example.handlers.types.GameStateType;
import org.example.utils.WorldCoordinates;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.handlers.PanelHandler.*;

public class UserInterfaceHandler {

    private Font gameFont;
    private Font menuTitleFont;
    private Font menuSelectionFont;
    private final Player player;
    private final BufferedImage keyImage;
    private CursorStateType cursorState;

    public UserInterfaceHandler(Player player) {
        this.player = player;
        this.gameFont = new Font("Arial", Font.PLAIN, 20);
        this.menuTitleFont = new Font("Arial", Font.BOLD, 40);
        this.menuSelectionFont = new Font("Arial", Font.BOLD, 30);
        //TODO change getting of key image
        this.keyImage = (new Key(ObjectType.KEY, KeyType.GOLD, new WorldCoordinates(0, 0))).getStaticImage();
        this.cursorState = CursorStateType.NEW_GAME;
    }

    public void drawGame(Graphics2D g2, GameStateType stateType) {
        if (stateType.equals(GameStateType.RUNNING)) {
            g2.setFont(gameFont);
            g2.setColor(Color.black);
            g2.drawImage(keyImage, TILE_SIZE/2, TILE_SIZE/2, TILE_SIZE, TILE_SIZE, null);
            g2.drawString("x " + player.getNumberOfKeys(), 50, 40);
        } else if (stateType.equals(GameStateType.PAUSE)) {
            g2.drawString("PAUSED",
                    SCREEN_WIDTH/2 - ((int)g2.getFontMetrics().getStringBounds("PAUSED", g2).getWidth()),
                    SCREEN_HEIGHT/2);
        }
    }

    public void drawMenu(Graphics2D g2) {
        g2.setFont(menuTitleFont);
        g2.setColor(Color.white);
        g2.drawString("Main Menu fuckerrrrrr", 50, 40);
        g2.setFont(menuSelectionFont);
        g2.drawString("NEW GAME", 80, 170);
        g2.drawString("LOAD GAME", 80, 210);
        g2.drawString("QUIT", 80, 250);
    }

}
