package Vragen;

import java.util.Scanner;


interface Question {
    boolean askQuestion(Scanner scanner);
    void displayQuestion();
}