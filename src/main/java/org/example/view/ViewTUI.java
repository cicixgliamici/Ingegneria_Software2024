package org.example.view;

import org.example.view.gui.utilities.Coordinates;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Text User Interface (TUI) version of the game view.
 * This class handles displaying the game state and interacting with the user via the console.
 */
public class ViewTUI extends View {


    private boolean matchStarted;

    // Map to associate resource names with their ANSI color codes for colored console output.
    private static final HashMap<String, String> resourceColors = new HashMap<>();
    private static final HashMap<String, String> resourceInitials = new HashMap<>();

    static {
        // Initialize color and initial mappings for resources.
        resourceColors.put("FUNGI", "\u001B[31m"); // Red
        resourceColors.put("PLANT", "\u001B[32m"); // Green
        resourceColors.put("ANIMAL", "\u001B[36m"); // Cyan
        resourceColors.put("INSECT", "\u001B[35m"); // Purple
        resourceColors.put("EMPTY", "\u001B[34m"); // Blue
        resourceColors.put("HIDDEN", "\u001B[33m"); // Yellow
        resourceColors.put("INKWELL", "\u001B[33m"); // Gold
        resourceColors.put("QUILL", "\u001B[33m"); // Gold
        resourceColors.put("MANUSCRIPT", "\u001B[33m"); // Gold
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

    private static final String RESET_COLOR = "\u001B[0m"; // ANSI reset code to clear previous coloring.

    public ViewTUI() {
        // Initialize the grid in the constructor of the superclass.
        super();
        this.flag=0;
    }

    /**
     * Handles message outputs based on a message code input.
     * @param x The message code determining output behavior.
     */
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
                break;
            case 4:
                System.out.println("Not your turn!");
                break;
            case 5:
                System.out.println("draw:x (0-5)");
                System.out.println("0: Deck Res - 1: Deck Gold - 2/3: Res from Vis. - 4/5: Gold from Vis.");
                break;
            case 6:
                System.out.println("play:choice,side,x,y");
                System.out.println("Where choice is the number of the card of your hand (1-3), side (1-2), and x-y are the coordinates");
                break;
            case 7:
                System.out.println("Username already taken. Please reconnect with a different username.");
                break;
            case 8:
                System.out.println("Connection successful");
                break;
            case 9:
                System.out.println("Waiting for other players");
                break;
            case 10:
                System.out.println("Match Started");
                break;
            default:
                System.out.println("Unknown message code.");
                break;
        }
    }

    @Override
    public void updateSetupUI(String[] colors, boolean isFirst) {

    }

    /**
     * Processes actions to take when a new card is drawn.
     * @param id The card identifier.
     */
    public void drawnCard(int id) {
        System.out.println("You have drawn:");
        printCardDetailsFormatted(getCardById(id));
        Hand.add(id);
        System.out.println("Now your hand is: ");
        printHand();
    }

    /**
     * Prints the details of all cards currently in the player's hand.
     */
    public void printHand() {
        for (Integer id:Hand){
            printCardDetails(getCardById(id));
        }
    }
    public void printGrid() {
        System.out.println("Current grid state:");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                boolean found = false;
                for (Map.Entry<Integer, Coordinates> entry : map.entrySet()) {
                    Coordinates coordinates = entry.getValue();
                    int gridX = M - coordinates.getY();
                    int gridY = coordinates.getX() + M;
                    if (gridX == i && gridY == j) {
                        int id = entry.getKey();
                        System.out.printf(" %4d ", id);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.print("   .  ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Outputs information when another player has drawn a card.
     * @param username The player's username.
     * @param id The card identifier.
     */
    public void hasDrawn(String username, int id) {
        System.out.println("Player: " + username + " has drawn");
        printCardDetailsFormatted(getCardById(id));
    }

    /**
     * Outputs information when another player has played a card.
     * @param username The player's username.
     * @param id The card identifier.
     */
    public void hasPlayed(String username, int id) {
        System.out.println("Player: " + username + " has played");
        printCardDetailsFormatted(getCardById(id));
    }

    /**
     * Handles the placement of a card on the game grid by a player.
     * @param id The card identifier.
     * @param x The x-coordinate on the grid.
     * @param y The y-coordinate on the grid.
     */
    @Override
    public void playedCard(int id, int x, int y) {
        PlayerCardArea.add(id);
        removeHand(id);
        super.addMapping(id,x,y);
        printGrid();
        System.out.println("\n");
        printPlayerCardArea();
        message(5);
    }

    /**
     * Prints all cards that have been played by the player in a specific area.
     */
    public void printPlayerCardArea() {
        for(Integer id: PlayerCardArea){
            printCardDetailsFormatted(getCardById(id));
        }
    }

    /**
     * Handles the scenario where a card cannot be played at the specified grid position.
     * @param id The card identifier.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    @Override
    public void unplayable(int id, int x, int y) {
        System.out.println("The card is unplayable at position: (" + x + ", " + y + ") because you need:");
        JSONObject card = getCardById(id);
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
            reqString.setLength(reqString.length() - 1); // Remove the last hyphen
        }
        System.out.println(reqString.toString());
        System.out.println("\n");
    }

    @Override
    public void placeholder(int id, int x, int y) {
        System.out.println("The card is unplayable at position: (" + x + ", " + y + ")");
        printCardDetailsFormatted(getCardById(id));
        printGrid();
    }


    /**
     * Processes the initial set of cards received by the player.
     * @param id1 Card ID 1.
     * @param id2 Card ID 2.
     * @param id3 Card ID 3.
     * @param id4 Card ID 4.
     * @param id5 Card ID 5.
     * @param id6 Card ID 6.
     */
    public void firstHand(int id1, int id2, int id3, int id4, int id5, int id6) {
        System.out.println("In your hand:\n");
        Hand.add(id1);
        Hand.add(id2);
        Hand.add(id3);
        printHand();
        System.out.println("Now please choose the side of the starter card:");
        PlayerCardArea.add(id4);
        printCardDetails(getCardById(id4));
        super.addMapping(id4,0,0);
        System.out.println("And what Objective Card you want to keep:");
        printCardDetails(getCardById(id5));
        printCardDetails(getCardById(id6));
        System.out.println("You should write 'setObjStarter:x,y', where " +
                "\nx is for the side of the starter card and " +
                "\ny is the objective card you want to keep.");
    }

    public void visibleArea(int id1, int id2, int id3, int id4, int id5, int id6){
        System.out.println("Drawable:\n");
        printCardDetailsFormatted(getCardById(id1));
        printCardDetailsFormatted(getCardById(id2));
        printCardDetailsFormatted(getCardById(id3));
        printCardDetailsFormatted(getCardById(id4));
        printCardDetailsFormatted(getCardById(id5));
        printCardDetailsFormatted(getCardById(id6));
    }

    @Override
    public void pubObj(int id1, int id2) {

    }

    @Override
    public void setHand(int side, int choice) {

    }

    /**
     * Sends a list of player usernames to the client to indicate player turn order.
     * It checks each username and prints only non-null usernames.
     * @param us1 Username of the first player or null
     * @param us2 Username of the second player or null
     * @param us3 Username of the third player or null
     * @param us4 Username of the fourth player or null
     */
    public void order(String us1, String us2, String us3, String us4) {
        List<String> users = Arrays.asList(us1, us2, us3, us4);
        StringBuilder orderBuilder = new StringBuilder("Player order: ");
        boolean isFirst = true; // Flag to handle commas correctly.
        for (String user : users) {
            if (!user.equals("null")) { // Check if the username is not "null".
                if (!isFirst) {
                    orderBuilder.append(", ");
                }
                orderBuilder.append(user);
                isFirst = false;
            }
        }
        // Print the constructed order if at least one username was added.
        if (!isFirst) {
            System.out.println(orderBuilder.toString());
        } else {
            System.out.println("No players are currently connected.");
        }
    }

    /**
     * Adds non-null colors to a formatted string of available colors.
     */
    @Override
    public void color(String c1, String c2, String c3, String c4) {
        List<String> colors = Arrays.asList(c1, c2, c3, c4); // Create a list of the input colors
        StringBuilder colorBuilder = new StringBuilder("Colors available:");
        for (String color : colors) {
            if (!color.equals("null")) { // Check if the color is not "null".
                colorBuilder.append(", ");
                colorBuilder.append(color);
            }
            // Print the constructed order if at least one color was added.
        }
        System.out.println(colorBuilder.toString());
    }

    public void setFirst(){
        System.out.println("Enter the maximum number of players (1-4):");
    }

    /**
     * Displays the current points for each player, ensuring to only include non-null usernames.
     * @param us1 Username and points of the first player or null
     * @param us2 Username and points of the second player or null
     * @param us3 Username and points of the third player or null
     * @param us4 Username and points of the fourth player or null
     */
    public void points(String us1, int points1, String us2, int points2, String us3, int points3, String us4, int points4) {
        List<String> users = Arrays.asList(us1, us2, us3, us4);
        List<Integer> points = Arrays.asList(points1, points2, points3, points4);
        StringBuilder pointsBuilder = new StringBuilder("Player points: ");
        boolean isFirst = true; // Flag to handle commas correctly.

        for (int i = 0; i < users.size(); i++) {
            String user = users.get(i);
            int point = points.get(i);
            if (!user.equals("null")) { // Check if the username is not "null".
                if (!isFirst) {
                    pointsBuilder.append(", ");
                }
                pointsBuilder.append(user).append(": ").append(point);
                isFirst = false;
            }
        }
        // Print the constructed points if at least one username was added.
        if (!isFirst) {
            System.out.println(pointsBuilder.toString());
        } else {
            System.out.println("No players have points.");
        }
    }

    /**
     * Generates a string representation of a standard card for display in the console, showing resource positions and colors.
     * @param card The card as a JSONObject.
     * @param sides The number of sides to display (1 for front, 2 for back).
     * @return A string representing the card's display.
     */
    private String generateStandardCardDisplay(JSONObject card, int sides) {
        JSONObject side = (JSONObject) card.get("side");
        JSONArray front = null;
        if (sides == 1) {
            front = (JSONArray) side.get("front");
        } else if (sides == 2) {
            front = (JSONArray) side.get("back");
        }
        if (front == null) {
            return "Invalid side configuration";
        }
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
        String type = (String) card.get("type");
        if ("STARTER".equals(type)) {
            return
                    "+ - - - - - - - - - - - - - - - - +\n" +
                            "| " + topL + "                             " + topR + " |\n" +
                            "|                                 |\n" +
                            "|                                 |\n" +
                            "|                                 |\n" +
                            "| " + bottomL + "                             " + bottomR + " |\n" +
                            "+ - - - - - - - - - - - - - - - - +\n";
        }
        else {
            return
                    "+ - - - - - - - - - - - - - - - - +\n" +
                            "| " + topL + "                             " + topR + " |\n" +
                            "|                                 |\n" +
                            "|               " + colorCenter + resourceCenter + RESET_COLOR + "                 |\n" +
                            "|                                 |\n" +
                            "| " + bottomL + "                             " + bottomR + " |\n" +
                            "+ - - - - - - - - - - - - - - - - +\n";
        }
    }


    /**
     * Retrieves the ANSI color code associated with a specific resource.
     * @param resource The resource name.
     * @return The ANSI color code as a string.
     */
    private String getResourceColor(String resource) {
        return resourceColors.getOrDefault(resource, RESET_COLOR);
    }

    /**
     * Retrieves the initial character of a resource for display purposes.
     * @param resource The resource name.
     * @return The initial character of the resource.
     */
    private String getResourceInitial(String resource) {
        return resourceInitials.getOrDefault(resource, "?");
    }

    /**
     * Prints detailed information about a card based on its JSON representation.
     * @param card The JSONObject representing the card.
     */
    public void printCardDetails(JSONObject card) {
        if (card == null) {
            System.out.println("Card not found.");
            return;
        }
        String type = (String) card.get("type");
        System.out.println("Type: " + type);
        if ("RESOURCES".equals(type) || "GOLD".equals(type)) {
            System.out.println(generateStandardCardDisplay(card, 1));
            System.out.println(generateStandardCardDisplay(card, 2));
            System.out.println("ID:" + card.get("id"));
        } else if ("STARTER".equals(type)) {
            System.out.println(generateStandardCardDisplay(card, 1));
            System.out.println(generateStandardCardDisplay(card, 2));
            System.out.println("ID:" + card.get("id"));
        } else if ("OBJECT".equals(type)) {
            System.out.println("Achievement:");
            System.out.println(card.get("description") + " for " + card.get("points") + " points");
            System.out.println("\n");
        }
        if ("RESOURCES".equals(type)) {
            System.out.println("Points: " + card.get("points"));
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
                reqString.setLength(reqString.length() - 1); // Remove the last hyphen
            }
            System.out.println(reqString.toString());
            System.out.println("\n");
        } else if ("STARTER".equals(type)) {
            System.out.println("Permanent Resources on the front:");
            JSONArray req = (JSONArray) card.get("requireGold");
            StringBuilder reqString = new StringBuilder();
            for (Object CardRes : req) {
                String resource = (String) CardRes;
                String color = getResourceColor(resource);
                String initial = getResourceInitial(resource);
                reqString.append(color).append(initial).append(RESET_COLOR).append("-");
            }
            if (reqString.length() > 0) {
                reqString.setLength(reqString.length() - 1); // Remove the last hyphen
            }
            System.out.println(reqString.toString());
            System.out.println("\n");
        }
    }

    public void printCardDetailsFormatted(JSONObject card) {
        // Provides detailed formatted output of a card including type, ID, front and back details, and handles special cases for GOLD and STARTER cards.
        if (card == null) {
            System.out.println("Card not found.");
            return;
        }
        String type = (String) card.get("type");
        Long id = (Long) card.get("id");
        JSONObject side = (JSONObject) card.get("side");
        JSONArray front = (JSONArray) side.get("front");
        JSONArray back = (JSONArray) side.get("back");
        // Define the variables for resource initials and colors for both front and back
        String[] cornersFront = new String[]{" ", " ", " ", " "};
        String[] cornersBack = new String[]{" ", " ", " ", " "};
        setCornerDetails(front, cornersFront);
        setCornerDetails(back, cornersBack);
        // Printing the basic card details for front and back
        System.out.printf("Type: %s    ID: %d\n", type, id);
        System.out.printf("Front: TL: %s, TR: %s, BR: %s, BL: %s\n",
                cornersFront[0], cornersFront[1], cornersFront[2], cornersFront[3]);
        System.out.printf("Back: TL: %s, TR: %s, BR: %s, BL: %s\n",
                cornersBack[0], cornersBack[1], cornersBack[2], cornersBack[3]);
        // Additional details for GOLD and STARTER cards
        if ("GOLD".equals(type)) {
            printGoldCardDetails(card);
        } else if ("STARTER".equals(type)) {
            printStarterCardDetails(card);
        }
        System.out.println("\n");
    }

    private void setCornerDetails(JSONArray corners, String[] cornerDetails) {
        // Helper method to set corner details for both front and back of the card based on the JSON data.
        for (Object corner : corners) {
            JSONObject cornerDetail = (JSONObject) corner;
            String position = (String) cornerDetail.get("Position");
            String property = (String) cornerDetail.get("PropertiesCorner");
            String color = getResourceColor(property);
            String initial = getResourceInitial(property);

            switch (position) {
                case "TOPL":
                    cornerDetails[0] = color + initial + RESET_COLOR;
                    break;
                case "TOPR":
                    cornerDetails[1] = color + initial + RESET_COLOR;
                    break;
                case "BOTTOMR":
                    cornerDetails[2] = color + initial + RESET_COLOR;
                    break;
                case "BOTTOML":
                    cornerDetails[3] = color + initial + RESET_COLOR;
                    break;
            }
        }
    }

    private void printGoldCardDetails(JSONObject card) {
        // Prints additional details for gold cards, including points and required resources.
        int points = ((Long) card.get("points")).intValue();
        JSONArray requireGold = (JSONArray) card.get("requireGold");

        // Prepare to output the points and required resources with correct formatting.
        System.out.printf("Points: %d    RequireGold: ", points);
        for (Object resource : requireGold) {
            String res = (String) resource;
            String color = getResourceColor(res);
            String initial = getResourceInitial(res);
            // Print each resource directly with its color coding, followed by a reset.
            System.out.print(color + initial + RESET_COLOR + " ");
        }
        System.out.println(); // Move to the next line after listing all resources.
    }



    private void printStarterCardDetails(JSONObject card) {
        // Prints details specific to starter cards, highlighting permanent resources.
        JSONArray requireGold = (JSONArray) card.get("requireGold");
        StringBuilder reqString = new StringBuilder("Permanent res: ");
        for (Object resource : requireGold) {
            String res = (String) resource;
            String color = getResourceColor(res);
            String initial = getResourceInitial(res);
            reqString.append(color).append(initial).append(RESET_COLOR).append(" ");
        }
        System.out.println(reqString.toString().trim());
    }

    public void numCon(int maxCon){
        System.out.println("The game will start with " + maxCon + " players");
    }

    @Override
    public void chatC(String username, String message) {

    }

}