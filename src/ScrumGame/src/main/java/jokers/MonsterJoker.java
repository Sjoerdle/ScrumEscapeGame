package jokers;

import player.Speler;

public class MonsterJoker implements Joker {
    int usesLeft = 2;
    final String name = "Monster Joker";

    @Override
    public int getUsesLeft() {
        return usesLeft;
    }

    @Override
    public void use(Speler speler) {
        if (usesLeft > 0) {
            speler.activateMonsterSkip();
            usesLeft--;
            System.out.println("Je gebruikt een Monster Joker! vlucht nu het nog kan!");
            System.out.println("Je kan MonsterJoker nog " + usesLeft + " keer gebruiken");

            if (usesLeft == 0) {
                speler.removeItem(name);
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Allows you to skip 2 monster encounters";
    }
}