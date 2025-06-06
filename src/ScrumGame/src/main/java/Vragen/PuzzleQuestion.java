package Vragen;

import java.util.*;
import player.Speler;

public class PuzzleQuestion implements IQuestion {
    private String question;
    private Map<String, String> matchPairs;
    private List<String> terms;
    private List<String> definitions;
    private List<String> shuffledDefinitions;
    private Map<Integer, Integer> definitionMapping;

    public boolean alGehad = false;
    public boolean goedBeantwoord = false;

    public PuzzleQuestion(Map<String, String> matchPairs) {
        this.question = "Match de termen met hun definities.";
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
    public boolean askQuestion(Scanner scanner, Speler speler) {
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
            System.out.println("Fout antwoord! Je verliest gezondheid.");
            speler.takeDamage();

            System.out.println("De correcte antwoorden waren:");
            for (String term : terms) {
                System.out.println(term + " → " + matchPairs.get(term));
            }
            return false;
        }
    }

    public boolean isGoedBeantwoord() {
        return goedBeantwoord;
    }

    public QuestionType getQuestionType() {
        return QuestionType.Puzzle;
    }
}