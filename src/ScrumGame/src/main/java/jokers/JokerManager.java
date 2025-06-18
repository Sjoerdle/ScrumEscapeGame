package jokers;

import player.Speler;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class JokerManager {
    private final Scanner scanner;
    private final Speler speler;

    public JokerManager(Speler speler) {
        this.speler = speler;
        this.scanner = new Scanner(System.in);
    }

    public void chooseAndAddJoker() {
        System.out.println("\nWelke joker wil je gebruiken?");
        System.out.println("1. Monster Joker (2x monster overslaan)");
        System.out.println("2. Sleutel Joker (1x deur openen)");
        System.out.println("3. Toch geen joker");
        System.out.print("Keuze (1-3): ");

        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1":
                speler.addItem(new MonsterJoker());
                System.out.println("Monster Joker toegevoegd aan je inventaris!");
                break;
            case "2":
                speler.addItem(new SleutelJoker());
                System.out.println("Sleutel Joker toegevoegd aan je inventaris!");
                break;
            case "3":
                System.out.println("Geen joker geselecteerd.");
                break;
            default:
                System.out.println("Ongeldige keuze, geen joker geselecteerd.");
        }
        System.out.println("Druk op Enter om door te gaan...");
        scanner.nextLine();
    }

    public boolean askToUseJoker() {
        System.out.println("Wil je een joker gebruiken (is de uitdaging te groot voor je?)");
        System.out.println("[1] ja");
        System.out.println("[2] nee");
        System.out.print("maak je keuze (1-2): ");

        String choice = scanner.nextLine().trim();
        return choice.equals("1");
    }
}