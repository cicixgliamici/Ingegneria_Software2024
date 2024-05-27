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

    @Override
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
                org.json.simple.JSONArray cards = (JSONArray) parser.parse(new FileReader(filePath));
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
    public String getImagePathById(int id) {
        JSONObject card = getCardById(id);
        if (card != null) {
            return (String) card.get("imagePath");
        }
        return null;
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
    public Image getImageById(int id) {
        String imagePath = getImagePathById(id);
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
        // Mostrare dopo le prime 3 carte
        // Stampare in quadrato (TL-BL) Front starter, Back Starter, 1° Obj, 2° Obj
        // al click equivale int (1-2)
        // "setObjStarter:" + int + "," + int
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
        for (String color : inputColors) {
            if (!"null".equals(color)) {
                colors.add(color);
            }
        }
    }


    public void setFirst() {
        isFirst = true;
    }
    public void numCon(int maxCon){
        numConnection=maxCon;
    }
}