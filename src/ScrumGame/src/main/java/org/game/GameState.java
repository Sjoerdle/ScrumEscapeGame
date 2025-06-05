package org.game;

import Vragen.QuestionLoader;
import Monsters.MonsterLoader;
import org.game.rooms.Room;

public class GameState {
    private Room currentRoom;
    private Speler speler;
    private org.game.Console console;
    private QuestionLoader questionLoader;
    private MonsterLoader monsterLoader;

    public GameState() {
        currentRoom = new Room("map_0.txt");
        speler = new Speler("Gameboii", currentRoom.getStartX(), currentRoom.getStartY());
        console = new Console();
        questionLoader = new QuestionLoader();
        monsterLoader = new MonsterLoader();
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

    public org.game.Console getConsole() {
        return console;
    }

    public QuestionLoader getQuestionLoader() {
        return questionLoader;
    }

    public MonsterLoader getMonsterLoader() {
        return monsterLoader;
    }
}