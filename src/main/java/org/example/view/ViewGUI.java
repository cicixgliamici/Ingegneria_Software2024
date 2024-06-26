package org.example.view;

import org.example.view.gui.gamearea4.Chat;
import org.example.view.gui.listener.EvListener;
import org.example.view.gui.listener.Event;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Concrete class extending View to provide a GUI-based implementation of the view component.
 */
public class ViewGUI extends View {

    /**
     * Constructor initializing various lists and flags for the GUI view.
     */
    public ViewGUI() {
        this.isFirst = false;
        this.matchStarted = false;
        this.flag = 1;
        cardsPath = new ArrayList<>();
        cardsId = new ArrayList<>();
        drawableCards = new ArrayList<>();
        colors = new ArrayList<>();

        map = new HashMap<>();
        points = new HashMap<>();
    }

    @Override
    public void updatePlayerCardArea(int id) {
        // This method is currently not implemented for GUI.
    }

    @Override
    public void removePlayerCardArea(int id) {
        // This method is currently not implemented for GUI.
    }

    @Override
    public void printPlayerCardArea() {
        // This method is currently not implemented for GUI.
    }

    @Override
    public void printHand() {
        // This method is currently not implemented for GUI.
    }

    @Override
    public void printGrid() {
        // This method is currently not implemented for GUI.
    }

    @Override
    public void hasDrawn(String username, int id) {
        // This method is currently not implemented for GUI.
    }

    @Override
    public void hasPlayed(String username, int id) {
        // This method is currently not implemented for GUI.
    }

    @Override
    public void setHand(int side, int choice) {
        // This method is currently not implemented for GUI.
    }

    @Override
    public void updateSetupUI(String[] colors, boolean isFirst) {
        // This method is currently not implemented for GUI.
    }

    /**
     * Handles a new player connection and associates the player with a color.
     *
     * @param player The username of the new player.
     * @param color  The color chosen by the new player.
     */
    @Override
    public void newConnection(String player, String color) {
        colorPlayer.put(player, color);
        System.out.println("newConnection GUI: " + colorPlayer);
    }

    /**
     * Gets the image path for a card based on its dimensions and side.
     *
     * @param card The card JSON object.
     * @param dim  The dimension of the card (1 for big, 2 for small, 3 for medium).
     * @param side The side of the card (1 for front, 2 for back).
     * @return The image path as a string.
     */
    public String getImagePath(JSONObject card, int dim, int side) {
        if (card == null) {
            return null;
        }
        switch (dim) {
            case 1:
                if (side == 1) {
                    return (String) card.get("imagePathBF");
                } else if (side == 2) {
                    return (String) card.get("imagePathBB");
                }
            case 2:
                if (side == 1) {
                    return (String) card.get("imagePathSF");
                } else if (side == 2) {
                    return (String) card.get("imagePathSB");
                }
            case 3:
                if (side == 1) {
                    return (String) card.get("imagePathMF");
                } else if (side == 2) {
                    return (String) card.get("imagePathMB");
                }
            default:
                return null;
        }
    }

    /**
     * Loads an image from a given resource path.
     *
     * @param imagePath The path to the image resource.
     * @return The loaded Image object, or null if loading failed.
     */
    public Image loadImage(String imagePath) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(imagePath)) {
            if (is != null) {
                return ImageIO.read(is);
            } else {
                System.err.println("Resource not found: " + imagePath);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets an image by card ID, side, and dimension.
     *
     * @param id   The card ID.
     * @param side The side of the card (1 for front, 2 for back).
     * @param dim  The dimension of the card (1 for big, 2 for small, 3 for medium).
     * @return The Image object representing the card.
     */
    public Image getImageById(int id, int side, int dim) {
        String imagePath = getImagePath(getCardById(id), side, dim);
        if (imagePath != null) {
            return loadImage(imagePath);
        }
        return null;
    }

    /**
     * Adds a drawn card to the player's hand and notifies listeners.
     *
     * @param id The ID of the drawn card.
     */
    @Override
    public void drawnCard(int id) {
        Hand.add(id);
        notifyListeners(new Event(this, "handUpdated"));
    }

    /**
     * Handles the placement of a played card on the game board.
     *
     * @param id    The ID of the played card.
     * @param side  The side of the card (1 for front, 2 for back).
     * @param x     The x-coordinate on the grid.
     * @param y     The y-coordinate on the grid.
     */
    @Override
    public void playedCard(int id, int side, int x, int y) {
        turn = 1;
        validPlay = 1;
        removeHand(id);
        System.out.println("Turn and VP in GUI: " + turn + ", " + validPlay);
        System.out.println("playedCard in GUI");
        notifyListeners(new Event(this, "playUpdated", id, side, x, y)); // Event that updates the game board
    }

    /**
     * Adds an event listener to the list of listeners.
     *
     * @param listener The event listener to add.
     */
    @Override
    public void addListener(EvListener listener) {
        listeners.add(listener);
    }

    /**
     * Displays a message indicating that a card cannot be played due to insufficient resources.
     *
     * @param id The ID of the unplayable card.
     * @param x  The x-coordinate of the attempted placement.
     * @param y  The y-coordinate of the attempted placement.
     */
    @Override
    public void unplayable(int id, int x, int y) {
        System.out.println("unplayable in GUI");
        JOptionPane.showMessageDialog(null, "You don't have enough resources for the " + id + " card", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays a message indicating that a card cannot be placed at the specified position.
     *
     * @param id The ID of the card.
     * @param x  The x-coordinate of the attempted placement.
     * @param y  The y-coordinate of the attempted placement.
     */
    @Override
    public void placeholder(int id, int x, int y) {
        System.out.println("placeholder in GUI");
        validPlay = 0;
        JOptionPane.showMessageDialog(null, "Card " + id + " cannot be placed at " + x + ", " + y, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Processes the initial set of cards received by the player.
     *
     * @param id1 Card ID 1.
     * @param id2 Card ID 2.
     * @param id3 Card ID 3.
     * @param id4 Card ID 4.
     * @param id5 Card ID 5.
     * @param id6 Card ID 6.
     */
    @Override
    public void firstHand(int id1, int id2, int id3, int id4, int id5, int id6) {
        showStarterObjective(id4, id5, id6);
        updateHand(id1);
        updateHand(id2);
        updateHand(id3);
        System.out.println("Hand from FirstHand GUI: " + getHand());
    }

    /**
     * Displays the starter objectives for the player.
     *
     * @param id4 The ID of the first objective card.
     * @param id5 The ID of the second objective card.
     * @param id6 The ID of the third objective card.
     */
    public void showStarterObjective(int id4, int id5, int id6) {
        cardsPath.clear();
        cardsId.clear();
        cardsId.add(id4);
        cardsId.add(id5);
        cardsId.add(id6);
        cardsPath.add(getImagePath(getCardById(id4), 3, 1));
        cardsPath.add(getImagePath(getCardById(id4), 3, 2));
        cardsPath.add(getImagePath(getCardById(id5), 3, 1));
        cardsPath.add(getImagePath(getCardById(id6), 3, 1));
    }

    /**
     * Adds the public objective cards to the list.
     *
     * @param id1 The ID of the first public objective card.
     * @param id2 The ID of the second public objective card.
     */
    @Override
    public void pubObj(int id1, int id2) {
        PublicObjectives.add(id1);
        PublicObjectives.add(id2);
    }

    /**
     * Sets the order of players and their corresponding colors.
     *
     * @param us1 Username of the first player.
     * @param us2 Color of the first player.
     * @param us3 Username of the second player.
     * @param us4 Color of the second player.
     * @param us5 Username of the third player.
     * @param us6 Color of the third player.
     * @param us7 Username of the fourth player.
     * @param us8 Color of the fourth player.
     */
    @Override
    public void order(String us1, String us2, String us3, String us4, String us5, String us6, String us7, String us8) {
        List<String> orderPlayer = Arrays.asList(us1, us3, us5, us7);
        firstPlayer=us1;
        List<String> orderColor = Arrays.asList(us2, us4, us6, us8);
        isFirst = true;
        for (int i = 0; i < orderPlayer.size(); i++) {
            if (!"null".equals(orderPlayer.get(i))) {
                colorPlayer.put(orderPlayer.get(i), orderColor.get(i));
            }
        }
        System.out.println("from order GUI: " + colorPlayer);
    }


    /**
     * Displays a message indicating that the last round has started.
     */
    @Override
    public void lastRound() {
        JOptionPane.showMessageDialog(null, "last round has started", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays a message indicating the winner of the game.
     *
     * @param string The message to display, typically the winner's name.
     */
    @Override
    public void Winner(String string) {

        JOptionPane.showMessageDialog(null, string, "Info", JOptionPane.INFORMATION_MESSAGE);
        Event event = new Event(this, "winner");
        if (evListener != null) {
            try {
                evListener.eventListener(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Displays a message indicating that the game ended in a tie.
     *
     * @param string The message to display, typically indicating a tie.
     */
    @Override
    public void Tie(String string) {
        JOptionPane.showMessageDialog(null, string, "Info", JOptionPane.INFORMATION_MESSAGE);
        Event event = new Event(this, "winner");
        if (evListener != null) {
            try {
                evListener.eventListener(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Updates the points of a player.
     *
     * @param username The username of the player.
     * @param point    The points to assign to the player.
     */
    @Override
    public void points(String username, int point) {
        System.out.println("from points GUI: " + username + ", " + point);
        points.put(username, point);
    }


    /**
     * Displays various messages to the user based on the provided message code.
     * @param x The message code determining the behavior.
     */
    @Override
    public void message(int x) {
        switch (x) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                turn = 0;
                //System.out.println("Turn in GUI: " + turn + " should pop JPANEL");
                JOptionPane.showMessageDialog(null, "Not your turn", "Info", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                JOptionPane.showMessageDialog(null, "username already in use, please reconnect with different name", "Info", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 8:
                break;
            case 9:
                matchStarted = false;
                System.out.println(matchStarted);
                break;
            case 10:
                matchStarted = true;
                System.out.println(matchStarted);
                break;
            case 12:
                JOptionPane.showMessageDialog(null, "The match has ended", "Info", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 13:
                JOptionPane.showMessageDialog(null, "Is your turn, make your move", "Info", JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                System.out.println("Unknown message code." + x);
                break;
        }
    }

    /**
     * Sets the available colors for players.
     *
     * @param c1 The first color.
     * @param c2 The second color.
     * @param c3 The third color.
     * @param c4 The fourth color.
     */
    @Override
    public void color(String c1, String c2, String c3, String c4) {
        List<String> inputColors = Arrays.asList(c1, c2, c3, c4);
        isFirst = true;
        for (String color : inputColors) {
            if (!"null".equals(color)) {
                colors.add(color);
            } else {
                isFirst = false;
            }
        }
    }

    /**
     * Sets the current player as the first player.
     */
    @Override
    public void setFirst() {
        isFirst = true;
    }


    /**
     * Sets the maximum number of connections for the game.
     *
     * @param maxCon The maximum number of players.
     */
    @Override
    public void numCon(int maxCon) {
        numConnection = maxCon;
        System.out.println("Received the maximum number of players: " + numConnection);
    }

    /**
     * Updates the visible area with drawable cards.
     *
     * @param id1 ID of the first card.
     * @param id2 ID of the second card.
     * @param id3 ID of the third card.
     * @param id4 ID of the fourth card.
     * @param id5 ID of the fifth card.
     * @param id6 ID of the sixth card.
     */
    @Override
    public void visibleArea(int id1, int id2, int id3, int id4, int id5, int id6) {
        drawableCards.clear();
        drawableCards.add(id1);
        drawableCards.add(id2);
        drawableCards.add(id3);
        drawableCards.add(id4);
        drawableCards.add(id5);
        drawableCards.add(id6);
        System.out.println("from visibleArea GUI: " + drawableCards);
        notifyListeners(new Event(this, "visibleArea"));
    }

    /**
     * Displays a chat message from a user.
     *
     * @param username The username of the message sender.
     * @param message  The message content.
     */
    @Override
    public void chatC(String username, String message) {
        Chat.displayMessage(username, message);
    }

    public void close(){JOptionPane.showMessageDialog(null, "Server crashed", "Info", JOptionPane.INFORMATION_MESSAGE);}
}
