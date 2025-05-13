package Vragen;

import java.util.Scanner;

public class OpenQuestion implements Question {
    private String question;
    private String correctAnswer;

    public boolean alGehad = false;
    public boolean goedBeantwoord = false;

    public OpenQuestion(String question, String correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer.toLowerCase();
    }

    @Override
    public void displayQuestion() {
        System.out.println("\n" + question);
        System.out.print("Jouw antwoord: ");
    }

    @Override
    public boolean askQuestion(Scanner scanner) {
        alGehad = true;
        displayQuestion();

        String answer = scanner.nextLine().trim().toLowerCase();
        if (answer.equals(correctAnswer)) {
            System.out.println("Correct! Goed gedaan.");
            goedBeantwoord = true;
            return true;
        } else {
            System.out.println("Dat is helaas niet juist. Het correcte antwoord was: " + correctAnswer);
            return false;
        }
    }
}