package Vragen;

import hints.HintManager;
import java.util.Scanner;
import player.Speler;

public class OpenQuestion implements IQuestion {
    private String question;
    private String correctAnswer;
    private String helpHint;
    private String funnyHint;
    private HintManager hintManager;

    public boolean alGehad = false;
    public boolean goedBeantwoord = false;

    public OpenQuestion(String question, String correctAnswer, String helpHint, String funnyHint) {
        this.question = question;
        this.correctAnswer = correctAnswer.toLowerCase();
        this.helpHint = helpHint;
        this.funnyHint = funnyHint;
        this.hintManager = new HintManager(); // Dependency via interface
    }

    @Override
    public void displayQuestion() {
        System.out.println("\n" + question);
        System.out.print("Jouw antwoord: ");
    }

    @Override
    public boolean askQuestion(Scanner scanner, Speler speler) {
        alGehad = true;
        displayQuestion();

        String answer = scanner.nextLine().trim().toLowerCase();
        if (answer.equals(correctAnswer)) {
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
    }

    public boolean isGoedBeantwoord() {
        return goedBeantwoord;
    }

    public QuestionType getQuestionType() {
        return QuestionType.Open;
    }
}