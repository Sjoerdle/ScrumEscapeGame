package org.game;

import player.Speler;
import rooms.Room;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveManager {
    private static final String SAVE_FILE = "save.txt";

    /**
     * Checks if a save file exists
     */
    public static boolean saveFileExists() {
        return Files.exists(Paths.get(SAVE_FILE));
    }

    /**
     * Saves the current game state to save.txt
     */
    public static void saveGame(GameState gameState) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            Speler speler = gameState.getSpeler();
            Room currentRoom = gameState.getCurrentRoom();

            // Save player data
            writer.write("playerName=" + speler.getNaam());
            writer.newLine();
            writer.write("playerX=" + speler.getX());
            writer.newLine();
            writer.write("playerY=" + speler.getY());
            writer.newLine();
            writer.write("playerHP=" + speler.getHp());
            writer.newLine();
            writer.write("deathCount=" + speler.getDeathCount());
            writer.newLine();
            writer.write("keyCount=" + speler.getKeyCount());
            writer.newLine();

            // Save room data
            writer.write("currentRoom=" + currentRoom.getCurrentRoom());
            writer.newLine();

            // Save inventory items (only the keys that exist)
            StringBuilder inventoryItems = new StringBuilder();
            for (String itemName : speler.getInventory().keySet()) {
                if (inventoryItems.length() > 0) {
                    inventoryItems.append(",");
                }
                inventoryItems.append(itemName);
            }
            writer.write("inventoryItems=" + inventoryItems.toString());
            writer.newLine();

            // Save score multiplier data if active
            if (speler.hasActiveScoreMultiplier()) {
                writer.write("scoreMultiplier=" + speler.getCurrentScoreMultiplier());
                writer.newLine();
                // Note: We don't save the end time as it would be invalid when loading
            }

            // Save joker usage flags
            writer.write("hasUsedKeyJoker=" + speler.hasUsedKeyJoker());
            writer.newLine();

            System.out.println("Game saved successfully!");

        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    /**
     * Loads a saved game state from save.txt
     */
    public static GameState loadGame() {
        if (!saveFileExists()) {
            System.err.println("No save file found!");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
            String line;
            String playerName = "Player";
            int playerX = 0, playerY = 0, playerHP = 100;
            int deathCount = 0, keyCount = 0;
            String currentRoomFile = "map_0.txt";
            String inventoryItems = "";
            int scoreMultiplier = 1;
            boolean hasUsedKeyJoker = false;

            // Read save data
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length != 2) continue;

                String key = parts[0];
                String value = parts[1];

                switch (key) {
                    case "playerName":
                        playerName = value;
                        break;
                    case "playerX":
                        playerX = Integer.parseInt(value);
                        break;
                    case "playerY":
                        playerY = Integer.parseInt(value);
                        break;
                    case "playerHP":
                        playerHP = Integer.parseInt(value);
                        break;
                    case "deathCount":
                        deathCount = Integer.parseInt(value);
                        break;
                    case "keyCount":
                        keyCount = Integer.parseInt(value);
                        break;
                    case "currentRoom":
                        currentRoomFile = value;
                        break;
                    case "inventoryItems":
                        inventoryItems = value;
                        break;
                    case "scoreMultiplier":
                        scoreMultiplier = Integer.parseInt(value);
                        break;
                    case "hasUsedKeyJoker":
                        hasUsedKeyJoker = Boolean.parseBoolean(value);
                        break;
                }
            }

            // Create new game state with loaded data
            GameState gameState = new GameState();

            // Create and configure room
            Room room = new Room(currentRoomFile);
            gameState.setCurrentRoom(room);

            // Configure player
            Speler speler = gameState.getSpeler();
            speler.setNaam(playerName);
            speler.setLocation(playerX, playerY);
            speler.setHp(playerHP);
            speler.setKeyCount(keyCount);

            // Set death count via reflection or direct field access
            // Since there's no setter for deathCount, we'll add deaths
            for (int i = 0; i < deathCount; i++) {
                speler.addDeath(1);
            }

            // Restore inventory items
            if (!inventoryItems.isEmpty()) {
                String[] items = inventoryItems.split(",");
                for (String itemName : items) {
                    // Create appropriate item objects based on name
                    switch (itemName.trim()) {
                        case "Healing Potion":
                            speler.addItem(new items.HealthPotion());
                            break;
                        case "Scroll of Monster Evasion":
                            speler.addItem(new items.SkipMonster());
                            break;
                        case "Monster Joker":
                            speler.addItem(new jokers.MonsterJoker());
                            break;
                        case "Sleutel Joker":
                            speler.addItem(new jokers.SleutelJoker());
                            break;
                    }
                }
            }

            // Restore joker usage flags
            if (hasUsedKeyJoker) {
                speler.setKeyJokerUsed();
            }

            System.out.println("Game loaded successfully!");
            return gameState;

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading game: " + e.getMessage());
            return null;
        }
    }

    /**
     * Deletes the save file
     */
    public static void deleteSave() {
        try {
            Files.deleteIfExists(Paths.get(SAVE_FILE));
            System.out.println("Save file deleted.");
        } catch (IOException e) {
            System.err.println("Error deleting save file: " + e.getMessage());
        }
    }
}