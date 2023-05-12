package org.example;

import org.example.handlers.GameHandler;
import org.example.handlers.WindowHandler;

public class Main {
    public static void main(String[] args) {
        GameHandler game = new GameHandler();

        game.setupWindow();
        game.startGameThread();
    }
}