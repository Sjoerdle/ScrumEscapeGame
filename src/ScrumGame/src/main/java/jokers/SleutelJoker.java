package jokers;

import player.Speler;

public class SleutelJoker implements Joker {
    final String name = "Sleutel Joker";

    @Override
    public int getUsesLeft() {
        return 1;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Opens any locked door";
    }

    @Override
    public void use(Speler speler) {
        System.out.println("Sleutel Joker gebruikt om de deur te openen!");
    }
}

