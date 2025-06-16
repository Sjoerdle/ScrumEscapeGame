package Monsters;

import java.util.ArrayList;
import Vragen.*;
import org.game.Game;
import player.Speler;

import java.util.Random;
import java.util.Scanner;

public class MixMonster extends Monster {
    private ArrayList<IQuestion> questions = Game.questionLoader.getAlleVragen();

    public MixMonster(String filePath, int questionCount, String asciiArt) {
        super(filePath, questionCount, asciiArt);
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