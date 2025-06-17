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

    // Template methode - nu met 3 stappen, maar giveReward alleen bij succes
    public final void monsterEncounter(Speler speler) {
        toonIntroductie();
        boolean isDefeated = geefOpdracht(speler);
        if (isDefeated) {
            giveReward(speler);
        }
    }

    // Stap 1: Introductie tonen (kan worden overschreven)
    protected void toonIntroductie() {
        System.out.println("Je bent een monster tegengekomen!");
        System.out.println("beantwoord de vragen om te ontsnappen!");
        toonAsciiArt();
    }

    // Stap 2: Deze methode MOET worden geïmplementeerd door elke subklasse
    // Nu retourneert het een boolean om aan te geven of het monster is verslagen
    public abstract boolean geefOpdracht(Speler speler);

    // Stap 3: Encounter afronden met beloningen (alleen bij succes)
    protected void giveReward(Speler speler) {
        // Willekeurige beloningen na het verslaan van een monster
        Random random = new Random();
        double chance = random.nextDouble();

        if (chance < 0.05) {
            // 5% kans op Skip Monster item
            speler.addItem(new items.SkipMonster());
            System.out.println("🎉 Zeldzame beloning! Je hebt een Scroll of Monster Evasion gevonden!");
        } else if (chance < 0.15) {
            // 10% kans op sleutel (5% + 10% = 15% totaal)
            speler.addKey();
            System.out.println("🗝️ Je hebt een sleutel gevonden in de resten van het monster!");
        } else if (chance < 0.40) {
            // 25% kans op health boost (15% + 25% = 40% totaal)
            speler.heal(20);
            System.out.println("❤️ Gelukkig! Je hebt 20 health teruggekregen na het verslaan van het monster!");
        }
        // 60% kans op niets (100% - 40% = 60%)
    }

    public void toonAsciiArt(){
        if(asciiArt != null && !asciiArt.isEmpty()) {
            System.out.println(asciiArt);
        } else {
            System.out.println("[Geen ASCII-art beschikbaar]");
        }
    }

    protected boolean executeQuestionLoop(Speler speler, List<IQuestion> questionList) {
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

        // Return true als het monster is verslagen (minimaal 1 correct antwoord)
        return correcteAntwoorden >= 1;
    }
}