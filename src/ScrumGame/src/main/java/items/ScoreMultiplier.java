package items;

import player.Speler;

public class ScoreMultiplier implements Item {
    private final String name = "Score Multiplier";
    private final String description = "Doubles points earned for 30 seconds";
    private final int duration = 30; // seconds
    private final int multiplier = 2;

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
        // This would need to be implemented in the Game class
        // to track the duration and apply the multiplier
    }
}