package Monsters;

import java.util.Scanner;
import player.Speler;

public abstract class Monster {

    public final void doorLoopKamer(Speler speler) {
        toonIntroductie();
        geefOpdracht(speler);
        String antwoord = vraagAntwoord();
        boolean isCorrect = controleerAntwoord(antwoord);
        toonResultaat(isCorrect);
        geefFeedback(isCorrect);
    }

    // Deze 3 methoden MOETEN worden geïmplementeerd door elke subklasse
    public abstract void toonIntroductie();
    public abstract void geefOpdracht(Speler speler);
    protected abstract boolean controleerAntwoord(String antwoord);

    // Deze 3 methodes kunnen worden overschreven, maar hebben al een standaardimplementatie
    // als je deze methodes wil wijzigen MOET je ze overriden anders worden deze methodes automatisch gebruikt

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

    public abstract void toonAsciiArt();
}