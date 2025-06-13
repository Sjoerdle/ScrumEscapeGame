package org.game;

import ui.StartScherm;

public class Main {

    public static StartScherm startScherm;

    public static void main(String[] args) {
        startScherm = new StartScherm();
        startScherm.start();
    }
}