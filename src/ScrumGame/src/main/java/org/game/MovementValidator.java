package org.game;

import rooms.Room;
import player.Speler;
import java.util.Scanner;

public class MovementValidator {
    private GameState gameState;

    public MovementValidator(GameState gameState) {
        this.gameState = gameState;
    }

    // Check if a move is valid (not into walls)
    public boolean isValidMove(int x, int y) {
        Room currentRoom = gameState.getCurrentRoom();
        Speler speler = gameState.getSpeler();

        // Check boundaries
        if (x < 0 || x >= currentRoom.getMapWidth() ||
                y < 0 || y >= currentRoom.getMapHeight()) {
            return false;
        }

        char cell = currentRoom.getMap()[y][x];

        // check for walls
        if (isWall(cell)) {
            return false;
        }

        // check for doors
        if (cell == 'D' && !speler.hasKey()) {
            System.out.println("De deur is op slot.. had je maar een sleutel");
            return false;
        }

        // Monster encounters worden nu afgehandeld in InputHandler.processMovement()
        // Hier alleen checken of de move geldig is, niet de encounter zelf afhandelen

        return true;
    }

    private boolean isWall(char cell) {
        return cell == '+' || cell == '-' || cell == '|' || cell == '#';
    }
}