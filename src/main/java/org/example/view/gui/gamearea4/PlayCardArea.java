package org.example.view.gui.gamearea4;

import org.example.client.TCPClient;
import org.example.view.gui.listener.EvListener;
import org.example.view.gui.listener.Event;
import org.example.view.gui.utilities.ImageCard;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayCardArea extends JPanel {

    private final static int COSTX = 320;
    private final static int COSTY = 347;
    private final static int SCALEX = 125;
    private final static int SCALEY = 64;
    private double scale = 1.0;
    private String pathImageInsert;
    private List<ImageCard> cardPlaced = new ArrayList<>();
    private TCPClient tcpClient;
    private GameAreaPanel gameAreaPanel;

    public PlayCardArea(TCPClient tcpClient, GameAreaPanel gameAreaPanel) {
        this.tcpClient = tcpClient;
        this.gameAreaPanel = gameAreaPanel;
        setLayout(null);
        setPreferredSize(new Dimension(800, 800));
    }

    public void insertCardStarter(int x, int y, String pathImage, int nx, int ny) {
        ImageCard imageCard = new ImageCard(pathImage, x, y, nx, ny);
        addCardButtons(imageCard);
        cardPlaced.add(imageCard);
    }

    public void insertCard(int x, int y, String pathImage, int nx, int ny) {
        ImageCard imageCard = new ImageCard(pathImage, x, y, nx, ny);
        addCardButtons(imageCard);
        deactivateButton(nx, ny);
        cardPlaced.add(imageCard);
    }

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

    private void deactivateButton(int nx, int ny) {
        findAndRemoveButton(nx + 1, ny + 1);
        findAndRemoveButton(nx + 1, ny - 1);
        findAndRemoveButton(nx - 1, ny + 1);
        findAndRemoveButton(nx - 1, ny - 1);
    }

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

    private void handleEvent(Event ev, ImageCard imageCard) throws IOException {
        int x, y, nx, ny;
        System.out.println("id card: " + imageCard.getId());
        switch (ev.getEvent()) {
            case "addFromHighDx":
                x = COSTX + imageCard.getCornerButtonHighDx().getNx() * SCALEX;
                y = COSTY - imageCard.getCornerButtonHighDx().getNy() * SCALEY;
                nx = imageCard.getCornerButtonHighDx().getNx();
                ny = imageCard.getCornerButtonHighDx().getNy();
                sendPlayRequest(imageCard.getId(), gameAreaPanel.getChosenSide(), nx, ny);
                deactivateButton(nx, ny);
                break;
            case "addFromBottomDx":
                x = COSTX + imageCard.getCornerButtonBottomDx().getNx() * SCALEX;
                y = COSTY - imageCard.getCornerButtonBottomDx().getNy() * SCALEY;
                nx = imageCard.getCornerButtonBottomDx().getNx();
                ny = imageCard.getCornerButtonBottomDx().getNy();
                sendPlayRequest(imageCard.getId(), gameAreaPanel.getChosenSide(), nx, ny);
                deactivateButton(nx, ny);
                break;
            case "addFromHighSx":
                x = COSTX + imageCard.getCornerButtonHighSx().getNx() * SCALEX;
                y = COSTY - imageCard.getCornerButtonHighSx().getNy() * SCALEY;
                nx = imageCard.getCornerButtonHighSx().getNx();
                ny = imageCard.getCornerButtonHighSx().getNy();
                sendPlayRequest(imageCard.getId(), gameAreaPanel.getChosenSide(), nx, ny);
                deactivateButton(nx, ny);
                break;
            case "addFromBottomSx":
                x = COSTX + imageCard.getCornerButtonBottomSx().getNx() * SCALEX;
                y = COSTY - imageCard.getCornerButtonBottomSx().getNy() * SCALEY;
                nx = imageCard.getCornerButtonBottomSx().getNx();
                ny = imageCard.getCornerButtonBottomSx().getNy();
                sendPlayRequest(imageCard.getId(), gameAreaPanel.getChosenSide(), nx, ny);
                deactivateButton(nx, ny);
                break;
        }
        repaint();
    }

    public void sendPlayRequest(int id, int side, int x, int y) {
        try {
            for (ImageCard card: cardPlaced) {
                System.out.println("card: " + card.getId() + " HDX / Nx:" + card.getCornerButtonHighDx().getNx());
                // CARD.getID da sempre zero, il vero id è sempre gameAreaPanel.getChosenId, che sia questo il problema?
                System.out.println("card: " + card.getId() + " HDX / Ny:" + card.getCornerButtonHighDx().getNy());
                System.out.println("card: " + card.getId() + " HSX / Nx:" + card.getCornerButtonHighSx().getNx());
                System.out.println("card: " + card.getId() + " HSX / Ny:" + card.getCornerButtonHighSx().getNy());
                System.out.println("card: " + card.getId() + " BDX / Nx:" + card.getCornerButtonBottomDx().getNx());
                System.out.println("card: " + card.getId() + " BDX / Ny:" + card.getCornerButtonBottomDx().getNy());
                System.out.println("card: " + card.getId() + " BSX / Nx:" + card.getCornerButtonBottomSx().getNx());
                System.out.println("card: " + card.getId() + " BSX / Ny:" + card.getCornerButtonBottomSx().getNy());
            }
            System.out.println("tcp: " + gameAreaPanel.getChosenId() + " " + side + " " + x + " " + y);
            tcpClient.sendPlay(gameAreaPanel.getChosenId(), gameAreaPanel.getChosenSide(), x, y);
        } catch (Exception e) {
            System.out.println("Error during sendPlay: " + e.getMessage());
        }
    }

    protected void playCard(int id, int side, int x, int y) {
        ImageCard imageCard = new ImageCard(getPathImageInsert(), COSTX + x * SCALEX, COSTY - y * SCALEY, x, y);
        addCardButtons(imageCard);
        deactivateButton(x, y);
        cardPlaced.add(imageCard);
        repaint();
    }

    private void removeCard(ImageCard imageCard) {
        this.remove(imageCard.getCornerButtonHighDx());
        this.remove(imageCard.getCornerButtonBottomDx());
        this.remove(imageCard.getCornerButtonHighSx());
        this.remove(imageCard.getCornerButtonBottomSx());
        cardPlaced.remove(imageCard);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(scale, scale);
        for (ImageCard imageCard : cardPlaced) {
            g2d.drawImage(imageCard.getImage(), imageCard.getX(), imageCard.getY(), this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int width = (int) (800 * scale);
        int height = (int) (800 * scale);
        return new Dimension(width, height);
    }

    public List<ImageCard> getCardPlaced() {
        return cardPlaced;
    }

    public void setCardPlaced(List<ImageCard> cardPlaced) {
        this.cardPlaced = cardPlaced;
    }

    public String getPathImageInsert() {
        return pathImageInsert;
    }

    public void setPathImageInsert(String pathImageInsert) {
        this.pathImageInsert = pathImageInsert;
    }
}
