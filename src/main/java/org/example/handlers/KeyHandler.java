package org.example.handlers;

import org.example.handlers.types.GameStateType;
import org.example.handlers.types.MenuSelectionType;
import org.example.handlers.types.PauseSelectionType;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * From internet, bookmark
 */

public class KeyHandler implements KeyListener {

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    private GameStateHandler gameState;

    public KeyHandler(GameStateHandler gameState) {
        this.gameState = gameState;
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
            gameState.selectMenuOption();
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
            gameState.selectPauseOption();
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
            gameState.switchPause();
        }
    }

    private void fightingKeys(int code) {
        if (code == KeyEvent.VK_SPACE) {
            System.out.println("SKAP TY KOKOT");
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
}
