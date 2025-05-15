package org.game.rooms;

import org.game.Resources;

import java.util.ArrayList;
import java.util.Arrays;

public class Room {
    private String name;
    private String instruction;
    private String currentRoom;
    private String nextRoom;
    private ArrayList<String> monsters;
    private char[][] map;
    private int mapWidth;
    private int mapHeight;
    private int startX;
    private int startY;

    public Room(String mapPath)
    {
        currentRoom = mapPath;
        LoadMap(mapPath);
    }

    private void LoadMap(String mapPath)
    {
        String contents = Resources.getFileFromResouceAsString("maps/" + mapPath);
        String[] lines = contents.split("\n");

        // Parse metadata
        for (String line : lines) {
            if (line.startsWith("name=")) {
                this.name = line.substring(5);
            } else if (line.startsWith("instruction=")) {
                this.instruction = line.substring(12);
            } else if (line.startsWith("nextRoom=")) {
                this.nextRoom = line.substring(9);
            } else if (line.startsWith("monsters=")) {
                String monstersStr = line.substring(9);
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
            if (lines[i].startsWith("+")) {
                mapStartIndex = i;
                break;
            }
        }

        // Count map dimensions
        int rows = 0;
        int cols = 0;
        for (int i = mapStartIndex; i < lines.length; i++) {
            if (lines[i].startsWith("+") || lines[i].startsWith("|")) {
                rows++;
                if (cols == 0 && lines[i].length() > 0) {
                    cols = lines[i].length();
                }
            }
        }

        // Initialize the map array
        this.map = new char[rows][cols];
        this.mapHeight = rows;
        this.mapWidth = cols;

        // Populate the map array
        int mapRowIndex = 0;
        for (int i = mapStartIndex; i < lines.length; i++) {
            if (lines[i].startsWith("+") || lines[i].startsWith("|")) {
                for (int j = 0; j < lines[i].length() && j < cols; j++) {
                    this.map[mapRowIndex][j] = lines[i].charAt(j);

                    // Find the start position 'P'
                    if (lines[i].charAt(j) == 'P') {
                        this.startX = j;
                        this.startY = mapRowIndex;
                    }
                }
                mapRowIndex++;
            }
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