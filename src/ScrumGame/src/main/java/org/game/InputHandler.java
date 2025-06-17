package org.game;

import Monsters.Monster;
import items.*;
import jokers.*;
import rooms.Room;
import org.jline.terminal.Terminal;
import player.Speler;

import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

public class InputHandler {
    private GameState gameState;
    private GameRenderer gameRenderer;
    private MovementValidator movementValidator;

    public InputHandler(GameState gameState, GameRenderer gameRenderer, MovementValidator movementValidator) {
        this.gameState = gameState;
        this.gameRenderer = gameRenderer;
        this.movementValidator = movementValidator;
    }

    /**
     * Reads a single key from the console and returns its ASCII code.
     *
     * @return The ASCII code of the pressed key, or -1 if an error occurs
     */
    public static int readSingleKey() {
        try {
            Reader reader = System.console().reader();
            int key = reader.read();
            return key;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } catch (NullPointerException e) {
            // This happens if System.console() returns null (e.g., when running in some IDEs)
            System.err.println("Console is not available in this environment");
            e.printStackTrace();
            return -1;
        }
    }

    // Take user input and process movement and item usage
    public void handleInput() throws IOException {

        // Haal de terminal uit de console klasse
        Terminal terminal = gameState.getConsole().getTerminal();

        while (true) {
            System.out.println("Use WASD to move (W=up, A=left, S=down, D=right)");
            System.out.println("Use 1/2/3 to use items, I for inventory, Q to quit:");

            int key = readSingleKey();
            char c = Character.toLowerCase((char) key);

            if (c == 'q') {
                System.out.println("Thanks for playing!");
                break;
            }

            // Handle item usage
            if (c >= '1' && c <= '9') {
                handleItemUsage(c - '0');
                continue;
            }

            // Show inventory
            if (c == 'i') {
                showInventory();
                continue;
            }

            Speler speler = gameState.getSpeler();
            int newX = speler.getX();
            int newY = speler.getY();

            // Bereken de nieuwe positie op basis van input
            switch (Character.toLowerCase(c)) {
                case 'w':
                    newY--;
                    break;
                case 's':
                    newY++;
                    break;
                case 'a':
                    newX--;
                    break;
                case 'd':
                    newX++;
                    break;
                default:
                    // Negeer andere toetsen
                    continue;
            }

            // Check if the new position is valid
            if (movementValidator.isValidMove(newX, newY)) {
                processMovement(newX, newY);
            } else {
                System.out.println("You can't move there - there's a wall!");
            }
        }
    }

    private void handleItemUsage(int itemSlot) {
        Speler speler = gameState.getSpeler();
        String[] itemNames = {"Healing Potion", "Scroll of Monster Evasion"};

        if (itemSlot >= 1 && itemSlot <= itemNames.length) {
            String itemName = itemNames[itemSlot - 1];
            if (speler.hasItem(itemName)) {
                boolean used = speler.useItem(itemName);
                if (used) {
                    gameRenderer.renderRoomFancy("Used " + itemName + "!");
                } else {
                    gameRenderer.renderRoomFancy("Failed to use " + itemName + "!");
                }
            } else {
                gameRenderer.renderRoomFancy("You don't have a " + itemName + "!");
            }
        }
    }

    private void showInventory() {
        Speler speler = gameState.getSpeler();
        System.out.println("\n=== INVENTORY ===");
        System.out.println("Health: " + speler.getHp() + "/100");
        System.out.println("Keys: " + speler.getKeyCount());
        System.out.println("\nItems:");

        String[] itemNames = {"Healing Potion", "Scroll of Monster Evasion"};
        for (int i = 0; i < itemNames.length; i++) {
            if (speler.hasItem(itemNames[i])) {
                System.out.println((i + 1) + ". " + itemNames[i]);
            } else {
                System.out.println((i + 1) + ". [Empty]");
            }
        }

        System.out.println("\nPress any key to continue...");
        try {
            readSingleKey();
        } catch (Exception e) {
            // Ignore
        }
        gameRenderer.renderRoomFancy("");
    }

    private void processMovement(int newX, int newY) {
        Room currentRoom = gameState.getCurrentRoom();
        Speler speler = gameState.getSpeler();

        // Check what's at the new position
        char destination = currentRoom.getMap()[newY][newX];
        String message = "";

        if (destination == 'E') {
            // Exit - load next room and save game
            if (!currentRoom.getNextRoom().isEmpty()) {
                Room nextRoom = new Room(currentRoom.getNextRoom());
                gameState.setCurrentRoom(nextRoom);
                speler.setLocation(nextRoom.getStartX(), nextRoom.getStartY());

                // Auto-save when completing a level
                SaveManager.saveGame(gameState);

                System.out.println("Entering next room... Game saved!");
            } else {
                System.out.println("You've completed all rooms! Congratulations!");

                // Mark player as won and delete save file
                speler.setHasWon(true);
                SaveManager.deleteSave();

                return;
            }

        } else if (destination == 'M') {
            // Monster encounter - check for Monster Joker first
            if (speler.hasJoker("Monster Joker")) {
                MonsterJoker joker = (MonsterJoker) speler.getJoker("Monster Joker");
                if (joker != null && joker.getUsesLeft() > 0) {
                    System.out.println("You encountered a monster!");
                    System.out.print("Do you want to use a Monster Joker to skip it? (y/n): ");
                    char choice = Character.toLowerCase((char) readSingleKey());
                    System.out.println();

                    if (choice == 'y') {
                        joker.use(speler); // Dit vermindert usesLeft
                        message = "Used Monster Joker to skip the monster!";
                        currentRoom.getMap()[newY][newX] = ' ';  // Remove the monster
                        speler.setLocation(newX, newY);
                        gameRenderer.renderRoomFancy(message);
                        return;
                    }
                }
            }

            // Check for Scroll of Monster Evasion
            if (speler.hasItem("Scroll of Monster Evasion")) {
                System.out.println("You encountered a monster!");
                System.out.print("Do you want to use a Scroll of Monster Evasion? (y/n): ");
                char choice = Character.toLowerCase((char) readSingleKey());
                System.out.println();

                if (choice == 'y') {
                    // Use the scroll
                    speler.useItem("Scroll of Monster Evasion");
                    message = "Used Scroll of Monster Evasion to skip the monster!";
                    currentRoom.getMap()[newY][newX] = ' ';  // Remove the monster
                    speler.setLocation(newX, newY);
                    gameRenderer.renderRoomFancy(message);
                    return;
                }
            }

            // Fight the monster
            System.out.println("You encountered a monster and must fight!");
            Monster monster = gameState.getMonsterLoader().loadAllMonsters().getFirst();
            monster.geefOpdracht(speler);

            currentRoom.getMap()[newY][newX] = ' ';
            speler.setLocation(newX, newY);
        } else if (destination == 'K') {
            //Key, to open a door, pick it up
            speler.addKey();
            message = "Je hebt een sleutel opgepakt!";

            //empty tile
            currentRoom.getMap()[newY][newX] = ' ';
            speler.setLocation(newX, newY);
        } else if (destination == 'D') {
            //Door, needs a key to open
            message = "Je hebt een deur geopend!";
            currentRoom.getMap()[newY][newX] = ' ';
            speler.setLocation(newX, newY);
        } else if (destination == 'H') {
            // Health potion pickup - aangepast voor ISP
            Object healthPotion = new HealthPotion();
            speler.addItem(healthPotion);
            message = "Je hebt een " + ((ItemInfo)healthPotion).getName() + " opgepakt!";

            //empty tile
            currentRoom.getMap()[newY][newX] = ' ';
            speler.setLocation(newX, newY);
        } else if (destination == '*') {
            // Magic item pickup - aangepast voor ISP
            Object magicItem = new SkipMonster();
            speler.addItem(magicItem);
            message = "Je hebt een " + ((ItemInfo)magicItem).getName() + " opgepakt!";

            currentRoom.getMap()[newY][newX] = ' ';
            speler.setLocation(newX, newY);
        } else {
            // Normal move
            speler.setLocation(newX, newY);
        }

        gameRenderer.renderRoomFancy(message);
    }

    private int getIntInput(int min, int max) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("\nVoer je keuze in (" + min + "-" + max + "): ");
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("Ongeldige keuze. Voer een getal in tussen " + min + " en " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Voer alstublieft een geldig getal in.");
            }
        }
    }
}