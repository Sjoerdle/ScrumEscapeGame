package Vragen;

import org.game.Resources;

import java.util.*;

class QuestionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Open vraag
        ArrayList<Question> vragen = new ArrayList();

        //Multiple choice
        ArrayList mp1Antwoorden = new ArrayList();
        vragen.add(new MultipleChoice(loadQuestion("Q1.txt", mp1Antwoorden), mp1Antwoorden, 3));

        for (Question question : vragen) {
            question.askQuestion(scanner);
        }
    }

    private static String loadQuestion(String questionPath, ArrayList antwoorden) {
        String contents = Resources.getFileFromResouceAsString("vragen/" + questionPath);
        String[] lines = contents.split("\n");
        String vraag = "";

        for (String line : lines) {
            if (line.startsWith("vraag=")) {
                vraag = line.substring(6);
            } else if (line.startsWith("antwoord1=")) {
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
        return vraag;
    }
}