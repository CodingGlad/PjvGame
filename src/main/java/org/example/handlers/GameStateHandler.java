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
     * @param isLoggerOn Whether logger is turned on.
     */
    public void switchPause(boolean isLoggerOn) {
        if (stateType.equals(GameStateType.PAUSE)) {
            stateType = GameStateType.RUNNING;
        } else if (stateType.equals(GameStateType.RUNNING)) {
            stateType = GameStateType.PAUSE;
        }
        if (isLoggerOn) {
            LOGGER.log(Level.INFO, "Switched game state to " + getStateType().toString());
        }
    }

    /**
     * Sets the game state to RUNNING.
     * Logs a message indicating the state change.
     * @param isLoggerOn Whether logger is turned on.
     */
    public void setRunning(boolean isLoggerOn) {
        stateType = GameStateType.RUNNING;
        if (isLoggerOn) {
            LOGGER.log(Level.INFO, "Switched game state to RUNNING.");
        }
    }

    /**
     * Sets the game state to FIGHTING.
     * Logs a message indicating the state change.
     * Configures the opponent's horizontal direction based on the player's direction.
     *
     * @param opponent The opponent to fight against.
     * @param player   The player entity.
     * @param isLoggerOn Whether logger is turned on.
     */
    public void setFighting(Enemy opponent, Entity player, boolean isLoggerOn) {
        stateType = GameStateType.FIGHTING;
        if (isLoggerOn) {
            LOGGER.log(Level.INFO, "Switched game state to FIGHTING.");
        }

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
     * @param isLoggerOn Whether logger is turned on.
     */
    public void setDying(boolean isLoggerOn) {
        stateType = GameStateType.DYING;
        if (isLoggerOn) {
            LOGGER.log(Level.INFO, "Switched game state to DYING.");
        }
    }

    /**
     * Sets the game state to END.
     * Logs a message indicating the state change.
     * @param isLoggerOn Whether logger is turned on.
     */
    public void setEnd(boolean isLoggerOn) {
        stateType = GameStateType.END;
        if (isLoggerOn) {
            LOGGER.log(Level.INFO, "Switched game state to END.");
        }
    }

    /**
     * Sets the game state to LOADING.
     * Logs a message indicating the state change.
     * @param isLoggerOn Whether logger is turned on.
     */
    public void setLoading(boolean isLoggerOn) {
        stateType = GameStateType.LOADING;
        if (isLoggerOn) {
            LOGGER.log(Level.INFO, "Switched game state to LOADING.");
        }
    }

    /**
     * Sets the game state to QUIT.
     * Logs a message indicating the state change.
     * @param isLoggerOn Whether logger is turned on.
     */
    public void setQuit(boolean isLoggerOn) {
        stateType = GameStateType.QUIT;
        if (isLoggerOn) {
            LOGGER.log(Level.INFO, "Switched game state to QUIT.");
        }
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
     * @param isLoggerOn Whether logger is turned on.
     */
    public void selectMenuOption(boolean isLoggerOn) {
        if (Objects.nonNull(menuCursorState)) {
            if (isLoggerOn) {
                LOGGER.log(Level.INFO, "Player has selected " + menuCursorState.toString() + " from menu.");
            }

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
     * @param isLoggerOn Whether logger is turned on.
     */
    public void selectPauseOption(boolean isLoggerOn) {
        if (Objects.nonNull(pauseCursorState)) {
            if (isLoggerOn) {
                LOGGER.log(Level.INFO, "Player has selected " + pauseCursorState.toString() + " from menu.");
            }

            switch (pauseCursorState) {
                case RESUME -> switchPause(isLoggerOn);
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
