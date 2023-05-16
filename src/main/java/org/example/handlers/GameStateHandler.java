package org.example.handlers;

import org.example.entities.Enemy;
import org.example.entities.Entity;
import org.example.entities.Player;
import org.example.entities.types.HorizontalDirectionType;
import org.example.handlers.types.GameStateType;
import org.example.handlers.types.MenuSelectionType;
import org.example.handlers.types.PauseSelectionType;

import java.io.*;
import java.util.Objects;

public class GameStateHandler {
    private GameStateType stateType;
    private Enemy opponent;
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

    public void setPause() {
        stateType = GameStateType.PAUSE;
    }

    public void setRunning() {
        stateType = GameStateType.RUNNING;
    }

    public void setFighting(Enemy opponent, Entity player) {
        stateType = GameStateType.FIGHTING;

        if (player.getHorizontalDirection().equals(HorizontalDirectionType.RIGHT)) {
            opponent.setHorizontalDirection(HorizontalDirectionType.LEFT);
        } else {
            opponent.setHorizontalDirection(HorizontalDirectionType.RIGHT);
        }

        this.opponent = opponent;
    }

    public void setDying() {
        stateType = GameStateType.DYING;
    }

    public void setEnd() {
        stateType = GameStateType.END;
    }

    public void setLoading() {
        stateType = GameStateType.LOADING;
    }

    public void setQuit() {
        stateType = GameStateType.QUIT;
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
                case NEW_GAME -> this.stateType = GameStateType.STARTING;
                case LOAD_GAME -> this.stateType = GameStateType.LOADING;
                case QUIT -> System.exit(0);
            }
        }
    }

    public void selectPauseOption() {
        if (Objects.nonNull(pauseCursorState)) {
            switch (pauseCursorState) {
                case RESUME -> switchPause();
                case SAVE -> stateType = GameStateType.SAVING;
                case QUIT -> System.exit(0);
            }
        }
    }

    public Enemy getOpponent() {
        return opponent;
    }
}
