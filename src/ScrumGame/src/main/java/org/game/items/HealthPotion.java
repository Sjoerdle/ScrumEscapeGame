package org.game.items;

import org.game.Speler;

/**
 * A health potion item that restores the player's health when used.
 * Implements the Observer pattern by notifying observers through the Speler class.
 */
public class HealthPotion implements Item {
    private static final int HEAL_AMOUNT = 50;
    private static final String NAME = "Healing Potion";
    private static final String DESCRIPTION = String.format("Restores %d health points", HEAL_AMOUNT);

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public void use(Speler speler) {
        if (speler != null) {
            speler.heal();
            // The Speler class will handle notifying observers about the health change
        }
    }
}