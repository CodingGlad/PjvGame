package org.example.handlers;


import org.example.entities.Entity;
import org.example.entities.Player;
import org.example.handlers.types.GameStateType;
import org.example.handlers.types.MenuSelectionType;
import org.example.handlers.types.PauseSelectionType;
import org.example.views.UserInterfaceView;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The UserInterfaceHandler class is responsible for managing the user interface elements and drawing them on the screen.
 */
public class UserInterfaceHandler {
    private final UserInterfaceView view;

    /**
     * Constructs a UserInterfaceHandler object and initializes the associated UserInterfaceView.
     */
    public UserInterfaceHandler() {
        this.view = new UserInterfaceView();
    }

    /**
     * Draws the appropriate user interface elements based on the current game state.
     *
     * @param g2           The Graphics2D object used for drawing.
     * @param gameState    The current game state.
     * @param menuCursor   The menu selection cursor type.
     * @param pauseCursor  The pause selection cursor type.
     * @param player       The player entity.
     * @param enemy        The enemy entity (used in the FIGHTING game state).
     */
    public void drawInterface(Graphics2D g2, GameStateType gameState,
                              MenuSelectionType menuCursor, PauseSelectionType pauseCursor,
                              Player player, Entity enemy, boolean isLoggerOn) {
        switch (gameState) {
            case RUNNING -> view.drawGame(g2, player);
            case PAUSE -> view.drawPause(g2, pauseCursor);
            case MAIN_MENU -> view.drawMenu(g2, menuCursor, isLoggerOn);
            case FIGHTING -> view.drawFight(g2, enemy, player);
            case END -> view.drawEnd(g2);
        }
    }
}

