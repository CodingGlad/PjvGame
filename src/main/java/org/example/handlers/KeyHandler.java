package org.example.handlers;

import org.example.handlers.types.GameStateType;
import org.example.handlers.types.MenuSelectionType;
import org.example.handlers.types.PauseSelectionType;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handler class that listens t users input from keyboard.
 * <br>
 * During the development, I have derived this game loop solution
 * from RyiSnow and his RPG game.
 */
public class KeyHandler implements KeyListener {

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean spacePressed;
    private boolean equipButtonPressed;
    private boolean useButtonPressed;
    private boolean isLoggerOn;

    private GameStateHandler gameState;

    public KeyHandler(GameStateHandler gameState) {
        this.gameState = gameState;
        isLoggerOn = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (gameState.getStateType()) {
            case MAIN_MENU -> menuKeys(code);
            case PAUSE -> pausedKeys(code);
            case RUNNING -> runningKeys(code);
            case FIGHTING -> fightingKeys(code);
            case END -> endKeys(code);
        }
    }

    private void menuKeys(int code) {
        if (code == KeyEvent.VK_1) {
            gameState.setMenuCursorState(MenuSelectionType.NEW_GAME);
        }
        if (code == KeyEvent.VK_2) {
            gameState.setMenuCursorState(MenuSelectionType.LOAD_GAME);
        }
        if (code == KeyEvent.VK_3) {
            gameState.setMenuCursorState(MenuSelectionType.QUIT);
        }
        if (code == KeyEvent.VK_SPACE) {
            gameState.selectMenuOption(isLoggerOn);
        }
        if (code == KeyEvent.VK_L) {
            isLoggerOn = !isLoggerOn;
        }
    }

    private void pausedKeys(int code) {
        if (code == KeyEvent.VK_1) {
            gameState.setPauseCursorState(PauseSelectionType.RESUME);
        }
        if (code == KeyEvent.VK_2) {
            gameState.setPauseCursorState(PauseSelectionType.SAVE);
        }
        if (code == KeyEvent.VK_3) {
            gameState.setPauseCursorState(PauseSelectionType.QUIT);
        }
        if (code == KeyEvent.VK_SPACE) {
            gameState.selectPauseOption(isLoggerOn);
        }
    }

    private void runningKeys(int code) {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            gameState.switchPause(isLoggerOn);
        }
        if (code == KeyEvent.VK_E) {
            equipButtonPressed = true;
        }
        if (code == KeyEvent.VK_F) {
            useButtonPressed = true;
        }
    }

    private void fightingKeys(int code) {
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
    }

    private void endKeys(int code) {
        if (code == KeyEvent.VK_Q) {
            gameState.setQuit(isLoggerOn);
        }
        if (code == KeyEvent.VK_L) {
            gameState.setLoading(isLoggerOn);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
        if (code == KeyEvent.VK_E) {
            equipButtonPressed = false;
        }
        if (code == KeyEvent.VK_F) {
            useButtonPressed = false;
        }
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isSpacePressed() {
        return spacePressed;
    }

    public boolean isEquipButtonPressed() {
        return equipButtonPressed;
    }

    public void setSpacePressedToFalse() {
        spacePressed = false;
    }

    public void setEquipButtonToFalse() {
        equipButtonPressed = false;
    }

    public boolean isUseButtonPressed() {
        return useButtonPressed;
    }

    public void setUseButtonToFalse() {
        useButtonPressed = false;
    }

    public boolean isLoggerOn() {
        return isLoggerOn;
    }
}
