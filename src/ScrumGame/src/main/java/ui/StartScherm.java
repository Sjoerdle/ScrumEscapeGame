package ui;

import org.game.Game;
import org.game.GameState;
import player.Speler;
import audio.GeluidSpeler;

import java.util.Scanner;

public class StartScherm {
    private Scanner scanner;
    private boolean running;
    public static GeluidSpeler speler;

    public StartScherm() {
        scanner = new Scanner(System.in);
        running = true;
        speler = new GeluidSpeler();
    }

    public void start() {
        speler.speel("startscherm.wav", true); // true = herhalen
        while (running) {
            displayMenu();
            handleUserInput();
        }
        scanner.close();
    }

    private void displayMenu() {
        Console.clearConsole();
        displayMenuWithDeathMessage();
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

    private void displayMenuWithDeathMessage() {
        String[] asciiLines = {
                "███████╗███████╗ ██████╗ █████╗ ██████╗ ██╗███████╗███╗   ███╗",
                "██╔════╝██╔════╝██╔════╝██╔══██╗██╔══██╗██║██╔════╝████╗ ████║",
                "█████╗  ███████╗██║     ███████║██████╔╝██║███████╗██╔████╔██║",
                "██╔══╝  ╚════██║██║     ██╔══██║██╔═══╝ ██║╚════██║██║╚██╔╝██║",
                "███████╗███████║╚██████╗██║  ██║██║     ██║███████║██║ ╚═╝ ██║",
                "╚══════╝╚══════╝ ╚═════╝╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝╚═╝     ╚═╝"
        };

        // Check if player has died
        Speler currentPlayer = GameState.getCurrentPlayer();
        boolean showDeathMessage = currentPlayer != null && currentPlayer.hasDied();

        if (showDeathMessage) {
            String[] deathMessages = {
                    "💀 GAME OVER! 💀",
                    "",
                    "Je bent gestorven in het",
                    "Scrum Escape Building!",
                    "",
                    "Probeer het nog eens!"
            };

            // Calculate padding to center the death message
            int maxWidth = 60; // Approximate width of ASCII art
            int deathMessageWidth = 25; // Approximate width of death message
            int padding = maxWidth - deathMessageWidth + 10;

            // Display ASCII art with death message on the right
            for (int i = 0; i < Math.max(asciiLines.length, deathMessages.length); i++) {
                StringBuilder line = new StringBuilder();

                // Add ASCII art if available
                if (i < asciiLines.length) {
                    line.append(asciiLines[i]);
                } else {
                    line.append(" ".repeat(asciiLines[0].length()));
                }

                // Add spacing
                line.append(" ".repeat(5));

                // Add death message if available
                if (i < deathMessages.length) {
                    if (i == 0) { // GAME OVER line - make it red/bold
                        line.append("\u001B[31m\u001B[1m").append(deathMessages[i]).append("\u001B[0m");
                    } else {
                        line.append(deathMessages[i]);
                    }
                }

                System.out.println(line.toString());
            }
        } else {
            // Display normal ASCII art
            for (String line : asciiLines) {
                System.out.println(line);
            }
        }

        System.out.println();
    }

    private void displayASCIIArt() {
        System.out.println("███████╗███████╗ ██████╗ █████╗ ██████╗ ██╗███████╗███╗   ███╗");
        System.out.println("██╔════╝██╔════╝██╔════╝██╔══██╗██╔══██╗██║██╔════╝████╗ ████║");
        System.out.println("█████╗  ███████╗██║     ███████║██████╔╝██║███████╗██╔████╔██║");
        System.out.println("██╔══╝  ╚════██║██║     ██╔══██║██╔═══╝ ██║╚════██║██║╚██╔╝██║");
        System.out.println("███████╗███████║╚██████╗██║  ██║██║     ██║███████║██║ ╚═╝ ██║");
        System.out.println("╚══════╝╚══════╝ ╚═════╝╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝╚═╝     ╚═╝");
        System.out.println();
    }

    private void handleUserInput() {
        String input = scanner.nextLine();

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
                exitGame();
                break;
            default:
                System.out.println("Ongeldige optie. Druk op Enter om terug te gaan.");
                scanner.nextLine();
                break;
        }
    }

    private void startGame() {
        System.out.println("\nGame wordt gestart...");

        // Reset death status for new game
        Speler currentPlayer = GameState.getCurrentPlayer();
        if (currentPlayer != null) {
            currentPlayer.resetDeathStatus();
        }

        Game game = new Game();
        game.start();
        System.out.println("Druk op Enter om terug te gaan naar het menu.");
        scanner.nextLine();
    }

    private void showInstructions() {
        Console.clearConsole();
        System.out.println("===============================================");
        System.out.println("UITLEG SCRUM ESCAPE BUILDING");
        System.out.println("===============================================");
        System.out.println("Je zit opgesloten in een Scrum gebouw en moet ontsnappen.");
        System.out.println("Navigeer door de verschillende ruimtes, vecht monster, ");
        System.out.println("los puzzels op, en gebruik Scrum-principes om te ontsnappen.");
        System.out.println("===============================================");
        System.out.println("Druk op Enter om terug te gaan naar het menu.");
        scanner.nextLine();
    }

    private void showCommands() {
        Console.clearConsole();
        System.out.println("===============================================");
        System.out.println("COMMANDS EN CONTROLS");
        System.out.println("===============================================");
        System.out.println("Voorbeeld commandos");
        System.out.println("W: beweeg naar voren");
        System.out.println("S: beweeg naar achteren");
        System.out.println("A: beweeg naar links");
        System.out.println("D: beweeg naar rechts");
        System.out.println("- help/h: Toon beschikbare commands");
        System.out.println("===============================================");
        System.out.println("Druk op Enter om terug te gaan naar het menu.");
        scanner.nextLine();
    }

    private void showCredits() {
        speler.stop();
        speler.speel("credits.wav", false);
        Console.clearConsole();
        System.out.println("===============================================");
        System.out.println("CREDITS");
        System.out.println("===============================================");
        System.out.println("Scrum Escape Building");
        System.out.println("Een terminal avonturenspel");
        System.out.println("\nOntworpen en ontwikkeld door:");
        System.out.println("Stefaan Molenaar");
        System.out.println("Benjamin van Teeseling");
        System.out.println("Roderick Schravendeel");
        System.out.println("Sjoerd Lunshof");
        System.out.println("\n© 2025 Alle rechten voorbehouden");
        System.out.println("===============================================");
        System.out.println("Druk op Enter om terug te gaan naar het menu.");
        scanner.nextLine();
        speler.speel("startscherm.wav", true);
    }

    private void exitGame() {
        System.out.println("\nBedankt voor het spelen van Scrum Escape Building!");
        System.out.println("Tot de volgende keer!");
        speler.stop();
        running = false;
    }
}