package org.game;

import Monsters.Monster;
import jokers.*;
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
        if (cell == '+' || cell == '-' || cell == '|' || cell == '#') {
            return false;
        }

        // check for doors
        if (cell == 'D') {
            //check for normal keys
            if (speler.hasKey()) {
                speler.removeKey();
                System.out.println("\nJe gebruikt een sleutel om de deur te openen!");
                return true;

                //check if JokerKey hasn't been used
            } else if (speler.hasJoker("Sleutel Joker")) {
                Joker sleutelJoker = speler.getJoker("Sleutel Joker");
                System.out.println("\nWil je de Sleutel Joker gebruiken? (j/n)");
                String answer = scanner.nextLine().trim().toLowerCase();
                if (answer.equals("j")) {
                    sleutelJoker.use(speler);
                    speler.removeItem("Sleutel Joker");  // Remove after use
                    return true;
                }
            }
            System.out.println("De deur is op slot.. had je maar een sleutel");
            return false;
        }


        // Check for monster
        if (cell == 'M') {
            if (speler.hasJoker("Monster Joker")) {
                Joker monsterJoker = speler.getJoker("Monster Joker");
                if (monsterJoker != null && monsterJoker.getUsesLeft() > 0) {
                    System.out.println("\nWil je de Monster Joker gebruiken? (j/n)");
                    System.out.println("Je hebt nog " + monsterJoker.getUsesLeft() + " Monster Jokers over.");

                    String answer = scanner.nextLine().trim().toLowerCase();
                    if (answer.equals("j") || answer.equals("ja")) {
                        monsterJoker.use(speler);
                        currentRoom.getMap()[y][x] = ' '; // Remove the monster
                        return true;
                    }
                }
            }

            // Normal monster encounter
            System.out.println("Je komt een monster tegen!");
            Monster monster = gameState.getMonsterLoader().loadAllMonsters().getFirst();
            monster.geefOpdracht(speler);
            return false;
        }

        return true;
    }
}
