package Vragen;

import hints.HintManager;
import java.util.List;
import java.util.Scanner;
import player.Speler;

public class MultipleChoice implements IQuestion {
    private String question;
    private List<String> options;
    private int correctAnswer;
    private String helpHint;
    private String funnyHint;
    private HintManager hintManager;

    public boolean alGehad = false;
    public boolean goedBeantwoord = false;

    public MultipleChoice(String question, List<String> options, int correctAnswer, String helpHint, String funnyHint) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.helpHint = helpHint;
        this.funnyHint = funnyHint;
        this.hintManager = new HintManager(); // Dependency via interface
    }

    @Override
    public void displayQuestion() {
        System.out.println("\n" + question);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.print("Jouw antwoord (1-" + options.size() + "): ");
    }

    @Override
    public boolean askQuestion(Scanner scanner, Speler speler) {
        alGehad = true;
        displayQuestion();

        try {
            int answer = Integer.parseInt(scanner.nextLine().trim());
            if (answer == correctAnswer) {
                System.out.println("Correct! Goed gedaan.");
                goedBeantwoord = true;
                return true;
            } else {
                System.out.println("Fout antwoord! Je verliest gezondheid.");
                speler.takeDamage();

                // Gebruik DIP: HintManager werkt via HintProvider interface
                if (!helpHint.isEmpty() || !funnyHint.isEmpty()) {
                    hintManager.offerHint(scanner, helpHint, funnyHint);
                } else {
                    System.out.println("Dat is helaas niet juist.");
                }
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Ongeldige invoer. Voer een nummer in tussen 1 en " + options.size());
            return false;
        }
    }

    public boolean isGoedBeantwoord() {
        return goedBeantwoord;
    }

    public QuestionType getQuestionType() {
        return QuestionType.MultipleChoice;
    }
}