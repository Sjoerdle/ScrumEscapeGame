package Monsters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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
        }else {
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
        Collections.shuffle(allMonsters); // Tijdelijke functie???

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

    /**
     * Voorbeeld van hoe de klasse te gebruiken
     */
    public static void main(String[] args) {
        // 1. Maak een MonsterLoader aan
        MonsterLoader loader = new MonsterLoader();
        Game game = new Game();

        // 3. Krijg alle monsters in één ArrayList
        ArrayList<Monster> alleMonsters = loader.loadAllMonsters();

        // 4. Check of er monsters zijn geladen
        if (alleMonsters.isEmpty()) {
            System.out.println("Geen monsters geladen!");
            return;
        }

        // 5. Print informatie over geladen monsters
        loader.printMonsterInfo();

        // 6. Selecteer een random monster
        Random random = new Random();
        int randomIndex = random.nextInt(alleMonsters.size());
        Monster randomMonster = alleMonsters.get(randomIndex);

        // 7. Gebruik het random monster
        System.out.println("\n=== Random Monster Geselecteerd ===");
        randomMonster.geefOpdracht();

        // Extra: Als je meerdere random monsters wilt
        System.out.println("\n=== 3 Random Monsters ===");
        for (int i = 0; i < 3 && i < alleMonsters.size(); i++) {
            int index = random.nextInt(alleMonsters.size());
            Monster monster = alleMonsters.get(index);
            System.out.println("Monster " + (i + 1) + ":");
            monster.geefOpdracht();
            System.out.println();
        }
    }

    // Helper methode om een random monster te krijgen
    public static Monster getRandomMonster(ArrayList<Monster> monsters) {
        if (monsters.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return monsters.get(random.nextInt(monsters.size()));
    }

    // Helper methode om een random monster van een specifiek type te krijgen
    public static OpenMonster getRandomOpenMonster(MonsterLoader loader) {
        ArrayList<OpenMonster> openMonsters = loader.getOpenMonsters();
        if (openMonsters.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return openMonsters.get(random.nextInt(openMonsters.size()));
    }
}