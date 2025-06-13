package hints;

import java.util.Scanner;

public class HintManager {
    private HintFactory hintFactory;

    public HintManager() {
        this.hintFactory = new HintFactory();
    }

    public void offerHint(Scanner scanner, String helpHint, String funnyHint) {
        System.out.print("Wil je een hint? (j/n): ");
        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("j") || choice.equals("ja") || choice.equals("y") || choice.equals("yes")) {
            HintProvider hintProvider = hintFactory.createHintProvider(helpHint, funnyHint);
            System.out.println("\n" + hintProvider.getHintType() + " Hint: " + hintProvider.getHint());
        } else {
            System.out.println("Geen hint gekozen. Probeer het nog eens!");
        }
    }
}