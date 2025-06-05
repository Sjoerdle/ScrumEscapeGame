package items;

import player.Speler;

public interface Item {
    String getName();
    String getDescription();
    void use(Speler speler);
}