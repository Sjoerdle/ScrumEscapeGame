package org.game;
import java.util.Scanner;

class StartScreen {
    private Scanner scanner;
    private boolean running;

    public StartScreen() {
        scanner = new Scanner(System.in);
        running = true;
    }

    public void start() {
        while (running) {
            displayMenu();
            handleUserInput();
        }
        scanner.close();
    }

    /**
     * Toon het menu met opties
     */
    private void displayMenu() {
        clearScreen();
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

    /**
     * Verwerk de gebruikersinvoer
     */
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

    /**
     * Start de game
     */
    private void startGame() {
        System.out.println("\nGame wordt gestart...");
        // Hier zou je de game starten door een nieuwe klasse aan te roepen
        // bijvoorbeeld: Game game = new Game(); game.start();

        // Voor nu gaan we terug naar het menu na een pauze
        System.out.println("Druk op Enter om terug te gaan naar het menu.");
        scanner.nextLine();
    }

    /**
     * Toon de uitleg van de game
     */
    private void showInstructions() {
        clearScreen();
        System.out.println("===============================================");
        System.out.println("UITLEG SCRUM ESCAPE BUILDING");
        System.out.println("===============================================");
        System.out.println("Je zit opgesloten in een Scrum gebouw en moet ontsnappen.");
        System.out.println("Navigeer door de verschillende ruimtes, los puzzels op,");
        System.out.println("verzamel items en gebruik Scrum-principes om te ontsnappen.");
        System.out.println("===============================================");
        System.out.println("Druk op Enter om terug te gaan naar het menu.");
        scanner.nextLine();
    }

    /**
     * Toon de commands en controls
     */
    private void showCommands() {
        clearScreen();
        System.out.println("===============================================");
        System.out.println("COMMANDS EN CONTROLS");
        System.out.println("===============================================");
        System.out.println("- noord/n: Beweeg naar het noorden");
        System.out.println("- oost/o: Beweeg naar het oosten");
        System.out.println("- zuid/z: Beweeg naar het zuiden");
        System.out.println("- west/w: Beweeg naar het westen");
        System.out.println("- pak [item]: Pak een item op");
        System.out.println("- gebruik [item]: Gebruik een item");
        System.out.println("- bekijk [object/item]: Bekijk een object of item");
        System.out.println("- inventaris/i: Bekijk je inventaris");
        System.out.println("- help/h: Toon beschikbare commands");
        System.out.println("===============================================");
        System.out.println("Druk op Enter om terug te gaan naar het menu.");
        scanner.nextLine();
    }

    /**
     * Toon de credits
     */
    private void showCredits() {
        clearScreen();
        System.out.println("===============================================");
        System.out.println("CREDITS");
        System.out.println("===============================================");
        System.out.println("Scrum Escape Building");
        System.out.println("Een terminal avonturenspel");
        System.out.println("\nOntworpen en ontwikkeld door:");
        System.out.println("Stefaan Molenaar /n");
        System.out.println("Benjamin van Teeseling");
        System.out.println("Roderick Schravendeel");
        System.out.println("Sjoerd Lunshof");
        System.out.println("\n© 2025 Alle rechten voorbehouden");
        System.out.println("===============================================");
        System.out.println("Druk op Enter om terug te gaan naar het menu.");
        scanner.nextLine();
    }

    /**
     * Sluit de game af
     */
    private void exitGame() {
        System.out.println("\nBedankt voor het spelen van Scrum Escape Building!");
        System.out.println("Tot de volgende keer!");
        running = false;
    }

    private void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}


public class Main {
    public static void main(String[] args) {
        StartScreen startScreen = new StartScreen();
        startScreen.start();
    }
}