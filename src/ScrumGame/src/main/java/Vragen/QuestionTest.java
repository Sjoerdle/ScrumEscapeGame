package Vragen;

import org.game.Resources;

import java.util.*;

class QuestionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Open vraag
        ArrayList<Question> vragen = new ArrayList();

        //Multiple choice
        vragen.add(new MultipleChoice(loadQuestion("MultipleChoiceQ1.txt"), loadAnswers("MultipleChoiceQ1.txt"), 3));
        vragen.add(new MultipleChoice(loadQuestion("MultipleChoiceQ2"), loadAnswers("MultipleChoiceQ2"),2));
        vragen.add(new MultipleChoice(loadQuestion("MultipleChoiceQ3"), loadAnswers("MultipleChoiceQ3"), 2));
        vragen.add(new MultipleChoice(loadQuestion("MultipleChoiceQ4"), loadAnswers("MultipleChoiceQ4"), 4));
        vragen.add(new MultipleChoice(loadQuestion("MultipleChoiceQ5"), loadAnswers("MultipleChoiceQ5"), 1));
        vragen.add(new MultipleChoice(loadQuestion("MultipleChoiceQ6"), loadAnswers("MultipleChoiceQ6"), 3));
        vragen.add(new MultipleChoice(loadQuestion("MultipleChoiceQ7"), loadAnswers("MultipleChoiceQ7"), 2));
        vragen.add(new MultipleChoice(loadQuestion("MultipleChoiceQ8"), loadAnswers("MultipleChoiceQ8"), 1));
        vragen.add(new MultipleChoice(loadQuestion("MultipleChoiceQ9"), loadAnswers("MultipleChoiceQ9"), 4));
        vragen.add(new MultipleChoice(loadQuestion("MultipleChoiceQ10"), loadAnswers("MultipleChoiceQ10"), 2));


        for (Question question : vragen) {
            question.askQuestion(scanner);
        }
    }

    private static ArrayList loadAnswers(String questionPath) {
        String contents = Resources.getFileFromResouceAsString("vragen/" + questionPath);
        String[] lines = contents.split("\n");
        ArrayList antwoorden = new ArrayList();
        for (String line : lines) {
            if (line.startsWith("antwoord1=")) {
                String antwoord1 = line.substring(10);
                antwoorden.add(antwoord1);
            } else if (line.startsWith("antwoord2=")) {
                String antwoord2 = line.substring(10);
                antwoorden.add(antwoord2);
            } else if (line.startsWith("antwoord3=")) {
                String antwoord3 = line.substring(10);
                antwoorden.add(antwoord3);
            } else if (line.startsWith("antwoord4=")) {
                String antwoord4 = line.substring(10);
                antwoorden.add(antwoord4);
            }
        }
        return antwoorden;
    }

    private static String loadQuestion(String questionPath) {
        String contents = Resources.getFileFromResouceAsString("vragen/" + questionPath);
        String[] lines = contents.split("\n");
        String vraag = "";

        for (String line : lines) {
            if (line.startsWith("vraag=")) {
                vraag = line.substring(6);
            }
        }
        return vraag;
    }
}