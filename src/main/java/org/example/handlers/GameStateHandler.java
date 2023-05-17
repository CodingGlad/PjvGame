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

/**
 * Handles the game state and related operations.
 */
public class GameStateHandler {
    private static final Logger LOGGER = Logger.getLogger(GameStateHandler.class.getName());
    private GameStateType stateType;
    private Enemy opponent;
    private MenuSelectionType menuCursorState;
    private PauseSelectionType pauseCursorState;

    /**
     * Constructs a new GameStateHandler object.
     * Sets the initial game state to MAIN_MENU.
     */
    public GameStateHandler() {
        this.stateType = GameStateType.MAIN_MENU;
    }

    /**
     * Returns the current game state.
     *
     * @return The game state.
     */
    public GameStateType getStateType() {
        return stateType;
    }

    /**
     * Switches the game state between PAUSE and RUNNING.
     * Logs a message indicating the state change.
     */
    public void switchPause() {
        if (stateType.equals(GameStateType.PAUSE)) {
            stateType = GameStateType.RUNNING;
        } else if (stateType.equals(GameStateType.RUNNING)) {
            stateType = GameStateType.PAUSE;
        }

        LOGGER.log(Level.INFO, "Switched game state to " + getStateType().toString());
    }

    /**
     * Sets the game state to RUNNING.
     * Logs a message indicating the state change.
     */
    public void setRunning() {
        stateType = GameStateType.RUNNING;

        LOGGER.log(Level.INFO, "Switched game state to RUNNING.");
    }

    /**
     * Sets the game state to FIGHTING.
     * Logs a message indicating the state change.
     * Configures the opponent's horizontal direction based on the player's direction.
     *
     * @param opponent The opponent to fight against.
     * @param player   The player entity.
     */
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

    /**
     * Sets the game state to DYING.
     * Logs a message indicating the state change.
     */
    public void setDying() {
        stateType = GameStateType.DYING;
        LOGGER.log(Level.INFO, "Switched game state to DYING.");
    }

    /**
     * Sets the game state to END.
     * Logs a message indicating the state change.
     */
    public void setEnd() {
        stateType = GameStateType.END;
        LOGGER.log(Level.INFO, "Switched game state to END.");
    }

    /**
     * Sets the game state to LOADING.
     * Logs a message indicating the state change.
     */
    public void setLoading() {
        stateType = GameStateType.LOADING;
        LOGGER.log(Level.INFO, "Switched game state to LOADING.");
    }

    /**
     * Sets the game state to QUIT.
     * Logs a message indicating the state change.
     */
    public void setQuit() {
        stateType = GameStateType.QUIT;
        LOGGER.log(Level.INFO, "Switched game state to QUIT.");
    }

    /**
     * Returns the current menu cursor state.
     *
     * @return The menu cursor state.
     */
    public MenuSelectionType getMenuCursorState() {
        return menuCursorState;
    }

    /**
     * Returns the current pause cursor state.
     *
     * @return The pause cursor state.
     */
    public PauseSelectionType getPauseCursorState() {
        return pauseCursorState;
    }

    /**
     * Sets the menu cursor state to the specified state.
     *
     * @param menuCursorState The new menu cursor state.
     */
    public void setMenuCursorState(MenuSelectionType menuCursorState) {
        this.menuCursorState = menuCursorState;
    }

    /**
     * Sets the pause cursor state to the specified state.
     *
     * @param pauseCursorState The new pause cursor state.
     */
    public void setPauseCursorState(PauseSelectionType pauseCursorState) {
        this.pauseCursorState = pauseCursorState;
    }

    /**
     * Performs the selected menu option based on the current menu cursor state.
     * Logs a message indicating the selected option.
     * Updates the game state accordingly.
     */
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

    /**
     * Performs the selected pause option based on the current pause cursor state.
     * Logs a message indicating the selected option.
     * Updates the game state accordingly.
     */
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

    /**
     * Returns the opponent object.
     *
     * @return The opponent object.
     */
    public Enemy getOpponent() {
        return opponent;
    }
}
