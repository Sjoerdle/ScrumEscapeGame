package org.game;

import rooms.Room;
import player.Speler;

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
        if (cell == '+' || cell == '-' || cell == '|' || cell == '#') {
            return false;
        }

        return true;
    }
}