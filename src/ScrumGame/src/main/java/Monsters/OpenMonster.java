package Monsters;

import Vragen.OpenQuestion;
import Vragen.IQuestion;
import org.game.Game;
import player.Speler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class OpenMonster extends Monster {
    private List<IQuestion> questions = Game.questionLoader.getOpenVragen();

    public OpenMonster(String filePath, int questionCount, String asciiArt) {
        super(filePath, questionCount, asciiArt);
    }

    @Override
    public void geefOpdracht(Speler speler) {
        executeQuestionLoop(speler, questions);
    }
}