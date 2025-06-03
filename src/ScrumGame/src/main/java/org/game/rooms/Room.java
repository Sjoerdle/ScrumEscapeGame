package org.game.rooms;

import org.game.Resources;
import org.game.items.ItemSymbol;
import org.game.rooms.Emojis;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private String name = "Default Room";
    private String instruction = "";
    private String currentRoom = "";
    private String nextRoom = "";
    private ArrayList<String> monsters = new ArrayList<>();
    private char[][] map = new char[10][20];
    private int mapWidth = 20;
    private int mapHeight = 10;
    private int startX = 1;
    private int startY = 1;
    private Map<Point, String> items = new HashMap<>();
    
    private void initializeDefaultMap() {
        // Create a simple default map
        String[] defaultMap = {
            "+------------------+",
            "|P     H          |",
            "|                 |",
            "|  H          H   |",
            "|                 |",
            "|     E            |",
            "+------------------+"
        };
        
        this.mapHeight = defaultMap.length;
        this.mapWidth = defaultMap[0].length();
        this.map = new char[mapHeight][mapWidth];
        
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                char c = defaultMap[i].charAt(j);
                this.map[i][j] = c;
                
                if (c == 'P') {
                    this.startX = j;
                    this.startY = i;
                    this.map[i][j] = ' ';
                } else if (c == 'H') {
                    items.put(new Point(j, i), "health_potion");
                    this.map[i][j] = ' ';
                }
            }
        }
    }

    public Room(String mapPath) {
        currentRoom = mapPath;
        LoadMap(mapPath);
    }

    public String getItemAt(int x, int y) {
        return items.remove(new Point(x, y));
    }

    public String[] getTransliteratedMap() {
        // Create result array
        String[] transliteratedMap = new String[mapHeight];

        // Convert each row
        for (int row = 0; row < mapHeight; row++) {
            StringBuilder rowBuilder = new StringBuilder();
            for (int col = 0; col < mapWidth; col++) {
                char currentChar = map[row][col];
                // Get the emoji mapping, or use the original character if no mapping exists
                String emoji = null;
                try {
                    emoji = Emojis.getEmojiForCharacter(currentChar);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                rowBuilder.append(emoji);
            }
            transliteratedMap[row] = rowBuilder.toString();
        }

        return transliteratedMap;
    }

    private void LoadMap(String mapPath) {
        try {
            String contents = Resources.getFileFromResouceAsString("maps/" + mapPath);
            String[] lines = contents.split("\r?\n"); // Handle both Windows and Unix line endings

            // Parse metadata
            for (String line : lines) {
                if (line.startsWith("name=")) {
                    this.name = line.substring(5).trim();
                } else if (line.startsWith("instruction=")) {
                    this.instruction = line.substring(12).trim();
                } else if (line.startsWith("nextRoom=")) {
                    this.nextRoom = line.substring(9).trim();
                } else if (line.startsWith("monsters=")) {
                    String monstersStr = line.substring(9).trim();
                    if (!monstersStr.isEmpty()) {
                        this.monsters = new ArrayList<>(Arrays.asList(monstersStr.split(";")));
                    } else {
                        this.monsters = new ArrayList<>();
                    }
                }
            }


            // Find where the map starts (line starting with '+')
            int mapStartIndex = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].trim().startsWith("+")) {
                    mapStartIndex = i;
                    break;
                }
            }

            // Count map dimensions
            int rows = 0;
            int cols = 0;
            for (int i = mapStartIndex; i < lines.length; i++) {
                String line = lines[i].trim();
                if (line.startsWith("+") || line.startsWith("|") || line.startsWith("-")) {
                    rows++;
                    if (cols < line.length()) {
                        cols = line.length();
                    }
                }
            }

            // Initialize the map array with spaces
            this.map = new char[rows][cols];
            for (int i = 0; i < rows; i++) {
                Arrays.fill(this.map[i], ' ');
            }
            this.mapHeight = rows;
            this.mapWidth = cols;

            // Populate the map array and handle items
            int mapRowIndex = 0;
            for (int i = mapStartIndex; i < lines.length && mapRowIndex < rows; i++) {
                String line = lines[i].trim();
                if (line.startsWith("+") || line.startsWith("|") || line.startsWith("-")) {
                    for (int j = 0; j < line.length() && j < cols; j++) {
                        char currentChar = line.charAt(j);
                        
                        // Skip any control characters
                        if (currentChar < ' ' && currentChar != '\t') {
                            continue;
                        }
                        
                        this.map[mapRowIndex][j] = currentChar;

                        // Handle special characters
                        if (currentChar == 'P') {
                            this.startX = j;
                            this.startY = mapRowIndex;
                            this.map[mapRowIndex][j] = ' '; // Replace P with empty space
                        } else if (ItemSymbol.fromChar(currentChar) != null) {
                            // Handle items
                            ItemSymbol itemSymbol = ItemSymbol.fromChar(currentChar);
                            if (itemSymbol != null) {
                                items.put(new Point(j, mapRowIndex), itemSymbol.getItemId());
                                this.map[mapRowIndex][j] = ' '; // Remove item from the map
                            }
                        }
                    }
                    mapRowIndex++;
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading map: " + e.getMessage());
            e.printStackTrace();
            // Initialize with a default map if loading fails
            initializeDefaultMap();
        }
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    public String getNextRoom() {
        return nextRoom;
    }

    public void setNextRoom(String nextRoom) {
        this.nextRoom = nextRoom;
    }

    public ArrayList<String> getMonsters() {
        return monsters;
    }

    public void setMonsters(ArrayList<String> monsters) {
        this.monsters = monsters;
    }

    public char[][] getMap() {
        return map;
    }

    public void setMap(char[][] map) {
        this.map = map;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }
}
