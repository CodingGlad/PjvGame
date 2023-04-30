package org.example.handlers;

import org.example.handlers.types.GameStateType;

public class GameStateHandler {
    private GameStateType stateType;

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
}
