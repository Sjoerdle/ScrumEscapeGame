package Monsters;

public class MultiChoiceMonster extends Monster {
    @Override
    protected void toonIntroductie() {
        System.out.println("Beantwoord de vragen om het monster te verslaan!");
    }

    @Override
    protected void geefOpdracht() {
        
    }

    @Override
    protected boolean controleerAntwoord(String antwoord) {

        return false;
    }
}
