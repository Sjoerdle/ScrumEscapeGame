package Monsters;

import java.util.Scanner;
import player.Speler;
import java.util.List;
import java.util.Random;
import Vragen.IQuestion;

public abstract class Monster {

    protected String filePath;
    protected int questionCount;
    protected String asciiArt;
    protected Scanner scanner;

    public Monster(String filePath, int questionCount, String asciiArt) {
        this.filePath = filePath;
        this.questionCount = questionCount;
        this.asciiArt = asciiArt;
        this.scanner = new Scanner(System.in);
    }

    public Monster() {
        this.scanner = new Scanner(System.in);
    }

    // Template methode
    public final void doorLoopKamer(Speler speler) {
        toonIntroductie();
        geefOpdracht(speler);

    }

    // Deze methode MOET worden geïmplementeerd door elke subklasse
    public abstract void geefOpdracht(Speler speler);

    // Deze methode is niet meer nodig voor de template, maar behouden voor compatibiliteit
    protected boolean controleerAntwoord(String antwoord) {
        return false;
    }

    // Deze methodes kunnen worden overschreven, maar hebben al een standaardimplementatie
    protected String vraagAntwoord() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("val aan met je antwoord!: ");
        return scanner.nextLine();
    }

    protected void toonResultaat(boolean isCorrect) {
        if (isCorrect) {
            System.out.println("je hebt de uitdaging gehaald!");
            System.out.print("Ga door naar de volgende kamer");
        } else {
            System.out.println("Helaas je hebt gefaald..");
            System.out.print("Maak je klaar voor het gevecht!");
        }
    }

    protected void geefFeedback(boolean isCorrect) {
        if (isCorrect ) return;
        System.out.println("Probeer het nog eens. Hint: ...");
    }

    public void toonIntroductie() {
        System.out.println("Je bent een monster tegengekomen!");
        System.out.println("beantwoord de vragen om te ontsnappen!");
        toonAsciiArt();
    }

    public void toonAsciiArt(){
        if(asciiArt != null && !asciiArt.isEmpty()) {
            System.out.println(asciiArt);
        } else {
            System.out.println("[Geen ASCII-art beschikbaar]");
        }
    }

    protected void executeQuestionLoop(Speler speler, List<IQuestion> questionList) {
        int correcteAntwoorden = 0;
        int pogingen = 0;
        final int MAX_POGINGEN = 3;

        while (correcteAntwoorden < 1 && pogingen < MAX_POGINGEN) {
            Random random = new Random();
            int randomIndex = random.nextInt(questionList.size());
            IQuestion randomQuestion = questionList.get(randomIndex);

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
}