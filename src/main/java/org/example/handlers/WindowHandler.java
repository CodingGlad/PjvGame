package org.example.handlers;

import org.example.entities.Player;
import org.example.gameobjects.GameObject;
import org.example.handlers.types.GameStateType;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

//TODO handle public constants
public class WindowHandler extends JPanel{

    public static final int ORIGINAL_TILE_SIZE = 16;
    public static final int SCALE_FACTOR = 2;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE_FACTOR;

    public static final int MAX_SCREEN_COLS = 16;
    public static final int MAX_SCREEN_ROWS = 12;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COLS;
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROWS;

    //TODO config file
    public static final int MAX_WORLD_COL = 50;
    public static final int MAX_WORLD_ROW = 50;
    public static final int WORLD_WIDTH = TILE_SIZE * MAX_WORLD_COL;
    public static final int WORLD_HEIGHT = TILE_SIZE * MAX_WORLD_ROW;

    private UserInterfaceHandler userInterface;

    public WindowHandler(KeyHandler keyHandler) {
        userInterface = new UserInterfaceHandler();

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

//        if (gameState.getStateType().equals(GameStateType.MAIN_MENU)) {
//            userInterface.drawGame(g2, gameState);
//        } else {
//            tileHandler.draw(g2);
//
//            for (GameObject object: displayedObjects) {
//                object.draw(g2, player);
//            }
//
//            player.draw(g2);
//
//            userInterface.drawGame(g2, gameState);
//        }

        g2.dispose();
    }


}
