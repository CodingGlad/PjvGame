package org.example.handlers;

import org.example.handlers.types.GameStateType;
import org.example.handlers.types.MenuSelectionType;
import org.example.handlers.types.PauseSelectionType;

import java.io.*;
import java.util.Objects;

public class GameStateHandler {
    private GameStateType stateType;

    private MenuSelectionType menuCursorState;
    private PauseSelectionType pauseCursorState;

    public GameStateHandler() {
        this.stateType = GameStateType.MAIN_MENU;
    }

    public GameStateType getStateType() {
        return stateType;
    }

    public void switchPause() {
        if (stateType.equals(GameStateType.PAUSE)) {
            stateType = GameStateType.RUNNING;
        } else if (stateType.equals(GameStateType.RUNNING)) {
            stateType = GameStateType.PAUSE;
        }
    }

    public MenuSelectionType getMenuCursorState() {
        return menuCursorState;
    }

    public PauseSelectionType getPauseCursorState() {
        return pauseCursorState;
    }

    public void setMenuCursorState(MenuSelectionType menuCursorState) {
        this.menuCursorState = menuCursorState;
    }

    public void setPauseCursorState(PauseSelectionType pauseCursorState) {
        this.pauseCursorState = pauseCursorState;
    }

    public void selectMenuOption() {
        if (Objects.nonNull(menuCursorState)){
            switch (menuCursorState) {
                case NEW_GAME -> this.stateType = GameStateType.RUNNING;
                case LOAD_GAME -> this.stateType = GameStateType.MAIN_MENU; //TODO LOADING
                case QUIT -> System.exit(0);
            }
        }
    }

    public void selectPauseOption() {
        if (Objects.nonNull(pauseCursorState)) {
            switch (pauseCursorState) {
                case RESUME -> switchPause();
                case SAVE -> saveGameState(); //TODO SAVE
                case QUIT -> System.exit(0);
            }
        }
    }

    public void saveGameState() {
        try {
            FileWriter fw = new FileWriter("save.txt");

            fw.write("5");

            fw.close();

            switchPause();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
