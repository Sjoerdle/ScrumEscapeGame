package org.game;

import Vragen.QuestionLoader;
import jokers.JokerManager;
import player.Speler;

import java.io.IOException;
import java.util.Scanner;

//Starts main game loop
public class Game {
    public static QuestionLoader questionLoader;
    private GameState gameState;
    private GameRenderer gameRenderer;
    private InputHandler inputHandler;
    private MovementValidator movementValidator;
    private final JokerManager jokerManager;

    // Default constructor for new games
    public Game() {
        questionLoader = new QuestionLoader();
        gameState = new GameState();
        gameRenderer = new GameRenderer(gameState);
        movementValidator = new MovementValidator(gameState);
        inputHandler = new InputHandler(gameState, gameRenderer, movementValidator);
        jokerManager = new JokerManager(gameState.getSpeler());
    }

    // Constructor for loading saved games
    public Game(GameState loadedGameState) {
        questionLoader = new QuestionLoader();
        gameState = loadedGameState;
        gameRenderer = new GameRenderer(gameState);
        movementValidator = new MovementValidator(gameState);
        inputHandler = new InputHandler(gameState, gameRenderer, movementValidator);
        jokerManager = new JokerManager(gameState.getSpeler());
    }

    public void start() {
        System.out.println("Welcome to the Game!");

        //asks for joker at start (only for new games, not loaded games)
        if (gameState.getSpeler().getHp() == 100 && gameState.getSpeler().getDeathCount() == 0) {
            if (jokerManager.askToUseJoker()) {
                jokerManager.chooseAndAddJoker();
            }
        }

        //shows the room
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