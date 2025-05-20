package Vragen;

import java.util.Scanner;


public interface Question {
    boolean askQuestion(Scanner scanner);
    void displayQuestion();
}