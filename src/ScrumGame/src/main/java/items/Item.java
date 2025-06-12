package items;

import player.Speler;

public interface Item extends ItemInfo, Usable {
    String getName();
    String getDescription();
    void use(Speler speler);
}