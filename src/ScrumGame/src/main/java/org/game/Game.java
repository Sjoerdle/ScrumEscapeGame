package org.game;

import Vragen.QuestionLoader;
import Monsters.MonsterLoader;
import org.game.items.*;
import org.game.rooms.Emojis;
import org.game.rooms.Room;

import java.util.*;

public class Game {
    public static Room currentRoom;
    public static Speler speler;
    public static org.game.Console console;
    public static QuestionLoader questionLoader;
    public static MonsterLoader monsterLoader;
    private GameItemHandler itemHandler;

    public Game() {
        try {
            // Try to load the first map
            currentRoom = new Room("map_0.txt");
            speler = new Speler("Gameboii", currentRoom.getStartX(), currentRoom.getStartY());
            console = new Console();
            questionLoader = new QuestionLoader();
            monsterLoader = new MonsterLoader();
            itemHandler = new GameItemHandler(this);

            // Initialize items
            ItemFactory.registerItem("health_potion", new HealthPotion());
        } catch (Exception e) {
            System.err.println("Error initializing game: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    // ... [rest of the Game class implementation] ...

    public void handleInput(String input) {
        if (input.length() == 0) {
            return;
        }

        char c = input.toLowerCase().charAt(0);

        // Handle item usage (1-9)
        if (c >= '1' && c <= '9') {
            int itemIndex = c - '1';  // Convert '1' to 0, '2' to 1, etc.
            List<String> itemNames = new ArrayList<>(speler.getInventory().keySet());
            if (itemIndex >= 0 && itemIndex < itemNames.size()) {
                String itemName = itemNames.get(itemIndex);
                if (speler.useItem(itemName)) {
                    renderRoomFancy("Used " + itemName);
                }
            }
            return;
        }

        // Handle movement
        int newX = speler.getX();
        int newY = speler.getY();

        switch (c) {
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
            case 'q':
                System.out.println("Game ended by user.");
                System.exit(0);
                break;
        }

        // Check for collisions after movement
        if (canMoveTo(newX, newY)) {
            speler.setX(newX);
            speler.setY(newY);

            // Check for item pickup
            itemHandler.handleItemPickup();

            // Check for other interactions (monsters, doors, etc.)
            // ...
        }
    }

    private boolean canMoveTo(int x, int y) {
        // Check boundaries
        if (x < 0 || x >= currentRoom.getMapWidth() || y < 0 || y >= currentRoom.getMapHeight()) {
            return false;
        }

        // Check for walls and other obstacles
        char cell = currentRoom.getMap()[y][x];
        return cell != '|' && cell != '+' && cell != '-' && cell != ' ';
    }

    public void renderRoomFancy(String message) {
        String[] emojiMap = currentRoom.getTransliteratedMap();
        Console.clearConsole();

        // Show room name and instruction
        System.out.println("Room: " + currentRoom.getName());
        if (!currentRoom.getInstruction().isEmpty()) {
            System.out.println(currentRoom.getInstruction());
        }
        System.out.println("HP: " + speler.getHp() + "/100");
        System.out.println("Keys: " + speler.getKeyCount());
        System.out.println();

        // Render the map with emojis and the player position
        for (int y = 0; y < currentRoom.getMapHeight(); y++) {
            StringBuilder row = new StringBuilder();
            for (int x = 0; x < currentRoom.getMapWidth(); x++) {
                if (x == speler.getX() && y == speler.getY()) {
                    // Show player emoji at current position
                    row.append("\uD83E\uDDD9\u200D♂\uFE0F"); // 🧙‍♂️
                } else {
                    char originalChar = currentRoom.getMap()[y][x];
                    try {
                        row.append(Emojis.getEmojiForCharacter(originalChar));
                    } catch (Exception e) {
                        row.append(originalChar);
                    }
                }
            }
            System.out.println(row.toString());
        }

        // Show inventory and controls
        itemHandler.showInventory();
        System.out.println(message);
        System.out.println("Use 1-9 to use items, WASD to move, Q to quit:");
    }
}
