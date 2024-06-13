package org.example.view;

import org.example.view.gui.gamearea4.Chat;
import org.example.view.gui.gamearea4.DrawingCardPanel;
import org.example.view.gui.listener.DrawingCardPanelListener;
import org.example.view.gui.listener.GameAreaPanelListener;
import org.example.view.gui.listener.EvListener;
import org.example.view.gui.listener.Event;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
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
    }

    @Override
    public void removePlayerCardArea(int id) {
    }

    @Override
    public void printPlayerCardArea() {
    }

    @Override
    public void printHand() {
    }

    @Override
    public void printGrid() {
    }

    @Override
    public void hasDrawn(String username, int id) {
    }

    @Override
    public void hasPlayed(String username, int id) {
    }

    @Override
    public void setHand(int side, int choice) {
    }

    @Override
    public void updateSetupUI(String[] colors, boolean isFirst) {
    }

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
     * Loads an image from a given file path.
     *
     * @param imagePath The path to the image file.
     * @return The loaded Image object, or null if loading failed.
     */
    public Image loadImage(String imagePath) {
        try {
            return ImageIO.read(new File(imagePath));
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

    @Override
    public void drawnCard(int id) {
        Hand.add(id);
        notifyListeners(new Event(this, "handUpdated"));
    }

    @Override
    public void playedCard(int id, int side, int x, int y) {
        turn = 1;
        validPlay = 1;
        removeHand(id);
        System.out.println("Turn e VP in GUI: " + turn + ", " + validPlay);
        System.out.println("playedCard in GUI");
        notifyListeners(new Event(this, "playUpdated", id, side, x, y)); // Evento che aggiorna il tabellone di gioco
    }

    @Override
    public void addListener(EvListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unplayable(int id, int x, int y) {
        System.out.println("unplayable in GUI");
        JOptionPane.showMessageDialog(null, "You don't have enough resources for the " + id + " card", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void placeholder(int id, int x, int y) {
        System.out.println("placeholder in GUI");
        validPlay = 0;
        JOptionPane.showMessageDialog(null, "Card " + id + " can not be placed at " + x + ", " + y, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void firstHand(int id1, int id2, int id3, int id4, int id5, int id6) {
        showStarterObjective(id4, id5, id6);
        updateHand(id1);
        updateHand(id2);
        updateHand(id3);
        System.out.println("hand from FirstHand GUI: " + getHand());
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

    @Override
    public void pubObj(int id1, int id2) {
        PublicObjectives.add(id1);
        PublicObjectives.add(id2);
    }

    @Override
    public void order(String us1, String us2, String us3, String us4, String us5, String us6, String us7, String us8) {
        List<String> orderPlayer = Arrays.asList(us1, us3, us5, us7);
        List<String> orderColor = Arrays.asList(us2, us4, us6, us8);
        isFirst = true;
        for (int i = 0; i < orderPlayer.size(); i++) {
            if (!"null".equals(orderPlayer.get(i))) {
                colorPlayer.put(orderPlayer.get(i), orderColor.get(i));
            }
        }
        System.out.println("from order GUI: " + colorPlayer);
    }

    @Override
    public void points(String username, int point) {
        System.out.println("from points GUI: " + username + ", " + point);
        points.put(username, point);
    }

    @Override
    public void message(int x) {
        switch (x) {
            case 4:
                turn = 0;
                System.out.println("Turn in GUI: " + turn + " should pop JPANEL");
                JOptionPane.showMessageDialog(null, "Not your turn", "Info", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 9:
                matchStarted = false;
                System.out.println(matchStarted);
                break;
            case 10:
                matchStarted = true;
                System.out.println(matchStarted);
                break;
        }
    }

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

    @Override
    public void setFirst() {
        isFirst = true;
    }

    @Override
    public void numCon(int maxCon) {
        numConnection = maxCon;
        System.out.println("ricevuto il num max di player " + numConnection);
    }

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

    @Override
    public void chatC(String username, String message) {
        Chat.displayMessage(username, message);
    }
}
