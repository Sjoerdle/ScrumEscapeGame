package Monsters;

import java.util.ArrayList;
import Vragen.*;
import org.game.Game;

import java.util.Random;
import java.util.Scanner;

public class MixMonster extends Monster {
    String filePath;
    int questionCount;
    String asciiArt;
    Scanner scanner = new Scanner(System.in);
    ArrayList<Question> questions = Game.questionLoader.getAlleVragen();

    public MixMonster(String filePath, int questionCount, String asciiArt) {
        this.filePath = filePath;
        this.questionCount = questionCount;
        this.asciiArt = asciiArt;
    }

    @Override
    protected void toonIntroductie() {
        System.out.println("Je bent een monster tegengekomen!");
        System.out.println("beantwoord de vragen om te ontsnappen!");
        if (asciiArt != null && !asciiArt.isBlank()) {
            System.out.println(asciiArt);
        } else {
            System.out.println("[Geen ASCII-art beschikbaar]");
        }
    }

    private void toonAsciiArt(){
        if(asciiArt != null && !asciiArt.isEmpty()) {
            System.out.println(asciiArt);
        }else{
            System.out.println("[Geen ASCII-art beschikbaar]");
        }
    }

    @Override
    protected void geefOpdracht() {
        toonIntroductie();
        for (int i = 0; i < questionCount; i++) {
            Random random = new Random();
            int randomIndex = random.nextInt(questions.size());
            Question randomQuestion = questions.get(randomIndex);
            randomQuestion.askQuestion(scanner);
        }
    }

    @Override
    protected boolean controleerAntwoord(String antwoord){

        return false;
    }
}