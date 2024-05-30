package org.example.view.gui.gamearea4;

import org.example.view.gui.listener.EvListener;
import org.example.view.gui.listener.Event;
import org.example.view.gui.utilities.ImageCard;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class PlayCardArea extends JPanel {

    private List<ImageCard> cardPlaced = new ArrayList<>();
    private double scale = 1.0;
    private final static int COSTX = 320;
    private final static int COSTY = 347;
    private final static int SCALEX = 125;
    private final static int SCALEY = 64;
    private String pathImageInsert;

    public PlayCardArea() {
        setLayout(null);
        setPreferredSize(new Dimension(800,800));

       /* addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.isControlDown()) {
                    int notches = e.getWheelRotation();
                    if (notches < 0) {
                        scale += 0.1;
                    } else {
                        scale -= 0.1;
                        if (scale < 0.1) {
                            scale = 0.1;
                        }
                    }
                    revalidate();
                    repaint();
                }
            }
        }); */
    }

    public void insertCardStarter(int x, int y, String pathImage, int nx, int ny){
        ImageCard imageCard = new ImageCard(pathImage, x, y, nx, ny);
        this.add(imageCard.getCornerButtonBottomDx());
        this.add(imageCard.getCornerButtonHighDx());
        this.add(imageCard.getCornerButtonBottomSx());
        this.add(imageCard.getCornerButtonHighSx());
        imageCard.setEvListener(new EvListener() {
            @Override
            public void eventListener(Event ev) throws IOException {
                if(ev.getEvent().equals("addFromHighDx")){
                    int corX = COSTX + imageCard.getCornerButtonHighDx().getNx() * SCALEX;
                    int corY = COSTY - imageCard.getCornerButtonHighDx().getNy() * SCALEY;
                    int insertNx = imageCard.getCornerButtonHighDx().getNx();
                    int insertNy = imageCard.getCornerButtonHighDx().getNy();
                    insertCardHighDx(corX, corY, pathImageInsert, insertNx, insertNy);
                    repaint();
                }
                else if(ev.getEvent().equals("addFromBottomDx")){
                    int corX = COSTX + imageCard.getCornerButtonBottomDx().getNx() * SCALEX;
                    int corY = COSTY - imageCard.getCornerButtonBottomDx().getNy() * SCALEY;
                    int insertNx = imageCard.getCornerButtonBottomDx().getNx();
                    int insertNy = imageCard.getCornerButtonBottomDx().getNy();
                    insertCardBottomDx(corX, corY, pathImageInsert, insertNx, insertNy);
                    repaint();
                }
                else if(ev.getEvent().equals("addFromHighSx")) {
                    int corX = COSTX + imageCard.getCornerButtonHighSx().getNx() * SCALEX;
                    int corY = COSTY - imageCard.getCornerButtonHighSx().getNy() * SCALEY;
                    int insertNx = imageCard.getCornerButtonHighSx().getNx();
                    int insertNy = imageCard.getCornerButtonHighSx().getNy();
                    insertCardHighSx(corX, corY, pathImageInsert, insertNx, insertNy);
                    repaint();
                }
                else if(ev.getEvent().equals("addFromBottomSx")) {
                    int corX = COSTX + imageCard.getCornerButtonBottomSx().getNx() * SCALEX;
                    int corY = COSTY - imageCard.getCornerButtonBottomSx().getNy() * SCALEY;
                    int insertNx = imageCard.getCornerButtonBottomSx().getNx();
                    int insertNy = imageCard.getCornerButtonBottomSx().getNy();
                    insertCardBottomSx(corX, corY, pathImageInsert, insertNx, insertNy);
                    repaint();
                }
            }
        });
        cardPlaced.add(imageCard);
    };

    public void insertCardHighDx(int x, int y, String pathImage, int nx, int ny){
        ImageCard imageCard = new ImageCard(pathImage, x, y, nx, ny);
        this.add(imageCard.getCornerButtonHighDx());
        this.add(imageCard.getCornerButtonHighSx());
        this.add(imageCard.getCornerButtonBottomDx());
        imageCard.setEvListener(new EvListener() {
            @Override
            public void eventListener(Event ev) throws IOException {
                if(ev.getEvent().equals("addFromHighDx")){
                    int corX = COSTX + imageCard.getCornerButtonHighDx().getNx() * SCALEX;
                    int corY = COSTY - imageCard.getCornerButtonHighDx().getNy() * SCALEY;
                    int insertNx = imageCard.getCornerButtonHighDx().getNx();
                    int insertNy = imageCard.getCornerButtonHighDx().getNy();
                    insertCardHighDx(corX, corY, pathImageInsert, insertNx, insertNy);
                    repaint();
                }
                else if(ev.getEvent().equals("addFromBottomDx")){
                    int corX = COSTX + imageCard.getCornerButtonBottomDx().getNx() * SCALEX;
                    int corY = COSTY - imageCard.getCornerButtonBottomDx().getNy() * SCALEY;
                    int insertNx = imageCard.getCornerButtonBottomDx().getNx();
                    int insertNy = imageCard.getCornerButtonBottomDx().getNy();
                    insertCardBottomDx(corX, corY, pathImageInsert, insertNx, insertNy);
                    repaint();
                }
                else if(ev.getEvent().equals("addFromBottomSx")) {
                    int corX = COSTX + imageCard.getCornerButtonBottomSx().getNx() * SCALEX;
                    int corY = COSTY - imageCard.getCornerButtonBottomSx().getNy() * SCALEY;
                    int insertNx = imageCard.getCornerButtonBottomSx().getNx();
                    int insertNy = imageCard.getCornerButtonBottomSx().getNy();
                    insertCardBottomSx(corX, corY, pathImageInsert, insertNx, insertNy);
                    repaint();
                }
            }
        });
        cardPlaced.add(imageCard);
    }

    public void insertCardBottomDx(int x, int y, String pathImage, int nx, int ny){
        ImageCard imageCard = new ImageCard(pathImage, x, y, nx, ny);
        this.add(imageCard.getCornerButtonHighDx());
        this.add(imageCard.getCornerButtonBottomDx());
        this.add(imageCard.getCornerButtonBottomSx());
        imageCard.setEvListener(new EvListener() {
            @Override
            public void eventListener(Event ev) throws IOException {
                if(ev.getEvent().equals("addFromHighDx")){
                    int corX = COSTX + imageCard.getCornerButtonHighDx().getNx() * SCALEX;
                    int corY = COSTY - imageCard.getCornerButtonHighDx().getNy() * SCALEY;
                    int insertNx = imageCard.getCornerButtonHighDx().getNx();
                    int insertNy = imageCard.getCornerButtonHighDx().getNy();
                    insertCardHighDx(corX, corY, pathImageInsert, insertNx, insertNy);
                    repaint();
                }
                else if(ev.getEvent().equals("addFromBottomDx")){
                    int corX = COSTX + imageCard.getCornerButtonBottomDx().getNx() * SCALEX;
                    int corY = COSTY - imageCard.getCornerButtonBottomDx().getNy() * SCALEY;
                    int insertNx = imageCard.getCornerButtonBottomDx().getNx();
                    int insertNy = imageCard.getCornerButtonBottomDx().getNy();
                    insertCardBottomDx(corX, corY, pathImageInsert, insertNx, insertNy);
                    repaint();
                }
                else if(ev.getEvent().equals("addFromBottomSx")) {
                    int corX = COSTX + imageCard.getCornerButtonBottomSx().getNx() * SCALEX;
                    int corY = COSTY - imageCard.getCornerButtonBottomSx().getNy() * SCALEY;
                    int insertNx = imageCard.getCornerButtonBottomSx().getNx();
                    int insertNy = imageCard.getCornerButtonBottomSx().getNy();
                    insertCardBottomSx(corX, corY, pathImageInsert, insertNx, insertNy);
                    repaint();
                }
            }
        });
        cardPlaced.add(imageCard);
    }

    public void insertCardHighSx(int x, int y, String pathImage, int nx, int ny){
        ImageCard imageCard = new ImageCard(pathImage, x, y, nx, ny);
        this.add(imageCard.getCornerButtonBottomSx());
        this.add(imageCard.getCornerButtonHighSx());
        this.add(imageCard.getCornerButtonHighDx());
        imageCard.setEvListener(new EvListener() {
            @Override
            public void eventListener(Event ev) throws IOException {
                if(ev.getEvent().equals("addFromHighDx")){
                    int corX = COSTX + imageCard.getCornerButtonHighDx().getNx() * SCALEX;
                    int corY = COSTY - imageCard.getCornerButtonHighDx().getNy() * SCALEY;
                    int insertNx = imageCard.getCornerButtonHighDx().getNx();
                    int insertNy = imageCard.getCornerButtonHighDx().getNy();
                    insertCardHighDx(corX, corY, pathImageInsert, insertNx, insertNy);
                    repaint();
                }
                else if(ev.getEvent().equals("addFromHighSx")) {
                    int corX = COSTX + imageCard.getCornerButtonHighSx().getNx() * SCALEX;
                    int corY = COSTY - imageCard.getCornerButtonHighSx().getNy() * SCALEY;
                    int insertNx = imageCard.getCornerButtonHighSx().getNx();
                    int insertNy = imageCard.getCornerButtonHighSx().getNy();
                    insertCardHighSx(corX, corY, pathImageInsert, insertNx, insertNy);
                    repaint();
                }
                else if(ev.getEvent().equals("addFromBottomSx")) {
                    int corX = COSTX + imageCard.getCornerButtonBottomSx().getNx() * SCALEX;
                    int corY = COSTY - imageCard.getCornerButtonBottomSx().getNy() * SCALEY;
                    int insertNx = imageCard.getCornerButtonBottomSx().getNx();
                    int insertNy = imageCard.getCornerButtonBottomSx().getNy();
                    insertCardBottomSx(corX, corY, pathImageInsert, insertNx, insertNy);
                    repaint();
                }
            }
        });
        cardPlaced.add(imageCard);
    }

    public void insertCardBottomSx(int x, int y, String pathImage, int nx, int ny){
        ImageCard imageCard = new ImageCard(pathImage, x, y, nx, ny);
        this.add(imageCard.getCornerButtonHighSx());
        this.add(imageCard.getCornerButtonBottomSx());
        this.add(imageCard.getCornerButtonBottomDx());
        imageCard.setEvListener(new EvListener() {
            @Override
            public void eventListener(Event ev) throws IOException {
                if(ev.getEvent().equals("addFromBottomDx")){
                    int corX = COSTX + imageCard.getCornerButtonBottomDx().getNx() * SCALEX;
                    int corY = COSTY - imageCard.getCornerButtonBottomDx().getNy() * SCALEY;
                    int insertNx = imageCard.getCornerButtonBottomDx().getNx();
                    int insertNy = imageCard.getCornerButtonBottomDx().getNy();
                    insertCardBottomDx(corX, corY, pathImageInsert, insertNx, insertNy);
                    repaint();
                }
                else if(ev.getEvent().equals("addFromHighSx")) {
                    int corX = COSTX + imageCard.getCornerButtonHighSx().getNx() * SCALEX;
                    int corY = COSTY - imageCard.getCornerButtonHighSx().getNy() * SCALEY;
                    int insertNx = imageCard.getCornerButtonHighSx().getNx();
                    int insertNy = imageCard.getCornerButtonHighSx().getNy();
                    insertCardHighSx(corX, corY, pathImageInsert, insertNx, insertNy);
                    repaint();
                }
                else if(ev.getEvent().equals("addFromBottomSx")) {
                    int corX = COSTX + imageCard.getCornerButtonBottomSx().getNx() * SCALEX;
                    int corY = COSTY - imageCard.getCornerButtonBottomSx().getNy() * SCALEY;
                    int insertNx = imageCard.getCornerButtonBottomSx().getNx();
                    int insertNy = imageCard.getCornerButtonBottomSx().getNy();
                    insertCardBottomSx(corX, corY, pathImageInsert, insertNx, insertNy);
                    repaint();
                }
            }
        });
        cardPlaced.add(imageCard);
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