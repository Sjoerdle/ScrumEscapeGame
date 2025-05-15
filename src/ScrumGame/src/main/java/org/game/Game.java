package org.game;

import org.game.rooms.Room;

public class Game {
    public Game()
    {
        currentRoom = new Room("map_0.txt");
        speler = new Speler("Gameboii");
    }

    public static Room currentRoom;
    public static Speler speler;

    


}
