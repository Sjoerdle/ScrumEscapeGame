package org.game;

import Monsters.Monster;
import items.*;
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
        Scanner scanner = new Scanner(System.in);
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
        String[] itemNames = {"Healing Potion", "Score Multiplier", "Scroll of Monster Evasion"};

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

        String[] itemNames = {"Healing Potion", "Score Multiplier", "Scroll of Monster Evasion"};
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
            // Exit - load next room
            if (!currentRoom.getNextRoom().isEmpty()) {
                Room nextRoom = new Room(currentRoom.getNextRoom());
                gameState.setCurrentRoom(nextRoom);
                speler.setLocation(nextRoom.getStartX(), nextRoom.getStartY());
                System.out.println("Entering next room...");
            } else {
                System.out.println("You've completed all rooms! Congratulations!");
                return;
            }
        } else if (destination == 'M') {
            // Monster encounter
            if (speler.canSkipMonster()) {
                System.out.println("You used your monster skip ability!");
                speler.useMonsterSkip();
                message = "Monster avoided successfully!";
            } else {
                System.out.println("You encountered a monster!");
                Monster monster = gameState.getMonsterLoader().loadAllMonsters().getFirst();
                monster.geefOpdracht();
            }

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
            // Health potion pickup
            Item healthPotion = new HealthPotion();
            speler.addItem(healthPotion);
            message = "Je hebt een " + healthPotion.getName() + " opgepakt!";

            currentRoom.getMap()[newY][newX] = ' ';
            speler.setLocation(newX, newY);
        } else if (destination == '*') {
            // Magic item - could be score multiplier or skip monster
            Item magicItem;
            // Randomly choose between the two magic items
            if (Math.random() < 0.5) {
                magicItem = new ScoreMultiplier();
            } else {
                magicItem = new SkipMonster();
            }

            speler.addItem(magicItem);
            message = "Je hebt een " + magicItem.getName() + " opgepakt!";

            currentRoom.getMap()[newY][newX] = ' ';
            speler.setLocation(newX, newY);
        } else {
            // Normal move
            speler.setLocation(newX, newY);
        }

        gameRenderer.renderRoomFancy(message);
    }
}