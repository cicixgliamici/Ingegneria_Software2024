package org.example.view;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

public class ViewTUI extends View {

    public ViewTUI() {
    }

    public void drawnCard(int id) {
        System.out.println("You have drawn:");
        printCardDetails(getCardbyId(id));
    }

    public void hasDrawn(String username, int id) {
        System.out.println("Player: " + username + " has drawn");
        printCardDetails(getCardbyId(id));
    }

    public void playedCard(int id, int x, int y) {
        System.out.println("You played card at position: (" + x + "," + y + ")");
        printCardDetails(getCardbyId(id));
    }

    public void hasPlayed(String username, int id) {
        System.out.println("Player: " + username + " has played");
        printCardDetails(getCardbyId(id));
    }

    public void unplayable(int id, int x, int y) {
        System.out.println("The card is unplayable at position: (" + x + "," + y + ")");
        printCardDetails(getCardbyId(id));
    }

    public void firstHand(int id1, int id2, int id3, int id4, int id5, int id6) {
        System.out.println("In your hand:");
        printCardDetails(getCardbyId(id1));
        printCardDetails(getCardbyId(id2));
        printCardDetails(getCardbyId(id3));
        System.out.println("Now please choose the side of the starter card:");
        printCardDetails(getCardbyId(id4));
        System.out.println("And what Objective Card you want to keep:");
        printCardDetails(getCardbyId(id5));
        printCardDetails(getCardbyId(id6));
    }

    public void pubObj(int id1, int id2) {
        System.out.println("These are the public objects:");
    }

    public JSONObject getCardbyId(int id) {
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

    public void printCardDetails(JSONObject card) {
        if (card == null) {
            System.out.println("Card not found.");
            return;
        }
        String type = (String) card.get("type");
        System.out.println("Type: " + type);
        if ("RESOURCES".equals(type)){
            System.out.println("Card Resource: " + card.get("cardres"));
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
        }
        else if ("GOLD".equals(type)){
            System.out.println("Card Resource: " + card.get("cardres"));
            System.out.println("Points: " + card.get("points") + " for every " + card.get("goldenPoint"));
            JSONArray req = (JSONArray) card.get("requireGold");
            for (Object CardRes : req ){
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
        }
        else if ("STARTER".equals(type)) {
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
            System.out.println(card.get("description"));
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
