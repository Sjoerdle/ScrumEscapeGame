package org.game;

import Monsters.Monster;
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
        Scanner scanner = new Scanner(System.in);

        // check for walls
        if (isWall(cell)) {
            return false;
        }

        // check for doors
        if (cell == 'D' && !speler.hasKey()) {
            System.out.println("De deur is op slot.. had je maar een sleutel");
            return false;
        }


        // Check for monster
        if (cell == 'M') {
            System.out.println("Je komt een monster tegen!");
            Monster monster = gameState.getMonsterLoader().loadAllMonsters().getFirst();
            monster.geefOpdracht(speler);
            return false;
        }

        return true;
    }

    private boolean isWall(char cell) {
        return cell == '+' || cell == '-' || cell == '|' || cell == '#';
    }

}

