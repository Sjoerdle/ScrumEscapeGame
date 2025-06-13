package org.game;

import rooms.Emojis;
import rooms.Room;
import player.Speler;
import ui.Console;

import java.util.Map;

public class GameRenderer {
    private GameState gameState;

    public GameRenderer(GameState gameState) {
        this.gameState = gameState;
    }

    private String createHpBar(int currentHp, int maxHp) {
        int hearts = (int) Math.ceil((double) currentHp / 20);
        int maxHearts = maxHp / 20;
        StringBuilder bar = new StringBuilder("HP: ");

        for (int i = 0; i < hearts; i++) {
            bar.append(Emojis.HEALTH);
        }

        for (int i = hearts; i < maxHearts; i++) {
            bar.append(Emojis.HEALTH_EMPTY);
        }

        return bar.append(" ").append(currentHp).append("/").append(maxHp).toString();
    }

    // Helper method to get inventory summary - aangepast voor ISP
    private String getInventorySummary(Speler speler) {
        StringBuilder summary = new StringBuilder();

        // Show keys separately
        if (speler.getKeyCount() > 0) {
            summary.append("Keys: 🗝️x").append(speler.getKeyCount());
        }

        // Get other items (excluding keys) - aangepast voor Object inventory
        Map<String, Object> inventory = speler.getInventory();
        if (!inventory.isEmpty() || speler.getKeyCount() > 0) {
            if (speler.getKeyCount() > 0) {
                summary.append(" | ");
            }
            summary.append("Items: ");
            boolean firstItem = true;
            for (Map.Entry<String, Object> entry : inventory.entrySet()) {
                if (!entry.getKey().equals("Key")) {
                    if (!firstItem) {
                        summary.append(", ");
                    }
                    summary.append(entry.getKey());
                    firstItem = false;
                }
            }
        }

        return summary.toString().isEmpty() ? "No items" : summary.toString();
    }

    // Render the current room to the console using emojis
    public void renderRoomFancy(String message) {
        Room currentRoom = gameState.getCurrentRoom();
        Speler speler = gameState.getSpeler();
        String[] emojiMap = currentRoom.getTransliteratedMap();

        Console.clearConsole();

        // Always show HP and inventory at the top
        System.out.println(createHpBar(speler.getHp(), 100));
        System.out.println(getInventorySummary(speler));
        System.out.println("-".repeat(40));

        // Show room name and instruction
        System.out.println("Room: " + currentRoom.getName());
        if (!currentRoom.getInstruction().isEmpty()) {
            System.out.println(currentRoom.getInstruction());
        }
        System.out.println();

        // Render the map with emojis and the player position
        for (int y = 0; y < currentRoom.getMapHeight(); y++) {
            StringBuilder row = new StringBuilder();
            for (int x = 0; x < currentRoom.getMapWidth(); x++) {
                if (x == speler.getX() && y == speler.getY()) {
                    // Show player emoji at current position
                    row.append(Emojis.PLAYER);
                } else {
                    char originalChar = currentRoom.getMap()[y][x];
                    if (originalChar == 'P') {
                        // Don't show the original P, show empty space
                        row.append("  "); // Double space to match emoji width
                    } else {
                        // Get the emoji for this position from the transliterated map
                        String charAtPosition = getEmojiForPosition(x, y, emojiMap[y]);
                        row.append(charAtPosition);
                    }
                }
            }
            System.out.println(row.toString());
        }
        System.out.println();
        System.out.println(message);
    }

    // Helper method to extract emoji at specific position from emoji row string
    private String getEmojiForPosition(int x, int y, String emojiRow) {
        Room currentRoom = gameState.getCurrentRoom();
        char originalChar = currentRoom.getMap()[y][x];

        try {
            return Emojis.getEmojiForCharacter(originalChar);
        } catch (Exception e) {
            return "  ";
        }
    }
}