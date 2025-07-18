package Monsters;

import Vragen.IQuestion;
import org.game.Game;
import player.Speler;

import java.util.List;

public class PuzzleMonster extends Monster {
    private List<IQuestion> questions = Game.questionLoader.getPuzzelVragen();

    public PuzzleMonster(String filePath, int questionCount, String asciiArt) {
        super(filePath, questionCount, asciiArt);
    }

    @Override
    public boolean geefOpdracht(Speler speler) {
        return executeQuestionLoop(speler, questions);
    }
}