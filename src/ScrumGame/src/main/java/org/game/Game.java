package org.game;

import Vragen.QuestionLoader;

import java.io.IOException;

//Starts main game loop
public class Game {
    public static QuestionLoader questionLoader;
    private GameState gameState;
    private GameRenderer gameRenderer;
    private InputHandler inputHandler;
    private MovementValidator movementValidator;

    public Game() {
        questionLoader = new QuestionLoader();
        gameState = new GameState();
        gameRenderer = new GameRenderer(gameState);
        movementValidator = new MovementValidator(gameState);
        inputHandler = new InputHandler(gameState, gameRenderer, movementValidator);
    }

    public void start() {
        System.out.println("Welcome to the Game!");
        gameRenderer.renderRoomFancy("");

        try {
            inputHandler.handleInput();
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}