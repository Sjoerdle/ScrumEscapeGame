package org.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartScherm {
    private BufferedReader reader;
    private boolean running;

    public StartScherm() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        running = true;
    }

    public void start() {
        try {
            while (running) {
                displayMenu();
                handleUserInput();
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing reader: " + e.getMessage());
            }
        }
    }

    private void displayMenu() {
        Console.clearConsole();
        System.out.println("===============================================");
        System.out.println("Welkom bij de Scrum Escape Building game!");
        System.out.println("Kies een optie: ");
        System.out.println("1. Start");
        System.out.println("2. Uitleg");
        System.out.println("3. Commands en controls");
        System.out.println("4. Credits");
        System.out.println("5. Afsluiten");
        System.out.println("===============================================");
        System.out.print("Jouw keuze: ");
    }

    private void handleUserInput() {
        try {
            String input = reader.readLine();
            if (input == null) {
                running = false;
                return;
            }

            input = input.trim();

            switch(input) {
                case "1":
                    startGame();
                    break;
                case "2":
                    showInstructions();
                    break;
                case "3":
                    showCommands();
                    break;
                case "4":
                    showCredits();
                    break;
                case "5":
                    System.out.println("Bedankt voor het spelen! Tot ziens!");
                    running = false;
                    return;
                default:
                    System.out.println("Ongeldige keuze. Probeer opnieuw.");
                    waitForEnter();
            }
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
            running = false;
        }
    }

    private void startGame() {
        Game game = new Game();
        game.renderRoomFancy("Welkom in de game! Gebruik WASD om te bewegen en Q om af te sluiten.");

        try (BufferedReader gameReader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String input = gameReader.readLine();
                if (input == null || input.equalsIgnoreCase("q")) {
                    break;
                }
                game.handleInput(input);
            }
        } catch (IOException e) {
            System.err.println("Error in game: " + e.getMessage());
        }
    }

    private void showInstructions() {
        Console.clearConsole();
        System.out.println("=== Speluitleg ===");
        System.out.println("Je bent een programmeur die vastzit in een gebouw vol met monsters.");
        System.out.println("Verzamel sleutels om deuren te openen en ontsnap uit het gebouw!");
        System.out.println("\nItems:");
        System.out.println("- H: Health Potion (herstelt 20 HP)");
        System.out.println("- K: Sleutel (opent deuren)");
        System.out.println("\nDruk op Enter om terug te keren naar het hoofdmenu...");
        waitForEnter();
    }

    private void showCommands() {
        Console.clearConsole();
        System.out.println("=== Commands en Controls ===");
        System.out.println("WASD: Beweeg je karakter");
        System.out.println("1-9: Gebruik een item uit je inventaris");
        System.out.println("Q: Stop het spel");
        System.out.println("\nDruk op Enter om terug te keren naar het hoofdmenu...");
        waitForEnter();
    }

    private void showCredits() {
        Console.clearConsole();
        System.out.println("=== Credits ===");
        System.out.println("Ontwikkeld door: [Jouw Naam]");
        System.out.println("Special thanks to: [Teamleden]");
        System.out.println("\nDruk op Enter om terug te keren naar het hoofdmenu...");
        waitForEnter();
    }

    private void waitForEnter() {
        System.out.println("Druk op Enter om door te gaan...");
        try {
            reader.readLine();
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }
}
