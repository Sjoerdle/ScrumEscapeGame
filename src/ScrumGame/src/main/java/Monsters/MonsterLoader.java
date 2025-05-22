package Monsters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.game.Game;
import org.game.Resources;

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
     * @param filePath Het pad naar het configuratie bestand
     */
    public void loadMonsterFromFile(String filePath) {
        String contents = Resources.getFileFromResouceAsString("monsters/" + filePath);
        String[] lines = contents.split("\n");

        String questionTypes = "";
        int questionCount = 0;

        for (String line : lines) {
            if (line.startsWith("questionType=")) {
                questionTypes = line.substring(13);
            } else if (line.startsWith("questionCount=")) {
                questionCount = Integer.parseInt(line.substring(14));
            }
        }

        if (questionTypes.isEmpty() || questionCount == 0) {
            System.err.println("Ongeldig bestandsformaat: " + filePath);
            return;
        }

        // Split de question types op komma's
        String[] questionTypeArray = questionTypes.split(",");

        // Maak monsters aan gebaseerd op de types
        createMonstersFromTypes(questionTypeArray, questionCount, filePath);
    }

    /**
     * Maakt monster objecten aan gebaseerd op de gegeven types
     */
    private void createMonstersFromTypes(String[] questionTypes, int questionCount, String filePath) {
        for (String type : questionTypes) {
            type = type.trim().toLowerCase();

            switch (type) {
                case "open":
                    OpenMonster openMonster = new OpenMonster(filePath, questionCount);
                    openMonsters.add(openMonster);
                    break;

                case "multiple_choice":
                case "multiple":
                    MultiChoiceMonster multiChoiceMonster = new MultiChoiceMonster(filePath, questionCount);
                    multiChoiceMonsters.add(multiChoiceMonster);
                    break;

                case "puzzle":
                    PuzzleMonster puzzleMonster = new PuzzleMonster(filePath, questionCount);
                    puzzleMonsters.add(puzzleMonster);
                    break;

                case "mix":
                    MixMonster mixMonster = new MixMonster(filePath, questionCount);
                    mixMonsters.add(mixMonster);
                    break;

                default:
                    System.err.println("Onbekend question type: " + type + " in bestand: " + filePath);
                    break;
            }
        }
    }

    /**
     * Laadt alle monster bestanden uit een directory
     * @param directoryPath Het pad naar de directory met monster bestanden
     */
    public void loadAllMonstersFromDirectory(String directoryPath) {
        java.io.File directory = new java.io.File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Directory bestaat niet of is geen directory: " + directoryPath);
            return;
        }

        java.io.File[] files = directory.listFiles();
        if (files != null) {
            for (java.io.File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    loadMonsterFromFile(file.getAbsolutePath());
                }
            }
        }
    }

    // Getter methoden voor de verschillende monster ArrayLists
    public ArrayList<OpenMonster> getOpenMonsters() {
        return openMonsters;
    }

    public ArrayList<MultiChoiceMonster> getMultiChoiceMonsters() {
        return multiChoiceMonsters;
    }

    public ArrayList<PuzzleMonster> getPuzzleMonsters() {
        return puzzleMonsters;
    }

    public ArrayList<MixMonster> getMixMonsters() {
        return mixMonsters;
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
        return allMonsters;
    }

    /**
     * Print informatie over alle geladen monsters
     */
    public void printMonsterInfo() {
        System.out.println("=== Monster Loader Informatie ===");
        System.out.println("Open Monsters: " + openMonsters.size());
        System.out.println("Multiple Choice Monsters: " + multiChoiceMonsters.size());
        System.out.println("Puzzle Monsters: " + puzzleMonsters.size());
        System.out.println("Mix Monsters: " + mixMonsters.size());
        System.out.println("Totaal aantal monsters: " + getAllMonsters().size());
    }

    /**
     * Voorbeeld van hoe de klasse te gebruiken
     */
    public static void main(String[] args) {
        Game game = new Game();
        MonsterLoader loader = new MonsterLoader();

        // Laad een enkel monster bestand
        loader.loadMonsterFromFile("monster_Open.txt");

        // Of laad alle bestanden uit een directory
        // loader.loadAllMonstersFromDirectory("monsters/");

        // Print informatie
        loader.printMonsterInfo();

        // Gebruik de monsters
        ArrayList<OpenMonster> openMonsters = loader.getOpenMonsters();
        for (OpenMonster monster : openMonsters) {
            // Doe iets met het monster
            monster.geefOpdracht();
        }
    }
}