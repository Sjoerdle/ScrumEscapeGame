package org.game;

import Vragen.QuestionLoader;  // Make sure to use the correct import path


public class Game {
    private GameEngine gameEngine;
    public static QuestionLoader questionLoader;

    public Game() {
        gameEngine = new GameEngine();
        questionLoader = new QuestionLoader();  // Initialize the questionLoader
    }

    public void start() {
        gameEngine.start();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}