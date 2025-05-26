package org.game.items;

import org.game.Speler;

public interface Item {
    String getName();
    String getDescription();
    void use(Speler speler);
}