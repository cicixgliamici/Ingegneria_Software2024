package org.example.view.gui.gamearea4;

import org.example.client.TCPClient;
import org.example.view.gui.listener.EvListener;
import org.example.view.gui.listener.Event;
import org.example.view.gui.utilities.ImageCard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The PlayCardArea class represents the area where cards are played in the game.
 * It handles the display and placement of cards, as well as interaction events.
 */
public class PlayCardArea extends JPanel {

    private final static int COSTX = 720; // Base X coordinate for card placement
    private final static int COSTY = 747; // Base Y coordinate for card placement
    private final static int SCALEX = 125; // X scale factor for card placement
    private final static int SCALEY = 64;  // Y scale factor for card placement
    private double scale = 1.0; // Scale factor for the panel
    private String pathImageInsert; // Path for the image to be inserted
    private List<ImageCard> cardPlaced = new ArrayList<>(); // List of placed cards
    private TCPClient tcpClient; // TCP client for communication
    private GameAreaPanel gameAreaPanel; // Reference to the game area panel

    /**
     * Constructs a PlayCardArea with the specified TCP client and game area panel.
     *
     * @param tcpClient The TCP client for communication.
     * @param gameAreaPanel The game area panel.
     */
    public PlayCardArea(TCPClient tcpClient, GameAreaPanel gameAreaPanel) {
        this.tcpClient = tcpClient;
        this.gameAreaPanel = gameAreaPanel;
        setLayout(null);
        setPreferredSize(new Dimension(1600, 1600));
    }

    /**
     * Inserts a starter card at the specified position.
     *
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @param pathImage The path to the card image.
     * @param nx The grid X coordinate.
     * @param ny The grid Y coordinate.
     */
    public void insertCardStarter(int x, int y, String pathImage, int nx, int ny) {
        // Ensure the path starts with "/" if it's meant to be absolute from the classpath root
        ImageCard imageCard = new ImageCard(pathImage, x, y, nx, ny);
        addCardButtons(imageCard);
        cardPlaced.add(imageCard);
    }

    /**
     * Inserts a card at the specified position.
     *
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @param pathImage The path to the card image.
     * @param nx The grid X coordinate.
     * @param ny The grid Y coordinate.
     */
    public void insertCard(int x, int y, String pathImage, int nx, int ny) {
        ImageCard imageCard = new ImageCard(pathImage, x, y, nx, ny);
        addCardButtons(imageCard);
        deactivateButton(nx, ny);
        cardPlaced.add(imageCard);
    }

    /**
     * Adds interactive buttons to the card for handling events.
     *
     * @param imageCard The image card to add buttons to.
     */
    private void addCardButtons(ImageCard imageCard) {
        this.add(imageCard.getCornerButtonBottomDx());
        this.add(imageCard.getCornerButtonHighDx());
        this.add(imageCard.getCornerButtonBottomSx());
        this.add(imageCard.getCornerButtonHighSx());

        imageCard.setEvListener(new EvListener() {
            @Override
            public void eventListener(Event ev) throws IOException {
                handleEvent(ev, imageCard);
            }
        });
    }

    /**
     * Deactivates buttons at the specified grid coordinates.
     *
     * @param nx The grid X coordinate.
     * @param ny The grid Y coordinate.
     */
    private void deactivateButton(int nx, int ny) {
        findAndRemoveButton(nx, ny);
    }

    /**
     * Finds and removes buttons at the specified grid coordinates.
     *
     * @param nx The grid X coordinate.
     * @param ny The grid Y coordinate.
     */
    private void findAndRemoveButton(int nx, int ny) {
        for (ImageCard imageCard : cardPlaced) {
            if (imageCard.getCornerButtonHighDx().matchesCoordinates(nx, ny)) {
                this.remove(imageCard.getCornerButtonHighDx());
            }
            if (imageCard.getCornerButtonBottomDx().matchesCoordinates(nx, ny)) {
                this.remove(imageCard.getCornerButtonBottomDx());
            }
            if (imageCard.getCornerButtonHighSx().matchesCoordinates(nx, ny)) {
                this.remove(imageCard.getCornerButtonHighSx());
            }
            if (imageCard.getCornerButtonBottomSx().matchesCoordinates(nx, ny)) {
                this.remove(imageCard.getCornerButtonBottomSx());
            }
        }
    }

    /**
     * Handles the event triggered by interacting with a card.
     *
     * @param ev The event triggered.
     * @param imageCard The image card involved in the event.
     * @throws IOException If an I/O error occurs during event handling.
     */
    private void handleEvent(Event ev, ImageCard imageCard) throws IOException {
        int x, y, nx = 0, ny = 0;
        System.out.println("id card: " + imageCard.getId());

        switch (ev.getEvent()) {
            case "addFromHighDx":
                nx = imageCard.getCornerButtonHighDx().getNx();
                ny = imageCard.getCornerButtonHighDx().getNy();
                break;
            case "addFromBottomDx":
                nx = imageCard.getCornerButtonBottomDx().getNx();
                ny = imageCard.getCornerButtonBottomDx().getNy();
                break;
            case "addFromHighSx":
                nx = imageCard.getCornerButtonHighSx().getNx();
                ny = imageCard.getCornerButtonHighSx().getNy();
                break;
            case "addFromBottomSx":
                nx = imageCard.getCornerButtonBottomSx().getNx();
                ny = imageCard.getCornerButtonBottomSx().getNy();
                break;
        }

        x = COSTX + nx * SCALEX;
        y = COSTY - ny * SCALEY;
        sendPlayRequest(imageCard.getId(), gameAreaPanel.getChosenSide(), nx, ny);
        repaint();
    }

    /**
     * Sends a play request to the server.
     *
     * @param id The card ID.
     * @param side The side of the card.
     * @param x The X coordinate.
     * @param y The Y coordinate.
     */
    public void sendPlayRequest(int id, int side, int x, int y) {
        try {
            for (ImageCard card : cardPlaced) {
                System.out.println("card: " + card.getId() + " HDX / Nx:" + card.getCornerButtonHighDx().getNx() + ";" + card.getCornerButtonHighDx().getNy());
                System.out.println("card: " + card.getId() + " HSX / Nx:" + card.getCornerButtonHighSx().getNx() + ";" + card.getCornerButtonHighSx().getNy());
                System.out.println("card: " + card.getId() + " BDX / Nx:" + card.getCornerButtonBottomDx().getNx() + ";" + card.getCornerButtonBottomDx().getNy());
                System.out.println("card: " + card.getId() + " BSX / Nx:" + card.getCornerButtonBottomSx().getNx() + ";" + card.getCornerButtonBottomSx().getNy());
                System.out.println(" ");
            }
            System.out.println("tcp: " + gameAreaPanel.getChosenId() + " " + side + " " + x + " " + y);
            tcpClient.sendPlay(gameAreaPanel.getChosenId(), gameAreaPanel.getChosenSide(), x, y);
        } catch (Exception e) {
            System.out.println("Error during sendPlay: " + e.getMessage());
        }
    }

    /**
     * Plays a card at the specified position.
     *
     * @param id The card ID.
     * @param side The side of the card.
     * @param x The X coordinate.
     * @param y The Y coordinate.
     */
    protected void playCard(int id, int side, int x, int y) {
        ImageCard imageCard = new ImageCard(getPathImageInsert(), COSTX + x * SCALEX, COSTY - y * SCALEY, x, y);
        addCardButtons(imageCard);
        cardPlaced.add(imageCard);
        deactivateButton(x, y);
        repaint();
    }

    /**
     * Removes a card from the play area.
     *
     * @param imageCard The image card to remove.
     */
    private void removeCard(ImageCard imageCard) {
        this.remove(imageCard.getCornerButtonHighDx());
        this.remove(imageCard.getCornerButtonBottomDx());
        this.remove(imageCard.getCornerButtonHighSx());
        this.remove(imageCard.getCornerButtonBottomSx());
        cardPlaced.remove(imageCard);
        repaint();
    }

    /**
     * Paints the component, including all placed cards.
     *
     * @param g The Graphics context.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(scale, scale);

        for (ImageCard imageCard : cardPlaced) {
            g2d.drawImage(imageCard.getImage(), imageCard.getX(), imageCard.getY(), this);
        }
    }

    /**
     * Gets the preferred size of the component.
     *
     * @return The preferred size.
     */
//    @Override
//    public Dimension getPreferredSize() {
//        int width = (int) (1600 * scale);
//        int height = (int) (1600 * scale);
//        return new Dimension(width, height);
//    }

    /**
     * Gets the list of placed cards.
     *
     * @return The list of placed cards.
     */
    public List<ImageCard> getCardPlaced() {
        return cardPlaced;
    }

    /**
     * Sets the list of placed cards.
     *
     * @param cardPlaced The list of placed cards.
     */
    public void setCardPlaced(List<ImageCard> cardPlaced) {
        this.cardPlaced = cardPlaced;
    }

    /**
     * Gets the path of the image to be inserted.
     *
     * @return The path of the image to be inserted.
     */
    public String getPathImageInsert() {
        return pathImageInsert;
    }

    /**
     * Sets the path of the image to be inserted.
     *
     * @param pathImageInsert The path of the image to be inserted.
     */
    public void setPathImageInsert(String pathImageInsert) {
        this.pathImageInsert = pathImageInsert;
    }
}
