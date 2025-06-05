package org.game;

import Monsters.Monster;
import org.game.rooms.Room;
import org.jline.terminal.Terminal;

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

    // Take user input and process movement
    public void handleInput() throws IOException {
        Scanner scanner = new Scanner(System.in);
        // Haal de terminal uit de console klasse
        Terminal terminal = gameState.getConsole().getTerminal();

        while (true) {
            System.out.println("Use WASD to move (W=up, A=left, S=down, D=right), Q to quit:");

            int key = readSingleKey();

//            while (key == -2) key = terminal.reader().read(1);
            char c = Character.toLowerCase((char) key);

            if (c == 'q') {
                System.out.println("Thanks for playing!");
                break;
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
            // Monster encounter - empty implementation for now
            System.out.println("You encountered a monster!");

            Monster monster = gameState.getMonsterLoader().loadAllMonsters().getFirst();
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

        gameRenderer.renderRoomFancy(message);
    }
}