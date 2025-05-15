package Vragen;

import org.game.Resources;

import java.util.*;

class QuestionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Question> vragen = new ArrayList<>();

        //laad MultipleChoice vragen
        for (int i = 1; i <= 10; i++) {
            String questionPath = "MultipleChoiceQ" + i;
            String question = loadQuestion(questionPath);
            ArrayList<String> answers = loadAnswers(questionPath);
            int correctAnswer = findCorrectAnswer(questionPath);

            vragen.add(new MultipleChoice(question, answers, correctAnswer));
        }

        //laad Open vragen
        for (int i = 1; i <= 5; i++) {
            String questionPath = "OpenQ" + i;
            String question = loadQuestion(questionPath);
            String correctAnswer = loadAnswers(questionPath).get(0);

            vragen.add(new OpenQuestion(question, correctAnswer));
        }

        //laad Puzzel vragen
        for (int i = 1; i <= 3; i++) {
            String questionPath = "PuzzelQ" + i;
            HashMap<String, String> puzzleItems = loadPuzzleItems(questionPath);

            vragen.add(new PuzzleQuestion(puzzleItems));
        }

        //stel vragen
        for (Question question : vragen) {
            question.askQuestion(scanner);
        }
    }

    private static String loadQuestion(String questionPath) {
        String contents = Resources.getFileFromResouceAsString("vragen/" + questionPath);
        String[] lines = contents.split("\n");

        for (String line : lines) {
            if (line.startsWith("vraag=")) {
                return line.substring(6);
            }
        }
        return "";
    }

    private static ArrayList<String> loadAnswers(String questionPath) {
        String contents = Resources.getFileFromResouceAsString("vragen/" + questionPath);
        String[] lines = contents.split("\n");
        ArrayList<String> antwoorden = new ArrayList<>();

        for (String line : lines) {
            if (line.startsWith("correct=")) {
                antwoorden.add(line.substring(8));
            } else if (line.startsWith("antwoord")) {
                antwoorden.add(line.substring(line.indexOf('=') + 1));
            }
        }

        return antwoorden;
    }

    private static HashMap<String, String> loadPuzzleItems(String questionPath) {
        String contents = Resources.getFileFromResouceAsString("vragen/" + questionPath);
        String[] lines = contents.split("\n");

        HashMap<String, String> puzzleItems = new HashMap<>();
        ArrayList<String> terms = new ArrayList<>();
        ArrayList<String> definitions = new ArrayList<>();

        // Eerst alle termen en definities verzamelen
        for (String line : lines) {
            if (line.startsWith("term")) {
                String term = line.substring(line.indexOf('=') + 1);
                terms.add(term);
            } else if (line.startsWith("definitie")) {
                String definitie = line.substring(line.indexOf('=') + 1);
                definitions.add(definitie);
            }
        }

        // Nu de termen en definities koppelen in de HashMap
        for (int i = 0; i < terms.size() && i < definitions.size(); i++) {
            puzzleItems.put(terms.get(i), definitions.get(i));
        }

        return puzzleItems;
    }

    private static int findCorrectAnswer(String questionPath) {
        String contents = Resources.getFileFromResouceAsString("vragen/" + questionPath);
        String[] lines = contents.split("\n");

        int teller = 1;
        for (String line : lines) {
            if (line.startsWith("correct=")) {
                return teller;
            } else if (line.startsWith("antwoord")) {
                teller++;
            }
        }

        return -1;
    }
}