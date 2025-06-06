package Vragen;

import java.util.Scanner;
import java.util.Random;
import player.Speler;

public class OpenQuestion implements IQuestion {
    private String question;
    private String correctAnswer;
    private String helpHint;
    private String funnyHint;
    private Random random;

    public boolean alGehad = false;
    public boolean goedBeantwoord = false;

    public OpenQuestion(String question, String correctAnswer) {
        this(question, correctAnswer, "", "");
    }

    public OpenQuestion(String question, String correctAnswer, String helpHint, String funnyHint) {
        this.question = question;
        this.correctAnswer = correctAnswer.toLowerCase();
        this.helpHint = helpHint;
        this.funnyHint = funnyHint;
        this.random = new Random();
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

            if (!helpHint.isEmpty() || !funnyHint.isEmpty()) {
                boolean useHelpHint = helpHint.isEmpty() ? false :
                        (funnyHint.isEmpty() ? true : random.nextBoolean());

                if (useHelpHint) {
                    System.out.println("\nHINT: " + helpHint);
                } else {
                    System.out.println("\nHINT: " + funnyHint);
                }
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