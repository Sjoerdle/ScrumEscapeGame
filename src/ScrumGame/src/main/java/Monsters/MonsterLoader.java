package Monsters;

import org.game.Resources;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonsterLoader {
    private List<Monster> monsters = new ArrayList<>();

    public MonsterLoader(String filePath) {
        Map<String, String> variabelen = laadVariabelen(filePath);

        if (variabelen.containsKey("questionType")) {
            String questionType = variabelen.get("questionType");

            //Controleer op combinaties van types
            if (variabelen.containsKey("questionType1") && variabelen.containsKey("questionType2")) {
                // Maak een MixMonster voor combinaties van types
                monsters.add(new MixMonster(filePath));
            } else {
                switch (questionType) {
                    case "multiple_choice":
                        monsters.add(new MultiChoiceMonster(filePath));
                        break;
                    case "open":
                        monsters.add(new OpenMonster(filePath));
                        break;
                    case "puzzle":
                        monsters.add(new PuzzleMonster(filePath));
                        break;
                    default:
                        System.err.println("Onbekend vraagtype: " + questionType);
                        break;
                }
            }
        } else {
            System.err.println("Geen questionType gevonden in het bestand: " + filePath);
        }
    }

    public MonsterLoader(String[] bestandsNamen) {
        for (String bestandsNaam : bestandsNamen) {
            Map<String, String> variabelen = laadVariabelen(bestandsNaam);

            // Bepaal welk type monster we moeten aanmaken op basis van questionType
            if (variabelen.containsKey("questionType")) {
                String questionType = variabelen.get("questionType");

                // Controleer of er meerdere types zijn
                if (variabelen.containsKey("questionType1") && variabelen.containsKey("questionType2")) {
                    // Controleer specifiek voor open,puzzle combinatie
                    if (variabelen.get("questionType1").equals("open") &&
                            variabelen.get("questionType2").equals("puzzle")) {
                        monsters.add(new MixMonster(bestandsNaam));
                    } else {
                        // Voor andere combinaties kies een standaard type op basis van eerste type
                        switch (variabelen.get("questionType1")) {
                            case "multiple_choice":
                                monsters.add(new MultiChoiceMonster(bestandsNaam));
                                break;
                            case "open":
                                monsters.add(new OpenMonster(bestandsNaam));
                                break;
                            case "puzzle":
                                monsters.add(new PuzzleMonster(bestandsNaam));
                                break;
                            default:
                                System.err.println("Onbekend vraagtype: " + questionType);
                                break;
                        }
                    }
                } else {
                    // Kies het juiste type monster op basis van questionType
                    switch (questionType) {
                        case "multiple_choice":
                            monsters.add(new MultiChoiceMonster(bestandsNaam));
                            break;
                        case "open":
                            monsters.add(new OpenMonster(bestandsNaam));
                            break;
                        case "puzzle":
                            monsters.add(new PuzzleMonster(bestandsNaam));
                            break;
                        default:
                            System.err.println("Onbekend vraagtype: " + questionType);
                            break;
                    }
                }
            } else {
                System.err.println("Geen questionType gevonden in het bestand: " + bestandsNaam);
            }
        }
    }


    public List<Monster> getMonsters() {
        return monsters;
    }

    public Monster getMonster(int index) {
        if (index >= 0 && index < monsters.size()) {
            return monsters.get(index);
        }
        return null;
    }

    public int getAantalMonsters() {
        return monsters.size();
    }

    /**
     * Leest een tekstbestand en haalt de variabelen eruit.
     * Variabelen zijn regels in het formaat "naam=waarde".
     *
     * @param bestandsNaam Het pad naar het bestandsnaam in de resources/vragen map
     * @return Een Map met alle gevonden variabelen
     */
    public static Map<String, String> laadVariabelen(String bestandsNaam) {
        Map<String, String> variabelen = new HashMap<>();

        String inhoud = Resources.getFileFromResouceAsString("vragen/" + bestandsNaam);
        if (inhoud == null || inhoud.isEmpty()) {
            System.err.println("Kon bestand niet laden: " + bestandsNaam);
            return variabelen;
        }

        String[] regels = inhoud.split("\n");

        for (String regel : regels) {
            regel = regel.trim();

            if (regel.contains("=")) {
                String[] delen = regel.split("=", 2);
                String naam = delen[0].trim();
                String waarde = delen.length > 1 ? delen[1].trim() : "";

                // meerdere variabelen
                if (naam.equals("questionType") && waarde.contains(",")) {
                    String[] types = waarde.split(",");
                    for (int i = 0; i < types.length; i++) {
                        if (i == 0) {
                            variabelen.put("questionType", types[i].trim());
                        }
                        variabelen.put("questionType" + (i + 1), types[i].trim());
                    }
                } else {
                    // Gewone variabele
                    variabelen.put(naam, waarde);
                }
            }
        }

        return variabelen;
    }
}
