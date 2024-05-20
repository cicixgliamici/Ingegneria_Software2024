package org.example.view;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ViewTUI extends View {

    private static final HashMap<String, String> resourceColors = new HashMap<>();
    private static final HashMap<String, String> resourceInitials = new HashMap<>();

    static {
        resourceColors.put("FUNGI", "\u001B[31m"); // Rosso
        resourceColors.put("PLANT", "\u001B[32m"); // Verde
        resourceColors.put("ANIMAL", "\u001B[36m"); // Celeste
        resourceColors.put("INSECT", "\u001B[35m"); // Viola

        resourceInitials.put("FUNGI", "F");
        resourceInitials.put("PLANT", "P");
        resourceInitials.put("ANIMAL", "A");
        resourceInitials.put("INSECT", "I");
    }

    private static final String RESET_COLOR = "\u001B[0m";

    public ViewTUI() {
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
                System.out.println("If is your turn, now write play:choice,side,x,y");
                System.out.println("Where choice is the number of the card of your hand (1-3)");
                System.out.println("If you want to draw you need to draw:x (1-5)");
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

    public void playedCard(int id, int x, int y) {
        System.out.println("You played card at position: (" + x + "," + y + ")");
        printCardDetails(getCardById(id));
    }

    public void hasPlayed(String username, int id) {
        System.out.println("Player: " + username + " has played");
        printCardDetails(getCardById(id));
    }

    public void unplayable(int id, int x, int y) {
        System.out.println("The card is unplayable at position: (" + x + "," + y + ")");
        printCardDetails(getCardById(id));
    }

    public void firstHand(int id1, int id2, int id3, int id4, int id5, int id6) {
        System.out.println("In your hand:\n");
        printCardDetails(getCardById(id1));
        printCardDetails(getCardById(id2));
        printCardDetails(getCardById(id3));
        System.out.println("Now please choose the side of the starter card:");
        printCardDetails(getCardById(id4));
        System.out.println("And what Objective Card you want to keep:");
        printCardDetails(getCardById(id5));
        printCardDetails(getCardById(id6));
        System.out.println("You should write 'setObjStarter:x,y', where " +
                "\nx is for the side of the starter card and " +
                "\ny is the objective card you want to keep ");
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

    private String getResourceColor(String resource) {
        return resourceColors.getOrDefault(resource, RESET_COLOR);
    }

    private String getResourceInitial(String resource) {
        return resourceInitials.getOrDefault(resource, "?");
    }

    public void printCardDetails(JSONObject card) {
        if (card == null) {
            System.out.println("Card not found.");
            return;
        }

        String type = (String) card.get("type");
        System.out.println("Type: " + type);

        String cardRes = (String) card.get("cardres");
        String color = getResourceColor(cardRes);
        String initial = getResourceInitial(cardRes);
        Long id = (Long) card.get("id");

        // Print card in a rectangle with resource initials at the corners and center
        System.out.println(color);
        System.out.println("  +--------+  ");
        System.out.println("  | " + initial + "      " + initial + " |  ");
        System.out.println("  |        |  ");
        System.out.println("  |    " + initial + "   |  ");
        System.out.println("  |        |  ");
        System.out.println("  | " + initial + "      " + initial + " |  ");
        System.out.println("  +--------+  ");
        System.out.println("     ID: " + id);
        System.out.println(RESET_COLOR);

        // Additional details for different card types
        if ("RESOURCES".equals(type)) {
            System.out.println("Card Resource: " + cardRes);
            System.out.println("Points: " + card.get("points"));
            JSONObject side = (JSONObject) card.get("side");
            JSONArray front = (JSONArray) side.get("front");
            if (front != null) {
                System.out.println("Front Side Details:");
                for (Object corner : front) {
                    JSONObject cornerDetails = (JSONObject) corner;
                    System.out.println("Position: " + cornerDetails.get("Position") + ", Property: " + cornerDetails.get("PropertiesCorner"));
                }
                System.out.println("Back Side is empty");
            } else {
                System.out.println("No front side details available.");
            }
            System.out.println("\n");
        } else if ("GOLD".equals(type)) {
            System.out.println("Card Resource: " + cardRes);
            System.out.println("Points: " + card.get("points") + " for every " + card.get("goldenPoint"));
            JSONArray req = (JSONArray) card.get("requireGold");
            for (Object CardRes : req) {
                System.out.println(CardRes);
            }
            JSONObject side = (JSONObject) card.get("side");
            JSONArray front = (JSONArray) side.get("front");
            if (front != null) {
                System.out.println("Front Side Details:");
                for (Object corner : front) {
                    JSONObject cornerDetails = (JSONObject) corner;
                    System.out.println("Position: " + cornerDetails.get("Position") + ", Property: " + cornerDetails.get("PropertiesCorner"));
                }
                System.out.println("Back Side is empty");
            } else {
                System.out.println("No front side details available.");
            }
            System.out.println("\n");
        } else if ("STARTER".equals(type)) {
            JSONObject side = (JSONObject) card.get("side");
            JSONArray back = (JSONArray) side.get("back");
            System.out.println("Front Side is empty");
            if (back != null && !back.isEmpty()) {
                System.out.println("Back Side Details:");
                for (Object corner : back) {
                    JSONObject cornerDetails = (JSONObject) corner;
                    System.out.println("Position: " + cornerDetails.get("Position") + ", Property: " + cornerDetails.get("PropertiesCorner"));
                }
            } else {
                System.out.println("Back side is empty by default.");
            }
            System.out.println("\n");
        } else if ("OBJECT".equals(type)) {
            System.out.println("Achievement:");
            System.out.println(card.get("description") + " for " + card.get("points") + " points");
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
                    for (int i = 0; i < parameters.length; i++) {
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
