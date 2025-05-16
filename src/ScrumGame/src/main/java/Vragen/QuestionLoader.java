package Vragen;

import org.game.Resources;

import java.util.*;

public class QuestionLoader {
    private ArrayList<Question> vragen = new ArrayList<>();

    public QuestionLoader() {
        loadMultipleChoiceVragen();
        loadPuzzelVragen();
        loadOpenVragen();
    }

    private void loadMultipleChoiceVragen() {
        for(int i = 1; i <= Integer.MAX_VALUE; i++){
            if (Resources.exists("vragen/MultipleChoiceQ" + i)) {
                String questionPath = "MultipleChoiceQ" + i;
                String question = loadQuestion(questionPath);
                ArrayList<String> answers = loadAnswers(questionPath);
                int correctAnswer = findCorrectAnswer(questionPath);

                vragen.add(new MultipleChoice(question, answers, correctAnswer));
            } else {
                break;
            }
        }
    }

    private void loadPuzzelVragen() {
        for (int i = 1; i <= Integer.MAX_VALUE; i++) {
            if (Resources.exists("vragen/PuzzelQ" + i)) {
                String questionPath = "PuzzelQ" + i;
                HashMap<String, String> puzzleItems = loadPuzzleItems(questionPath);

                if (!puzzleItems.isEmpty()) {
                    vragen.add(new PuzzleQuestion(puzzleItems));
                }
            } else {
                break;
            }
        }
    }

    private void loadOpenVragen() {
        for (int i = 1; i <= Integer.MAX_VALUE; i++) {
            if (Resources.exists("vragen/OpenQ" + i)) {
                String questionPath = "OpenQ" + i;
                String question = loadQuestion(questionPath);
                String correctAnswer = loadAnswers(questionPath).get(0);

                vragen.add(new OpenQuestion(question, correctAnswer));
            } else {
                break;
            }
        }
    }

    private String loadQuestion(String questionPath) {
        String contents = Resources.getFileFromResouceAsString("vragen/" + questionPath);
        String[] lines = contents.split("\n");

        for (String line : lines) {
            if (line.startsWith("vraag=")) {
                return line.substring(6);
            }
        }
        return "";
    }

    private ArrayList<String> loadAnswers(String questionPath) {
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

    private HashMap<String, String> loadPuzzleItems(String questionPath) {
        String contents = Resources.getFileFromResouceAsString("vragen/" + questionPath);
        String[] lines = contents.split("\n");

        HashMap<String, String> puzzleItems = new HashMap<>();
        ArrayList<String> terms = new ArrayList<>();
        ArrayList<String> definitions = new ArrayList<>();

        //verzamel termen en definities
        for (String line : lines) {
            if (line.startsWith("term")) {
                String term = line.substring(line.indexOf('=') + 1);
                terms.add(term);
            } else if (line.startsWith("definitie")) {
                String definitie = line.substring(line.indexOf('=') + 1);
                definitions.add(definitie);
            }
        }

        //koppel termen en definities in hasmap
        for (int i = 0; i < terms.size() && i < definitions.size(); i++) {
            puzzleItems.put(terms.get(i), definitions.get(i));
        }

        return puzzleItems;
    }

    private int findCorrectAnswer(String questionPath) {
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

    public ArrayList<Question> getVragen() {
        return vragen;
    }
}