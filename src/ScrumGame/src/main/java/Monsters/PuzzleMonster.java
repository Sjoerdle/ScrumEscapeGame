package Monsters;

import Vragen.OpenQuestion;
import Vragen.PuzzleQuestion;
import Vragen.Question;
import org.game.Game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class PuzzleMonster extends Monster {
    String filePath;
    int questionCount;
    String asciiArt;
    Scanner scanner = new Scanner(System.in);
    ArrayList<PuzzleQuestion> questions = Game.questionLoader.getPuzzelVragen();

    public PuzzleMonster(String filePath, int questionCount, String asciiArt) {
        this.filePath = filePath;
        this.questionCount = questionCount;
        this.asciiArt = asciiArt;
    }

    @Override
    public void toonIntroductie() {
        System.out.println("Je bent een monster tegengekomen!");
        System.out.println("beantwoord de vragen om te ontsnappen!");
        toonAsciiArt();
    }

    @Override
    public void toonAsciiArt(){
        if(asciiArt != null && !asciiArt.isEmpty()) {
            System.out.println(asciiArt);
        }else{
            System.out.println("[Geen ASCII-art beschikbaar]");
        }
    }

    @Override
    public void geefOpdracht() {
        toonIntroductie();
        int correcteAntwoorden = 0;

        while (correcteAntwoorden < 2) {
            Random random = new Random();
            int randomIndex = random.nextInt(questions.size());
            Question randomQuestion = questions.get(randomIndex);

            if (randomQuestion.isGoedBeantwoord()) {
                continue;
            }

            boolean isCorrect = randomQuestion.askQuestion(scanner);

            if (isCorrect) {
                correcteAntwoorden++;
            }
        }
    }
    @Override
    protected boolean controleerAntwoord(String antwoord){

        return false;
    }
}