package Monsters;

import java.util.ArrayList;
import Vragen.*;
import org.game.Game;

import java.util.Random;
import java.util.Scanner;

public class MixMonster extends Monster {
    String filePath;
    int questionCount;
    Scanner scanner = new Scanner(System.in);
    ArrayList<Question> questions = Game.questionLoader.getAlleVragen();

    public MixMonster(String filePath, int questionCount) {
        this.filePath = filePath;
        this.questionCount = questionCount;
    }

    @Override
    protected void toonIntroductie() {
        System.out.println("Je bent een monster tegengekomen!");
        System.out.println("beantwoord de vragen om te ontsnappen!");
    }

    @Override
    protected void geefOpdracht() {
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