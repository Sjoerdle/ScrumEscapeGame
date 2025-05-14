package Vragen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//Alleen voor test
class QuestionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Open vraag
        ArrayList<Question> vragen = new ArrayList();
        vragen.add(new OpenQuestion("Hoe lang duurt een sprint?", "2 weken"));

        //Multiple Choice
        ArrayList mp1Antwoorden = new ArrayList();
        mp1Antwoorden.add("Scrum Master");
        mp1Antwoorden.add("Product Owner");
        mp1Antwoorden.add("Development Team");
        vragen.add(new MultipleChoice("Wie is verantwoordelijk voor het bijhouden van de Product Backlog?", mp1Antwoorden, 2));

        //Matching pairs
        Map<String, String> matchPairs = new HashMap<>();
        matchPairs.put("Scrum Master", "Verantwoordelijk voor het bevorderen en ondersteunen van Scrum zoals gedefinieerd in de Scrum Guide");
        matchPairs.put("Sprint", "Een tijdsperiode van maximaal een maand waarin een 'Done', bruikbaar en potentieel verscheepbaar product-increment wordt gemaakt");
        matchPairs.put("Daily Scrum", "Een dagelijkse 15-minuten tijdboxed gebeurtenis voor het Development Team om activiteiten te synchroniseren");
        matchPairs.put("Definition of Done", "Een gedeeld begrip van wanneer werk aan een product-increment is voltooid");
        matchPairs.put("Product Backlog", "Een geordende lijst van alles wat bekend is dat nodig is in het product");

        vragen.add(new PuzzleQuestion("Vul de juiste Scrum definities in bij de termen", matchPairs));

        for (Question question : vragen) {
            question.askQuestion(scanner);
        }
    }
}