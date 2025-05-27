package org.game;

import Vragen.IQuestion;
import Vragen.QuestionLoader;
import Monsters.MonsterLoader;
import Monsters.Monster;
import org.game.rooms.Room;
import org.jline.terminal.Terminal;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.Scanner;

public class Game {
    public Game() {
        currentRoom = new Room("map_0.txt");
        speler = new Speler("Gameboii", currentRoom.getStartX(), currentRoom.getStartY());
        console = new Console();
        questionLoader = new QuestionLoader();
        monsterLoader = new MonsterLoader();
    }

    public static Room currentRoom;
    public static Speler speler;
    public static org.game.Console console;
    public static QuestionLoader questionLoader;
    public static MonsterLoader monsterLoader;

    // Render the current room to the console
    public void renderRoom(String message) {
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


    // Take user input and process movement
    public void handleInput() throws IOException {
        Scanner scanner = new Scanner(System.in);
        // Haal de terminal uit de console klasse
        Terminal terminal = console.getTerminal();

        while (true) {
            System.out.println("Use WASD to move (W=up, A=left, S=down, D=right), Q to quit:");

            int key = readSingleKey();

//            while (key == -2) key = terminal.reader().read(1);
            char c = Character.toLowerCase((char) key);

            if (c == 'q') {
                System.out.println("Thanks for playing!");
                break;
            }

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
            if (isValidMove(newX, newY)) {
                // Check what's at the new position
                char destination = currentRoom.getMap()[newY][newX];
                String message = "";

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
                    System.out.println("You encountered a monster!");

                    Monster monster = Game.monsterLoader.loadAllMonsters().getFirst();
                    monster.geefOpdracht();

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
                } else {
                    // Normal move
                    speler.setLocation(newX, newY);
                }

                renderRoom(message);
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

        if (cell == 'D')
        {
            if (speler.hasKey())
            {
                speler.removeKey();
                return true;
            } else {
                System.out.println("This door is locked, you need a key!");
                return false;
            }
        }

        // Can't move into walls
        if (cell == '+' || cell == '-' || cell == '|') {
            return false;
        }

        return true;
    }

    // Main game loop
    public void start() {
        System.out.println("Welcome to the Game!");
        renderRoom("");

        try {
            handleInput();
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}