package org.game.items;

import org.game.Speler;

public class HealthPotion implements Item {
    private final int healAmount = 50;
    private final String name = "Healing Potion";
    private final String description = "Restores " + healAmount + " health points";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void use(Speler speler) {
        speler.heal();
    }
}