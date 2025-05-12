package org.game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


interface Question {
    boolean askQuestion(Scanner scanner);
    void displayQuestion();
}


class MultipleChoice implements Question {
    private String question;
    private List<String> options;
    private int correctAnswer;

    public MultipleChoice(String question, List<String> options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
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
    public boolean askQuestion(Scanner scanner) {
        displayQuestion();

        try {
            int answer = Integer.parseInt(scanner.nextLine().trim());
            if (answer == correctAnswer) {
                System.out.println("Correct! Goed gedaan.");
                return true;
            } else {
                System.out.println("Dat is helaas niet juist. Het correcte antwoord was: " + correctAnswer);
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Ongeldige invoer. Voer een nummer in tussen 1 en " + options.size());
            return false;
        }
    }
}


class OpenQuestion implements Question {
    private String question;
    private String correctAnswer;

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
        displayQuestion();

        String answer = scanner.nextLine().trim().toLowerCase();
        if (answer.equals(correctAnswer)) {
            System.out.println("Correct! Goed gedaan.");
            return true;
        } else {
            System.out.println("Dat is helaas niet juist. Het correcte antwoord was: " + correctAnswer);
            return false;
        }
    }
}
