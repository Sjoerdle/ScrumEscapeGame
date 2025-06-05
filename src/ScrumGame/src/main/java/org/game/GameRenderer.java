package org.game;

import rooms.Emojis;
import rooms.Room;
import player.Speler;
import ui.Console;

public class GameRenderer {
    private GameState gameState;

    public GameRenderer(GameState gameState) {
        this.gameState = gameState;
    }

    // Render the current room to the console using emojis
    public void renderRoomFancy(String message) {
        Room currentRoom = gameState.getCurrentRoom();
        Speler speler = gameState.getSpeler();
        String[] emojiMap = currentRoom.getTransliteratedMap();

        Console.clearConsole();

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
                    row.append(Emojis.PLAYER); // 🧙‍♂️
                } else {
                    char originalChar = currentRoom.getMap()[y][x];
                    if (originalChar == 'P') {
                        // Don't show the original P, show empty space
                        row.append("  "); // Double space to match emoji width
                    } else {
                        // Get the emoji for this position from the transliterated map
                        // Extract the character at this position and get its emoji representation
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
            return "ERROR";
        }
    }

    // Render the current room to the console
    public void renderRoom(String message) {
        Room currentRoom = gameState.getCurrentRoom();
        Speler speler = gameState.getSpeler();
        char[][] map = currentRoom.getMap();

        Console.clearConsole();

        // Show room name and instruction
        System.out.println("Room: " + currentRoom.getName());
        if (!currentRoom.getInstruction().isEmpty()) {
            System.out.println(currentRoom.getInstruction());
        }
        System.out.println();

        // Render the map with the player position
        for (int y = 0; y < currentRoom.getMapHeight(); y++) {
            for (int x = 0; x < currentRoom.getMapWidth(); x++) {
                if (x == speler.getX() && y == speler.getY()) {
                    System.out.print('P');
                } else if (map[y][x] == 'P') {
                    // Don't show the original P, show empty space
                    System.out.print(' ');
                } else {
                    System.out.print(map[y][x]);
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println(message);
    }
}