package org.example.handlers;

import org.example.handlers.types.GameStateType;
import org.example.handlers.types.MenuSelectionType;

import java.util.Objects;

public class GameStateHandler {
    private GameStateType stateType;

    private MenuSelectionType cursorState;

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

    public MenuSelectionType getCursorState() {
        return cursorState;
    }

    public void setCursorState(MenuSelectionType cursorState) {
        this.cursorState = cursorState;
    }

    public void selectMenuOption() {
        if (Objects.nonNull(cursorState)){
            switch (cursorState) {
                case NEW_GAME -> this.stateType = GameStateType.RUNNING;
                case LOAD_GAME -> this.stateType = GameStateType.MAIN_MENU; //TODO LOADING
                case QUIT -> System.exit(0);
            }
        }
    }
}
