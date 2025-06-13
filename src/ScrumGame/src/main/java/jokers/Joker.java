package jokers;

import items.ItemInfo;
import items.Usable;
import player.Speler;

public interface Joker extends ItemInfo, Usable {
    int getUsesLeft();
    // use() method komt van Usable interface
    // getName() en getDescription() komen van ItemInfo interface
}