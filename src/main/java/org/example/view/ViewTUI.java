package org.example.view;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class ViewTUI extends View {

    private static final HashMap<String, String> resourceColors = new HashMap<>();
    private static final HashMap<String, String> resourceInitials = new HashMap<>();

    static {
        resourceColors.put("FUNGI", "\u001B[31m"); // Rosso
        resourceColors.put("PLANT", "\u001B[32m"); // Verde
        resourceColors.put("ANIMAL", "\u001B[36m"); // Celeste
        resourceColors.put("INSECT", "\u001B[35m"); // Viola
        resourceColors.put("EMPTY", "\u001B[34m"); // Blu
        resourceColors.put("HIDDEN", "\u001B[33m"); // Giallo
        resourceColors.put("INKWELL", "\u001B[33m"); // Oro
        resourceColors.put("QUILL", "\u001B[33m"); // Oro
        resourceColors.put("MANUSCRIPT", "\u001B[33m"); // Oro
        resourceInitials.put("FUNGI", "F");
        resourceInitials.put("PLANT", "P");
        resourceInitials.put("ANIMAL", "A");
        resourceInitials.put("INSECT", "I");
        resourceInitials.put("EMPTY", "E");
        resourceInitials.put("HIDDEN", "H");
        resourceInitials.put("INKWELL", "I");
        resourceInitials.put("QUILL", "Q");
        resourceInitials.put("MANUSCRIPT", "M");
    }

    private static final String RESET_COLOR = "\u001B[0m";

    public ViewTUI() {
        // Initialize the grid with empty strings
        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(grid[i], "");
        }
    }

    public void message(int x) {
        switch (x) {
            case 1:
                System.out.println("Command successfully executed");
                break;
            case 2:
                System.out.println("Command not executed");
                break;
            case 3:
                System.out.println("Everyone has set their Starter and Objective cards");
                System.out.println("play:choice,side,x,y");
                System.out.println("Where choice is the number of the card of your hand (1-3), side (1-2), and x-y are the coordinates");
                System.out.println("draw:x (1-5)");
                break;
            case 4:
                System.out.println("Not your turn!");
            default:
                break;
        }
    }

    public void drawnCard(int id) {
        System.out.println("You have drawn:");
        printCardDetails(getCardById(id));
    }

    public void hasDrawn(String username, int id) {
        System.out.println("Player: " + username + " has drawn");
        printCardDetails(getCardById(id));
    }

    public void hasPlayed(String username, int id) {
        System.out.println("Player: " + username + " has played");
        printCardDetails(getCardById(id));
    }


    @Override
    public void playedCard(int id, int x, int y) {
        super.playCardInGrid(x, y);
        System.out.println("You played card at position: (" + x + "," + y + ")");
        printCardDetails(getCardById(id));
        printGrid();
    }

    @Override
    public void unplayable(int id, int x, int y) {
        System.out.println("The card is unplayable at position: (" + x + "," + y + ")");
        printCardDetails(getCardById(id));
        printGrid();
    }

    public void firstHand(int id1, int id2, int id3, int id4, int id5, int id6) {
        System.out.println("In your hand:\n");
        //printCardDetails(getCardById(id1));
        Hand.add(id1);
        //printCardDetails(getCardById(id2));
        Hand.add(id2);
        //printCardDetails(getCardById(id3));
        Hand.add(id3);
        printHand();
        System.out.println("Now please choose the side of the starter card:");
        printCardDetails(getCardById(id4));
        PlayerCardArea.add(id4);
        System.out.println("And what Objective Card you want to keep:");
        printCardDetails(getCardById(id5));
        printCardDetails(getCardById(id6));
        System.out.println("You should write 'setObjStarter:x,y', where " +
                "\nx is for the side of the starter card and " +
                "\ny è la carta obiettivo che vuoi mantenere");
    }

    public void printHand () {
        for(int i = 0; i < Hand.size(); i++) {
            printCardDetails(getCardById(Hand.get(i)));
        }
    }


    public void pubObj(int id1, int id2) {
        System.out.println("These are the public objects:");
        printCardDetails(getCardById(id1));
        printCardDetails(getCardById(id2));
    }

    public JSONObject getCardById(int id) {
        JSONParser parser = new JSONParser();
        String[] filePaths = {
                "src/main/resources/Card.json",
                "src/main/resources/GoldCard.json",
                "src/main/resources/ObjectiveCard.json",
                "src/main/resources/StarterCard.json"
        };
        for (String filePath : filePaths) {
            try {
                JSONArray cards = (JSONArray) parser.parse(new FileReader(filePath));
                for (Object cardObj : cards) {
                    JSONObject card = (JSONObject) cardObj;
                    if (((Long) card.get("id")).intValue() == id) {
                        return card;
                    }
                }
            } catch (IOException | ParseException e) {
                System.err.println("Error reading or parsing the JSON file: " + e.getMessage());
            }
        }
        return null;
    }

    private String generateStandardCardDisplay(JSONObject card) {
        JSONObject side = (JSONObject) card.get("side");
        JSONArray front = (JSONArray) side.get("front");

        String topL = " ";
        String topR = " ";
        String bottomR = " ";
        String bottomL = " ";
        String resourceCenter = getResourceInitial((String) card.get("cardres"));
        String colorCenter = getResourceColor((String) card.get("cardres"));

        for (Object corner : front) {
            JSONObject cornerDetails = (JSONObject) corner;
            String position = (String) cornerDetails.get("Position");
            String property = (String) cornerDetails.get("PropertiesCorner");
            String color = getResourceColor(property);
            String initial = getResourceInitial(property);

            switch (position) {
                case "TOPL":
                    topL = color + initial + RESET_COLOR;
                    break;
                case "TOPR":
                    topR = color + initial + RESET_COLOR;
                    break;
                case "BOTTOMR":
                    bottomR = color + initial + RESET_COLOR;
                    break;
                case "BOTTOML":
                    bottomL = color + initial + RESET_COLOR;
                    break;
                default:
                    break;
            }
        }

        Long id = (Long) card.get("id");

        return
                "+ - - - - - - - - - - - - - - - - +\n" +
                        "| " + topL + "                             " + topR + " |\n" +
                        "|                                 |\n" +
                        "|               " + colorCenter + resourceCenter + RESET_COLOR + "                 |\n" +
                        "|                                 |\n" +
                        "| " + bottomL + "                             " + bottomR + " |\n" +
                        "+ - - - - - - - - - - - - - - - - +\n" +
                        "              ID: " + id + "\n";
    }

    private String getResourceColor(String resource) {
        return resourceColors.getOrDefault(resource, RESET_COLOR);
    }

    private String getResourceInitial(String resource) {
        return resourceInitials.getOrDefault(resource,"?");
    }

    public void printCardDetails(JSONObject card) {
        if (card == null) {
            System.out.println("Card not found.");
            return;
        }

        String type = (String) card.get("type");
        System.out.println("Type: " + type);

        if ("RESOURCES".equals(type) || "GOLD".equals(type)) {
            System.out.println(generateStandardCardDisplay(card));
        } else if ("STARTER".equals(type)) {
            System.out.println(generateStandardCardDisplay(card));
        } else if ("OBJECT".equals(type)) {
            System.out.println("Achievement:");
            System.out.println(card.get("description") + " for " + card.get("points") + " points");
            System.out.println("\n");
        }

        if ("RESOURCES".equals(type)) {
            System.out.println("Points: " + card.get("points"));
            System.out.println("Back Side is empty");
            System.out.println("\n");
        } else if ("GOLD".equals(type)) {
            String cardRes = (String) card.get("cardres");
            System.out.println("Card Resource: " + cardRes);
            System.out.println("Points: " + card.get("points") + " for every " + card.get("goldenPoint"));
            System.out.println("Requires:");
            JSONArray req = (JSONArray) card.get("requireGold");
            StringBuilder reqString = new StringBuilder();
            for (Object CardRes : req) {
                String resource = (String) CardRes;
                String color = getResourceColor(resource);
                String initial = getResourceInitial(resource);
                reqString.append(color).append(initial).append(RESET_COLOR).append("-");
            }
            if (reqString.length() > 0) {
                reqString.setLength(reqString.length() - 1); // Rimuove l'ultimo trattino
            }
            System.out.println(reqString.toString());
            System.out.println("Back Side is empty");
            System.out.println("\n");
        } else if ("STARTER".equals(type)) {
            System.out.println("Back side is empty by default.");
            System.out.println("\n");
        }
    }

    public void Interpreter(String message) {
        String[] parts = message.split(":");
        if (parts.length < 1) {
            System.out.println("Invalid command received.");
            return;
        }
        String command = parts[0];
        String[] parameters = parts.length > 1 ? parts[1].split(",") : new String[0];
        try {
            Method[] methods = this.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(command) && method.getParameterCount() == parameters.length) {
                    Class<?>[] paramTypes = method.getParameterTypes();
                    Object[] paramValues = new Object[parameters.length];
                    for (int i = 0; parameters != null && i < parameters.length; i++) {
                        if (paramTypes[i] == int.class) {
                            paramValues[i] = Integer.parseInt(parameters[i]);
                        } else if (paramTypes[i] == String.class) {
                            paramValues[i] = parameters[i];
                        }
                    }
                    method.invoke(this, paramValues);
                    return;
                }
            }
            System.out.println("No such method exists: " + command);
        } catch (Exception e) {
            System.out.println("Error executing command " + command + ": " + e.getMessage());
            e.printStackTrace();
        }
    }




}
