package org.example;

import org.example.handlers.GameHandler;

public class Main {
    public static void main(String[] args) {
        GameHandler game = new GameHandler();

        game.setupWindow();
        game.setupGame();
        game.startGameThread();
    }
}