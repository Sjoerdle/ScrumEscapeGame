package Monsters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.game.Game;
import ui.Resources;

public class MonsterLoader {
    private ArrayList<OpenMonster> openMonsters;
    private ArrayList<MultiChoiceMonster> multiChoiceMonsters;
    private ArrayList<PuzzleMonster> puzzleMonsters;
    private ArrayList<MixMonster> mixMonsters;

    public MonsterLoader() {
        this.openMonsters = new ArrayList<>();
        this.multiChoiceMonsters = new ArrayList<>();
        this.puzzleMonsters = new ArrayList<>();
        this.mixMonsters = new ArrayList<>();
    }

    /**
     * Leest een monster configuratie bestand en maakt het bijbehorende monster object aan
     *
     * @param filePath Het pad naar het configuratie bestand
     */
    public void loadMonsterFromFile(String filePath) {
        String contents = Resources.getFileFromResouceAsString("monsters/" + filePath);
        String[] lines = contents.split("\n");

        String questionTypes = "";
        int questionCount = 0;
        StringBuilder asciiBuilder = new StringBuilder();
        boolean isReadingAscii = false;

        for (String line : lines) {
            if (line.startsWith("questionType=")) {
                questionTypes = line.substring(13);
            } else if (line.startsWith("questionCount=")) {
                questionCount = Integer.parseInt(line.substring(14));
            } else if (!line.trim().isEmpty()) {
                isReadingAscii = true;
                asciiBuilder.append(line).append("\n");
            } else {
                asciiBuilder.append(line).append("\n");
            }
        }
        String asciiArt = asciiBuilder.toString().trim();

        if (questionTypes.isEmpty() || questionCount == 0) {
            System.err.println("Ongeldig bestandsformaat: " + filePath);
            return;
        }

        // Split de question types op komma's
        String[] questionTypeArray = questionTypes.split(",");
        String ascii = asciiBuilder.toString();
        // Maak monsters aan gebaseerd op de types
        createMonstersFromTypes(questionTypeArray, questionCount, filePath, ascii);
    }

    /**
     * Maakt monster objecten aan gebaseerd op de gegeven types
     */
    private void createMonstersFromTypes(String[] questionTypes, int questionCount, String filePath, String asciiArt) {
        for (String type : questionTypes) {
            type = type.trim().toLowerCase();

            switch (type) {
                case "open":
                    OpenMonster openMonster = new OpenMonster(filePath, questionCount, asciiArt);
                    openMonsters.add(openMonster);
                    break;

                case "multiple_choice":
                case "multiple":
                    MultiChoiceMonster multiChoiceMonster = new MultiChoiceMonster(filePath, questionCount, asciiArt);
                    multiChoiceMonsters.add(multiChoiceMonster);
                    break;

                case "puzzle":
                    PuzzleMonster puzzleMonster = new PuzzleMonster(filePath, questionCount, asciiArt);
                    puzzleMonsters.add(puzzleMonster);
                    break;

                case "mix":
                    MixMonster mixMonster = new MixMonster(filePath, questionCount, asciiArt);
                    mixMonsters.add(mixMonster);
                    break;

                default:
                    System.err.println("Onbekend question type: " + type + " in bestand: " + filePath);
                    break;
            }
        }
    }

    /**
     * Geeft alle monsters terug in één lijst
     */
    public ArrayList<Monster> getAllMonsters() {
        ArrayList<Monster> allMonsters = new ArrayList<>();
        allMonsters.addAll(openMonsters);
        allMonsters.addAll(multiChoiceMonsters);
        allMonsters.addAll(puzzleMonsters);
        allMonsters.addAll(mixMonsters);
        Collections.shuffle(allMonsters); // Tijdelijke functie???

        return allMonsters;
    }

    public ArrayList<Monster> loadAllMonsters() {
        // Lijst van alle monster bestanden in je resources/monsters directory
        String[] monsterFiles = {
                "monster_MultiChoice.txt",
                "monster_Open.txt",
                "monster_OpenPuzzle.txt",
                "monster_Puzzle.txt"
        };


        for (String fileName : monsterFiles) {
            try {
                loadMonsterFromFile(fileName);
            } catch (Exception e) {
                System.err.println("✗ Fout bij laden van " + fileName + ": " + e.getMessage());
            }
        }

        return getAllMonsters();
    }
}