package Vragen;

import java.util.Scanner;
import player.Speler;

public interface IQuestion {
    boolean askQuestion(Scanner scanner, Speler speler);
    void displayQuestion();
    boolean isGoedBeantwoord();
    QuestionType getQuestionType();
}