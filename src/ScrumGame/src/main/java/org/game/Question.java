package org.game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.*;


interface Question {
    boolean askQuestion(Scanner scanner);
    void displayQuestion();
}


class MultipleChoice implements Question {
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


class OpenQuestion implements Question {
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


class PuzzleQuestion implements Question {
    private String question;
    private Map<String, String> matchPairs;
    private List<String> terms;
    private List<String> definitions;

    public boolean alGehad = false;
    public boolean goedBeantwoord = false;

    public PuzzleQuestion(String question, Map<String, String> matchPairs) {
        this.question = question;
        this.matchPairs = matchPairs;
        this.terms = new ArrayList<>(matchPairs.keySet());
        this.definitions = new ArrayList<>(matchPairs.values());
    }

    @Override
    public void displayQuestion() {
        System.out.println("\n" + question);
        System.out.println("\nTermen:");
        for (int i = 0; i < terms.size(); i++) {
            System.out.println((i + 1) + ". " + terms.get(i));
        }

        System.out.println("\nDefinities:");
        for (int i = 0; i < definitions.size(); i++) {
            System.out.println((char)('A' + i) + ". " + definitions.get(i));
        }

        System.out.println("\nVoor elke term, geef de bijbehorende definitie-letter (A-" +
                (char)('A' + definitions.size() - 1) + ")");
    }

    @Override
    public boolean askQuestion(Scanner scanner) {
        alGehad = true;
        displayQuestion();

        Map<String, String> userAnswers = new HashMap<>();
        int correctCount = 0;

        for (int i = 0; i < terms.size(); i++) {
            String term = terms.get(i);
            System.out.print("Term " + (i + 1) + " (" + term + "): ");
            String answer = scanner.nextLine().trim().toUpperCase();

            if (answer.length() == 1) {
                char letter = answer.charAt(0);
                int index = letter - 'A';

                if (index >= 0 && index < definitions.size()) {
                    String selectedDefinition = definitions.get(index);
                    userAnswers.put(term, selectedDefinition);

                    if (selectedDefinition.equals(matchPairs.get(term))) {
                        correctCount++;
                    }
                }
            }
        }

        System.out.println("\nJe hebt " + correctCount + " van de " + terms.size() + " correct!");
        if (correctCount == terms.size()) {
            System.out.println("Perfect! Je hebt alle termen correct gematcht.");
            goedBeantwoord = true;
            return true;
        } else {
            System.out.println("De correcte antwoorden waren:");
            for (String term : terms) {
                System.out.println(term + " → " + matchPairs.get(term));
            }
            return false;
        }
    }
}