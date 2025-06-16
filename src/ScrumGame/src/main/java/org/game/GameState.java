package org.game;

import Vragen.QuestionLoader;
import Monsters.MonsterLoader;
import rooms.Room;
import player.Speler;
import ui.Console;
import ui.GameUIObserver;

public class GameState {
    private Room currentRoom;
    private Speler speler;
    private Console console;
    private QuestionLoader questionLoader;
    private MonsterLoader monsterLoader;
    private GameUIObserver uiObserver;

    // NEW: Static reference to the current player for global access
    private static Speler currentPlayer = null;

    public GameState() {
        currentRoom = new Room("map_0.txt");
        speler = new Speler("Gameboii", currentRoom.getStartX(), currentRoom.getStartY());
        console = new Console();
        questionLoader = new QuestionLoader();
        monsterLoader = new MonsterLoader();

        // NEW: Set the static reference
        currentPlayer = speler;

        // Set up observer pattern
        uiObserver = new GameUIObserver(false); // Set to true for debug mode
        speler.addObserver(uiObserver);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Speler getSpeler() {
        return speler;
    }

    public Console getConsole() {
        return console;
    }

    public QuestionLoader getQuestionLoader() {
        return questionLoader;
    }

    public MonsterLoader getMonsterLoader() {
        return monsterLoader;
    }

    public GameUIObserver getUiObserver() {
        return uiObserver;
    }

    // Method to toggle debug mode
    public void toggleDebugMode() {
        uiObserver.setDebugMode(!uiObserver.getClass().equals(GameUIObserver.class));
    }

    // NEW: Static method to get current player from anywhere
    public static Speler getCurrentPlayer() {
        return currentPlayer;
    }

    // NEW: Static method to set current player
    public static void setCurrentPlayer(Speler player) {
        currentPlayer = player;
    }
}