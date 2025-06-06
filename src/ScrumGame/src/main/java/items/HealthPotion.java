package items;

import player.Speler;

public class HealthPotion implements Item {
    private final int healAmount = 20;
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
        speler.heal(healAmount);
    }
}