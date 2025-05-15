package Vragen;

import org.game.Resources;

import java.util.*;

//Alleen voor test
class QuestionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Open vraag
        ArrayList<Question> vragen = new ArrayList();

        //Multiple choice
        String questionPath = "Q1.txt";

        ArrayList mp1Antwoorden = new ArrayList();
        loadAntwoorden(questionPath, mp1Antwoorden);
        vragen.add(new MultipleChoice("Hoe lang duurt een sprint?", mp1Antwoorden, 3));

        for (Question question : vragen) {
            question.askQuestion(scanner);
        }
    }


    private static void loadAntwoorden(String questionPath, ArrayList antwoorden) {
        String contents = Resources.getFileFromResouceAsString("vragen/" + questionPath);
        String[] lines = contents.split("\n");

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
    }
}