package org.example.handlers;

import org.example.entities.Player;
import org.example.gameobjects.Key;
import org.example.gameobjects.types.KeyType;
import org.example.gameobjects.types.ObjectType;
import org.example.handlers.types.MenuSelectionType;
import org.example.handlers.types.GameStateType;
import org.example.handlers.types.PauseSelectionType;
import org.example.utils.WorldCoordinates;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static org.example.handlers.PanelHandler.*;

public class UserInterfaceHandler {

    private Font gameFont;
    private Font menuTitleFont;
    private Font menuSelectionFont;
    private final Player player;
    private final BufferedImage keyImage;

    public UserInterfaceHandler(Player player) {
        this.player = player;
        this.gameFont = new Font("Arial", Font.PLAIN, 20);
        this.menuTitleFont = new Font("Arial", Font.BOLD, 40);
        this.menuSelectionFont = new Font("Arial", Font.BOLD, 30);
        //TODO change getting of key image
        this.keyImage = (new Key(ObjectType.KEY, KeyType.GOLD, new WorldCoordinates(0, 0))).getStaticImage();
    }

    public void drawGame(Graphics2D g2, GameStateHandler gameState) {
        if (gameState.getStateType().equals(GameStateType.RUNNING)) {
            g2.setFont(gameFont);
            g2.setColor(Color.black);
            g2.drawImage(keyImage, TILE_SIZE/2, TILE_SIZE/2, TILE_SIZE, TILE_SIZE, null);
            g2.drawString("x " + player.getNumberOfKeys(), 50, 40);
        } else if (gameState.getStateType().equals(GameStateType.PAUSE)) {
            g2.setFont(menuTitleFont);
            g2.drawString("PAUSED", 50, 80);
            g2.setFont(menuSelectionFont);
            g2.drawString("1-RESUME", PauseSelectionType.RESUME.getX(), PauseSelectionType.RESUME.getY());
            g2.drawString("2-SAVE", PauseSelectionType.SAVE.getX(), PauseSelectionType.SAVE.getY());
            g2.drawString("3-QUIT", PauseSelectionType.QUIT.getX(), PauseSelectionType.QUIT.getY());
            if (Objects.nonNull(gameState.getPauseCursorState())) {
                g2.drawString(">", 50, gameState.getPauseCursorState().getY());
            }
        } else if (gameState.getStateType().equals(GameStateType.MAIN_MENU)) {
            g2.setFont(menuTitleFont);
            g2.setColor(Color.white);
            g2.drawString("Main Menu fuckerrrrrr", 50, 40);
            g2.setFont(menuSelectionFont);
            g2.drawString("1-NEW GAME", MenuSelectionType.NEW_GAME.getX(), MenuSelectionType.NEW_GAME.getY());
            g2.drawString("2-LOAD GAME", MenuSelectionType.LOAD_GAME.getX(), MenuSelectionType.LOAD_GAME.getY());
            g2.drawString("3-QUIT", MenuSelectionType.QUIT.getX(), MenuSelectionType.QUIT.getY());
            if (Objects.nonNull(gameState.getMenuCursorState())) {
                g2.drawString(">", 50, gameState.getMenuCursorState().getY());
            }
        }
    }
}
