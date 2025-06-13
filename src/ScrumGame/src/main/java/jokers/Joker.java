package jokers;

import items.Item;
import player.Speler;

public interface Joker extends Item {
    int getUsesLeft();
    void use(Speler speler);
}
