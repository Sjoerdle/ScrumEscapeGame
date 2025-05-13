package Vragen;

import java.util.List;
import java.util.Scanner;

public class MultipleChoice implements Question {
    private String question;
    private List<String> options;
    private int correctAnswer;

    public boolean alGehad = false;
    public boolean goedBeantwoord = false;

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
        alGehad = true;
        displayQuestion();

        try {
            int answer = Integer.parseInt(scanner.nextLine().trim());
            if (answer == correctAnswer) {
                System.out.println("Correct! Goed gedaan.");
                goedBeantwoord = true;
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