package items;

import player.Speler;

public class SkipMonster implements ItemInfo, Usable {
    private final String name = "Scroll of Monster Evasion";
    private final String description = "Allows you to skip the next monster encounter";

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
        // Deze method wordt aangeroepen wanneer het item wordt gebruikt
        // De eigenlijke skip logica wordt afgehandeld in InputHandler
        System.out.println("Scroll of Monster Evasion activated!");
    }
}