package org.example.views;

import org.example.entities.Entity;
import org.example.entities.Player;
import org.example.gameobjects.Heart;
import org.example.gameobjects.Key;
import org.example.gameobjects.types.KeyType;
import org.example.gameobjects.types.ObjectType;
import org.example.handlers.types.MenuSelectionType;
import org.example.handlers.types.PauseSelectionType;
import org.example.utils.WorldCoordinates;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static org.example.utils.GameConstants.TILE_SIZE;

public class UserInterfaceView{

    private Font gameFont;
    private Font menuTitleFont;
    private Font menuSelectionFont;
    private final BufferedImage keyImage;
    private final BufferedImage heartImage;

    public UserInterfaceView() {
        this.gameFont = new Font("Arial", Font.BOLD, 20);
        this.menuTitleFont = new Font("Arial", Font.BOLD, 40);
        this.menuSelectionFont = new Font("Arial", Font.BOLD, 30);
        this.keyImage = (new Key(ObjectType.KEY, KeyType.GOLD, new WorldCoordinates(0, 0))).getStaticImage();
        this.heartImage = (new Heart(ObjectType.HEART, new WorldCoordinates(0, 0))).getStaticImage();
    }

    public void drawGame(Graphics2D g2, Player player) {
        g2.setFont(gameFont);
        g2.setColor(Color.black);
        g2.drawImage(keyImage, TILE_SIZE/2, TILE_SIZE/2, TILE_SIZE, TILE_SIZE, null);
        g2.drawString("x " + player.getNumberOfKeys(), 50, 40);
        g2.drawImage(heartImage, 460, 15, TILE_SIZE, TILE_SIZE, null);
        g2.drawString(String.valueOf(player.getHealth()), 420, 40);
        if (Objects.nonNull(player.getInventory().getWeaponEquipped())) {
            g2.drawImage(player.getInventory().getWeaponEquipped().getStaticImage(), 430, 52, TILE_SIZE, TILE_SIZE, null);
        }
        if (Objects.nonNull(player.getInventory().getArmorEquipped())) {
            g2.drawImage(player.getInventory().getArmorEquipped().getStaticImage(), 460, 45, TILE_SIZE, TILE_SIZE, null);
        }
    }

    public void drawMenu(Graphics2D g2, MenuSelectionType menuCursor) {
        g2.setFont(menuTitleFont);
        g2.setColor(Color.white);
        g2.drawString("Main Menu fuckerrrrrr", 50, 40);
        g2.setFont(menuSelectionFont);
        g2.drawString("1-NEW GAME", MenuSelectionType.NEW_GAME.getX(), MenuSelectionType.NEW_GAME.getY());
        g2.drawString("2-LOAD GAME", MenuSelectionType.LOAD_GAME.getX(), MenuSelectionType.LOAD_GAME.getY());
        g2.drawString("3-QUIT", MenuSelectionType.QUIT.getX(), MenuSelectionType.QUIT.getY());
        if (Objects.nonNull(menuCursor)) {
            g2.drawString(">", 50, menuCursor.getY());
        }
    }

    public void drawPause(Graphics2D g2, PauseSelectionType pauseCursor) {
        g2.setFont(menuTitleFont);
        g2.drawString("PAUSED", 50, 80);
        g2.setFont(menuSelectionFont);
        g2.drawString("1-RESUME", PauseSelectionType.RESUME.getX(), PauseSelectionType.RESUME.getY());
        g2.drawString("2-SAVE", PauseSelectionType.SAVE.getX(), PauseSelectionType.SAVE.getY());
        g2.drawString("3-QUIT", PauseSelectionType.QUIT.getX(), PauseSelectionType.QUIT.getY());
        if (Objects.nonNull(pauseCursor)) {
            g2.drawString(">", 50, pauseCursor.getY());
        }
    }

    public void drawFight(Graphics2D g2, Entity enemy, Player player) {
        drawGame(g2, player);
        g2.setFont(gameFont.deriveFont(18f));
        g2.drawString(enemy.getEntityType().toString(), 400, 300);
        g2.drawString("Health: " + enemy.getHealth(), 390, 325);
    }

    public void drawEnd(Graphics2D g2) {
        g2.setFont(menuTitleFont);
        g2.setColor(Color.BLACK);
        g2.drawString("You have died!", 110, 100);
        g2.setFont(gameFont);
        g2.drawString("Press 'Q' to quit.", 170, 140);
        g2.drawString("Press 'L' to load last save.", 130, 170);
    }
}
