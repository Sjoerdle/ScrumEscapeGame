package org.game;

import java.io.IOException;

public class GameEngine {
    private GameState gameState;
    private GameRenderer gameRenderer;
    private InputHandler inputHandler;
    private MovementValidator movementValidator;

    public GameEngine() {
        gameState = new GameState();
        gameRenderer = new GameRenderer(gameState);
        movementValidator = new MovementValidator(gameState);
        inputHandler = new InputHandler(gameState, gameRenderer, movementValidator);
    }

    // Main game loop
    public void start() {
        System.out.println("Welcome to the Game!");
        gameRenderer.renderRoomFancy("");

        try {
            inputHandler.handleInput();
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}