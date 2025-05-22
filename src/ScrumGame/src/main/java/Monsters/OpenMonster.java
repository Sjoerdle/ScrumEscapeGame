package Monsters;

import Vragen.OpenQuestion;
import Vragen.Question;
import org.game.Game;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class OpenMonster extends Monster {
    String filePath;
    int questionCount;
    String asciiArt;
    Scanner scanner = new Scanner(System.in);
    ArrayList<OpenQuestion> questions = Game.questionLoader.getOpenVragen();

    public OpenMonster(String filePath, int questionCount, String asciiArt) {
        this.filePath = filePath;
        this.questionCount = questionCount;
        this.asciiArt = asciiArt;
    }

    @Override
    protected void toonIntroductie() {
        System.out.println("Je bent een monster tegengekomen!");
        System.out.println("beantwoord de vragen om te ontsnappen!");
        if(asciiArt != null && !asciiArt.isBlank()){
            System.out.println(asciiArt);
        }else{
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