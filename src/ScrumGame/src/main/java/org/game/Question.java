package org.game;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Collections;


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
    private List<String> shuffledDefinitions;
    private Map<Integer, Integer> definitionMapping;

    public boolean alGehad = false;
    public boolean goedBeantwoord = false;

    public PuzzleQuestion(String question, Map<String, String> matchPairs) {
        this.question = question;
        this.matchPairs = matchPairs;
        this.terms = new ArrayList<>(matchPairs.keySet());
        this.definitions = new ArrayList<>(matchPairs.values());
    }

    private void shuffleDefinitions() {
        shuffledDefinitions = new ArrayList<>(definitions);

        // Randomize de volgorde
        Collections.shuffle(shuffledDefinitions);

        // Bewaar de mapping tussen de nieuwe en oude posities
        definitionMapping = new HashMap<>();
        for (int i = 0; i < shuffledDefinitions.size(); i++) {
            String shuffledDef = shuffledDefinitions.get(i);
            int originalIndex = definitions.indexOf(shuffledDef);
            definitionMapping.put(i, originalIndex);
        }
    }

    @Override
    public void displayQuestion() {
        shuffleDefinitions();

        System.out.println("\n" + question);
        System.out.println("\nTermen:");
        for (int i = 0; i < terms.size(); i++) {
            System.out.println((i + 1) + ". " + terms.get(i));
        }

        System.out.println("\nDefinities:");
        for (int i = 0; i < shuffledDefinitions.size(); i++) {
            System.out.println((char)('A' + i) + ". " + shuffledDefinitions.get(i));
        }

        System.out.println("\nVoor elke term, geef de bijbehorende definitie-letter (A-" +
                (char)('A' + shuffledDefinitions.size() - 1) + ")");
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

                if (index >= 0 && index < shuffledDefinitions.size()) {
                    String selectedDefinition = shuffledDefinitions.get(index);
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


//Alleen voor test
class QuestionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StartScherm startScreen = new StartScherm();
        startScreen.clearScreen();
        ArrayList<Question> vragen = new ArrayList();
        vragen.add(new OpenQuestion("Hoe lang duurt een sprint?", "2 weken"));

        ArrayList mp1Antwoorden = new ArrayList();
        mp1Antwoorden.add("Scrum Master");
        mp1Antwoorden.add("Product Owner");
        mp1Antwoorden.add("Development Team");

        vragen.add(new MultipleChoice("Wie is verantwoordelijk voor het bijhouden van de Product Backlog?", mp1Antwoorden, 2));

        Map<String, String> matchPairs = new HashMap<>();
        matchPairs.put("Scrum Master", "Verantwoordelijk voor het bevorderen en ondersteunen van Scrum zoals gedefinieerd in de Scrum Guide");
        matchPairs.put("Sprint", "Een tijdsperiode van maximaal een maand waarin een 'Done', bruikbaar en potentieel verscheepbaar product-increment wordt gemaakt");
        matchPairs.put("Daily Scrum", "Een dagelijkse 15-minuten tijdboxed gebeurtenis voor het Development Team om activiteiten te synchroniseren");
        matchPairs.put("Definition of Done", "Een gedeeld begrip van wanneer werk aan een product-increment is voltooid");
        matchPairs.put("Product Backlog", "Een geordende lijst van alles wat bekend is dat nodig is in het product");

        vragen.add(new PuzzleQuestion("Vul de juiste Scrum definities in bij de termen", matchPairs));

        for (Question question : vragen) {
            question.askQuestion(scanner);
        }
    }
}