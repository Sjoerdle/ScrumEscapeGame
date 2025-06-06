package Monsters;

import Vragen.MultipleChoice;
import Vragen.IQuestion;
import org.game.Game;
import player.Speler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MultiChoiceMonster extends Monster {
    String filePath;
    int questionCount;
    String asciiArt;
    Scanner scanner = new Scanner(System.in);
    List<IQuestion> questions = Game.questionLoader.getMultipleChoiceVragen();

    public MultiChoiceMonster(String filePath, int questionCount, String asciiArt) {
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
    public void geefOpdracht(Speler speler) {
        toonIntroductie();
        int correcteAntwoorden = 0;
        int pogingen = 0;
        final int MAX_POGINGEN = 3; // Voorkom oneindige lus

        while (correcteAntwoorden < 1 && pogingen < MAX_POGINGEN) {
            Random random = new Random();
            int randomIndex = random.nextInt(questions.size());
            IQuestion randomQuestion = questions.get(randomIndex);

            if (randomQuestion.isGoedBeantwoord()){
                continue;
            }

            boolean isCorrect = randomQuestion.askQuestion(scanner, speler);
            pogingen++;

            if (isCorrect){
                correcteAntwoorden++;
            }
        }
    }

    @Override
    protected boolean controleerAntwoord(String antwoord){
        return false;
    }
}