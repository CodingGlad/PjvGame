package org.example.handlers;


import org.example.handlers.types.GameStateType;
import org.example.handlers.types.MenuSelectionType;
import org.example.handlers.types.PauseSelectionType;
import org.example.views.UserInterfaceView;

import java.awt.*;

public class UserInterfaceHandler {

    private final UserInterfaceView view;

    public UserInterfaceHandler() {
        this.view = new UserInterfaceView();
    }

    public void drawInterface(Graphics2D g2, GameStateType gameState,
                              MenuSelectionType menuCursor, PauseSelectionType pauseCursor,
                              int numberOfKeys) {
        switch (gameState) {
            case RUNNING -> view.drawGame(g2, numberOfKeys);
            case PAUSE -> view.drawPause(g2, pauseCursor);
            case MAIN_MENU -> view.drawMenu(g2, menuCursor);
        }
    }
}
