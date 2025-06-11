package org.game;

import Vragen.QuestionLoader;
import jokers.MonsterJoker;
import jokers.SleutelJoker;
import org.w3c.dom.ls.LSOutput;
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

    public Game() {
        questionLoader = new QuestionLoader();
        gameState = new GameState();
        gameRenderer = new GameRenderer(gameState);
        movementValidator = new MovementValidator(gameState);
        inputHandler = new InputHandler(gameState, gameRenderer, movementValidator);
    }

    public void start() {
        System.out.println("Welcome to the Game!");


        //asks for joker at start
        askForJokersAtStart();

        //shows the room
        gameRenderer.renderRoomFancy("");

        try {
            inputHandler.handleInput();
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void askForJokersAtStart() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("wil je een joker gebruiken (is de uitdaging te groot voor je?)");
        System.out.println("[1] ja");
        System.out.println("[2] nee");
        System.out.println("maak je keuze (1-2): ");

        String choice = scanner.nextLine().trim();
        if (choice.equals("1")) {
            chooseJoker();
        }
    }

    public void chooseJoker() {
        Scanner scanner = new Scanner(System.in);
        Speler speler = gameState.getSpeler();

        System.out.println("\nWelke joker wil je gebruiken?");
        System.out.println("1. Monster Joker (2x monster overslaan)");
        System.out.println("2. Sleutel Joker (1x deur openen)");
        System.out.println("3. Toch geen joker");
        System.out.print("Keuze (1-3): ");

        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1":
                speler.addItem(new MonsterJoker());
                System.out.println("Monster Joker toegevoegd aan je inventaris!");
                break;
            case "2":
                speler.addItem(new SleutelJoker());
                System.out.println("Sleutel Joker toegevoegd aan je inventaris!");
                break;
            case "3":
                System.out.println("Geen joker geselecteerd.");
                break;
            default:
                System.out.println("Ongeldige keuze, geen joker geselecteerd.");
        }
        System.out.println("Druk op Enter om door te gaan...");
        scanner.nextLine();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
