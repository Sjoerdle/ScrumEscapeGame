package ui;

import org.game.Game;
import org.game.GameState;
import org.game.SaveManager;
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
        displayMenuWithStatusMessage();
        System.out.println("===============================================");
        System.out.println("Welkom bij de Scrum Escape Building game!");
        System.out.println("Kies een optie: ");
        System.out.println("1. Start");

        // Only show load option if save file exists
        if (SaveManager.saveFileExists()) {
            System.out.println("2. Load Saved Game");
            System.out.println("3. Uitleg");
            System.out.println("4. Commands en controls");
            System.out.println("5. Credits");
            System.out.println("6. Afsluiten");
        } else {
            System.out.println("2. Uitleg");
            System.out.println("3. Commands en controls");
            System.out.println("4. Credits");
            System.out.println("5. Afsluiten");
        }
        System.out.println("===============================================");
        System.out.print("Jouw keuze: ");
    }

    private void displayMenuWithStatusMessage() {
        String[] asciiLines = {
                "███████╗███████╗ ██████╗ █████╗ ██████╗ ██╗███████╗███╗   ███╗",
                "██╔════╝██╔════╝██╔════╝██╔══██╗██╔══██╗██║██╔════╝████╗ ████║",
                "█████╗  ███████╗██║     ███████║██████╔╝██║███████╗██╔████╔██║",
                "██╔══╝  ╚════██║██║     ██╔══██║██╔═══╝ ██║╚════██║██║╚██╔╝██║",
                "███████╗███████║╚██████╗██║  ██║██║     ██║███████║██║ ╚═╝ ██║",
                "╚══════╝╚══════╝ ╚═════╝╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝╚═╝     ╚═╝"
        };

        // Check player status
        Speler currentPlayer = GameState.getCurrentPlayer();
        boolean showDeathMessage = currentPlayer != null && currentPlayer.hasDied();
        boolean showWinMessage = currentPlayer != null && currentPlayer.hasWon();

        if (showWinMessage) {
            String[] winMessages = {
                    "🎉 CONGRATULATIONS! 🎉",
                    "",
                    "Je hebt het",
                    "Scrum Escape Building",
                    "voltooid!",
                    "",
                    "Goed gedaan!"
            };

            // Display ASCII art with win message on the right
            displayAsciiWithSideMessage(asciiLines, winMessages, true);
        } else if (showDeathMessage) {
            String[] deathMessages = {
                    "💀 GAME OVER! 💀",
                    "",
                    "Je bent gestorven in het",
                    "Scrum Escape Building!",
                    "",
                    "Probeer het nog eens!"
            };

            // Display ASCII art with death message on the right
            displayAsciiWithSideMessage(asciiLines, deathMessages, false);
        } else {
            // Display normal ASCII art
            for (String line : asciiLines) {
                System.out.println(line);
            }
        }

        System.out.println();
    }

    private void displayAsciiWithSideMessage(String[] asciiLines, String[] messages, boolean isWinMessage) {
        // Display ASCII art with message on the right
        for (int i = 0; i < Math.max(asciiLines.length, messages.length); i++) {
            StringBuilder line = new StringBuilder();

            // Add ASCII art if available
            if (i < asciiLines.length) {
                line.append(asciiLines[i]);
            } else {
                line.append(" ".repeat(asciiLines[0].length()));
            }

            // Add spacing
            line.append(" ".repeat(5));

            // Add message if available
            if (i < messages.length) {
                if (i == 0) { // First line - make it colored/bold
                    if (isWinMessage) {
                        line.append("\u001B[32m\u001B[1m").append(messages[i]).append("\u001B[0m"); // Green for win
                    } else {
                        line.append("\u001B[31m\u001B[1m").append(messages[i]).append("\u001B[0m"); // Red for death
                    }
                } else {
                    line.append(messages[i]);
                }
            }

            System.out.println(line.toString());
        }
    }

    private void handleUserInput() {
        String input = scanner.nextLine();
        boolean hasSaveFile = SaveManager.saveFileExists();

        if (hasSaveFile) {
            // Menu with save file present (1-6)
            switch(input) {
                case "1":
                    startNewGame();
                    break;
                case "2":
                    loadSavedGame();
                    break;
                case "3":
                    showInstructions();
                    break;
                case "4":
                    showCommands();
                    break;
                case "5":
                    showCredits();
                    break;
                case "6":
                    exitGame();
                    break;
                default:
                    System.out.println("Ongeldige optie. Druk op Enter om terug te gaan.");
                    scanner.nextLine();
                    break;
            }
        } else {
            // Menu without save file (1-5)
            switch(input) {
                case "1":
                    startNewGame();
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
    }

    private void startNewGame() {
        System.out.println("\nNieuw spel wordt gestart...");

        // Reset both death and win status for new game
        Speler currentPlayer = GameState.getCurrentPlayer();
        if (currentPlayer != null) {
            currentPlayer.resetDeathStatus();
            currentPlayer.resetWinStatus();
        }

        Game game = new Game();
        game.start();
        System.out.println("Druk op Enter om terug te gaan naar het menu.");
        scanner.nextLine();
    }

    private void loadSavedGame() {
        System.out.println("\nOpgeslagen spel wordt geladen...");

        GameState savedGameState = SaveManager.loadGame();
        if (savedGameState != null) {
            // Create a game with the loaded state
            Game game = new Game(savedGameState); // We'll need to modify Game constructor
            game.start();
        } else {
            System.out.println("Fout bij het laden van het opgeslagen spel.");
        }

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