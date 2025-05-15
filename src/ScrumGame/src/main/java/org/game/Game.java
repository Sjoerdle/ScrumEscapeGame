package org.game;

import org.game.rooms.Room;
import java.util.Scanner;

public class Game {
    public Game()
    {
        currentRoom = new Room("map_0.txt");
        speler = new Speler("Gameboii", currentRoom.getStartX(), currentRoom.getStartY());
    }

    public static Room currentRoom;
    public static Speler speler;

    // Render the current room to the console
    public void renderRoom() {
        char[][] map = currentRoom.getMap();

        StartScherm clearScreen = new StartScherm();
        clearScreen.clearScreen();

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
    }

    // Take user input and process movement
    public void handleInput() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Use WASD to move (W=up, A=left, S=down, D=right), Q to quit:");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("q")) {
                System.out.println("Thanks for playing!");
                break;
            }

            int newX = speler.getX();
            int newY = speler.getY();

            // Calculate new position based on input
            switch (input) {
                case "w":
                    newY--;
                    break;
                case "s":
                    newY++;
                    break;
                case "a":
                    newX--;
                    break;
                case "d":
                    newX++;
                    break;
                default:
                    System.out.println("Invalid input! Use WASD keys or Q to quit.");
                    continue;
            }

            // Check if the new position is valid
            if (isValidMove(newX, newY)) {
                // Check what's at the new position
                char destination = currentRoom.getMap()[newY][newX];

                if (destination == 'E') {
                    // Exit - load next room
                    if (!currentRoom.getNextRoom().isEmpty()) {
                        currentRoom = new Room(currentRoom.getNextRoom());
                        speler.setLocation(currentRoom.getStartX(), currentRoom.getStartY());
                        System.out.println("Entering next room...");
                    } else {
                        System.out.println("You've completed all rooms! Congratulations!");
                        break;
                    }
                } else if (destination == 'M') {
                    // Monster encounter - empty implementation for now
                    System.out.println("You encountered a monster! (Not implemented yet)");
                    continue;
                } else {
                    // Normal move
                    speler.setLocation(newX, newY);
                }

                renderRoom();
            } else {
                System.out.println("You can't move there - there's a wall!");
            }
        }

}

    // Check if a move is valid (not into walls)
    private boolean isValidMove(int x, int y) {
        // Check boundaries
        if (x < 0 || x >= currentRoom.getMapWidth() ||
                y < 0 || y >= currentRoom.getMapHeight()) {
            return false;
        }

        char cell = currentRoom.getMap()[y][x];

        // Can't move into walls
        if (cell == '+' || cell == '-' || cell == '|') {
            return false;
        }

        return true;
    }

    // Main game loop
    public void start() {
        System.out.println("Welcome to the Game!");
        renderRoom();
        handleInput();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}