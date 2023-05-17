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
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameStateHandler {
    private static final Logger LOGGER = Logger.getLogger(GameStateHandler.class.getName());
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

        LOGGER.log(Level.INFO, "Switched game state to " + getStateType().toString());
    }

    public void setRunning() {
        stateType = GameStateType.RUNNING;

        LOGGER.log(Level.INFO, "Switched game state to RUNNING.");
    }

    public void setFighting(Enemy opponent, Entity player) {
        stateType = GameStateType.FIGHTING;
        LOGGER.log(Level.INFO, "Switched game state to FIGHTING.");

        if (player.getHorizontalDirection().equals(HorizontalDirectionType.RIGHT)) {
            opponent.setHorizontalDirection(HorizontalDirectionType.LEFT);
        } else {
            opponent.setHorizontalDirection(HorizontalDirectionType.RIGHT);
        }

        this.opponent = opponent;
    }

    public void setDying() {
        stateType = GameStateType.DYING;
        LOGGER.log(Level.INFO, "Switched game state to DYING.");
    }

    public void setEnd() {
        stateType = GameStateType.END;
        LOGGER.log(Level.INFO, "Switched game state to END.");
    }

    public void setLoading() {
        stateType = GameStateType.LOADING;
        LOGGER.log(Level.INFO, "Switched game state to LOADING.");
    }

    public void setQuit() {
        stateType = GameStateType.QUIT;
        LOGGER.log(Level.INFO, "Switched game state to QUIT.");
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
            LOGGER.log(Level.INFO, "Player has selected " + menuCursorState.toString() + " from menu.");

            switch (menuCursorState) {
                case NEW_GAME -> this.stateType = GameStateType.STARTING;
                case LOAD_GAME -> this.stateType = GameStateType.LOADING;
                case QUIT -> System.exit(0);
            }
        }
    }

    public void selectPauseOption() {
        if (Objects.nonNull(pauseCursorState)) {

            LOGGER.log(Level.INFO, "Player has selected " + pauseCursorState.toString() + " from menu.");
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
