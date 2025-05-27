package Vragen;

import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class MultipleChoice implements IQuestion {
    private String question;
    private List<String> options;
    private int correctAnswer;
    private String helpHint;
    private String funnyHint;
    private Random random;

    public boolean alGehad = false;
    public boolean goedBeantwoord = false;

    public MultipleChoice(String question, List<String> options, int correctAnswer) {
        this(question, options, correctAnswer, "", "");
    }

    public MultipleChoice(String question, List<String> options, int correctAnswer, String helpHint, String funnyHint) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.helpHint = helpHint;
        this.funnyHint = funnyHint;
        this.random = new Random();

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
                 if (!helpHint.isEmpty() || !funnyHint.isEmpty()) {
                     boolean useHelpHint = helpHint.isEmpty() ? false :
                             (funnyHint.isEmpty() ? true : random.nextBoolean());

                     if(useHelpHint) {
                         System.out.println("\nHINT: " + helpHint);
                     }else{
                         System.out.println("\nHINT: " + funnyHint);
                     }
                 } else {
                     System.out.println("Dat is helaas niet juist.");
                 };
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
    public QuestionType getQuestionType() { return QuestionType.MultipleChoice;}
}