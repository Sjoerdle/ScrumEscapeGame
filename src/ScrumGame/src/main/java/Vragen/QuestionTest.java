package Vragen;

import org.game.Resources;

import java.util.*;

class QuestionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Question> vragen = new ArrayList<>();

        //laad vragen
        for (int i = 1; i <= 10; i++) {
            String questionPath = "MultipleChoiceQ" + i;
            String question = loadQuestion(questionPath);
            ArrayList<String> answers = loadAnswers(questionPath);
            int correctIndex = findCorrectAnswer(questionPath);

            vragen.add(new MultipleChoice(question, answers, correctIndex));
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