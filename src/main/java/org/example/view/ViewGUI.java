package org.example.view;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewGUI extends View {
    public ViewGUI() {
        this.flag=1;
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
    public void playCardInGrid(int x, int y, int cardId) {

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
    public void Interpreter(String message) {

    }

    @Override
    public JSONObject getCardById(int id) {
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

    }

    @Override
    public void updateSetupUI(String[] colors, boolean isFirst) {

    }

    /**
     * Adds non-null colors to a list of available colors.
     */
    @Override
    public void color(String c1, String c2, String c3, String c4) {
        // Create a list from the input parameters
        List<String> colors = Arrays.asList(c1, c2, c3, c4);
        // Initialize a new ArrayList to store the valid colors
        List<String> colorList = new ArrayList<>();
        // Iterate over each color in the list and add to the list if it is not "null"
        for (String color : colors) {
            if (!"null".equals(color)) {
                colorList.add(color);
            }
        }
    }

    public void setPlayers() {

    }



}