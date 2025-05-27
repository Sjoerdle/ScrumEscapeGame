package Vragen;

import java.util.Scanner;


public interface IQuestion {
    boolean askQuestion(Scanner scanner);
    void displayQuestion();

    boolean isGoedBeantwoord();
    QuestionType getQuestionType();
}