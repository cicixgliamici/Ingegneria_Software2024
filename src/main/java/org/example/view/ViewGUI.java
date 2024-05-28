package org.example.view;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewGUI extends View {


    public ViewGUI() {
        cardsPath=new ArrayList<>();
        this.isFirst=false;
        this.flag=1;
        this.matchStarted=false;
        colors=new ArrayList<>();
    }

    @Override
    public void updateHand(int id) {

    }

    @Override
    public void updatePlayerCardArea(int id) {

    }

    @Override
    public void removeHand(int id) {

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

    /**
     *
     * @param card
     * @param dim is for big (1), small(2) or mid(3)
     * @param side is for front(1) or back(2)
     * @return
     */
    public String getImagePath(JSONObject card, int dim, int side) {
        if (card == null) {
            return null;
        }
        switch(dim) {
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


    //metodo che ritorna una grafica da una stringa (il percorso dell'immagine)
    public Image loadImage(String imagePath) {
        try {
            return ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    //metodo che ritorna una grafica direttamente da un id
    public Image getImageById(int id, int side, int dim) {
        String imagePath = getImagePath(getCardById(id), side, dim);
        if (imagePath != null) {
            return loadImage(imagePath);
        }
        return null;
    }


    @Override
    public void drawnCard(int id) {

    }

    @Override
    public void hasDrawn(String username, int id) {

    }

    @Override
    public void playedCard(int id, int x, int y) {

    }

    @Override
    public void hasPlayed(String username, int id) {

    }

    @Override
    public void unplayable(int id, int x, int y) {

    }

    @Override
    public void placeholder(int id, int x, int y) {

    }


    @Override
    public void firstHand(int id1, int id2, int id3, int id4, int id5, int id6) {
        // showStarterObjective(id4, id5, id6);
        cardsPath.clear();

    }

    public void showStarterObjective(int id4, int id5, int id6){
        cardsPath.clear();
        cardsPath.add(getImagePath(getCardById(id4), 3, 1));
        cardsPath.add(getImagePath(getCardById(id4), 3, 2));
        cardsPath.add(getImagePath(getCardById(id5), 3, 1));
        cardsPath.add(getImagePath(getCardById(id6), 3, 1));
    }

    @Override
    public void setHand(int side, int choice) {

    }

    @Override
    public void pubObj(int id1, int id2) {

    }

    @Override
    public void order(String us1, String us2, String us3, String us4) {

    }

    @Override
    public void points(String us1, int p1, String us2, int p2, String us3, int p3, String us4, int p4) {

    }



    @Override
    public void message(int x) {
        switch (x) {
            case 9:
                matchStarted=false;
                System.out.println(matchStarted);
                break;
            case 10:
                matchStarted=true;
                System.out.println(matchStarted);
                break;
        }

    }

    @Override
    public void updateSetupUI(String[] colors, boolean isFirst) {

    }

    /**
     * Adds non-null colors to a list of available colors.
     */
    @Override
    public void color(String c1, String c2, String c3, String c4) {
        List<String> inputColors = Arrays.asList(c1, c2, c3, c4);
        isFirst=true;
        for (String color : inputColors) {
            if (!"null".equals(color)) {
                colors.add(color);
            } else
                isFirst=false;
        }
        System.out.println("in view: Colors: " + colors);
    }


    public void setFirst() {
        isFirst = true;
    }
    public void numCon(int maxCon){
        numConnection=maxCon;
        System.out.println("ricevuto il num max di player " + numConnection);
    }
}