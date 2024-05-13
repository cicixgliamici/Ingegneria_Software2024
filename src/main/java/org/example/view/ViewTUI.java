package org.example.view;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class ViewTUI extends View{

    public ViewTUI() {

    }

    public void drawnCard(int id){
        System.out.println("You have drawn:");
        printCardDetails(getCardbyId(id));
    }
    public void hasDrawn(String username, int id){
        //todo ricostruire carta da id
        System.out.println("Player:" + username + "has drawn" );
        printCardDetails(getCardbyId(id));
    }
    public void playedCard(int id, int x, int y){
        //todo ricostruire carta da id
        System.out.println("You played card" + "at position:(" +x+ "," +y+")" );
    }
    public void hasPlayed(String username, int id){
        System.out.println("Player:" + username + "has played");
    }
    public void unplayable(int id, int x, int y){
        System.out.println("The card"+ "is unplayable at position:(" +x+ "," +y+")");
    }
    public void firstHand(int id1, int id2, int id3, int id4, int id5, int id6){
        System.out.println("In your hand:\n" );
        printCardDetails(getCardbyId(id1));
        printCardDetails(getCardbyId(id2));
        printCardDetails(getCardbyId(id3));
        System.out.println("\n Now please choose the side of the starter card");
        printCardDetails(getCardbyId(id4));
        System.out.println( "\n And what Objective Card you want to keep" );
        printCardDetails(getCardbyId(id5));
        printCardDetails(getCardbyId(id6));
    }

    public void pubObj(int id1, int id2){
        System.out.println("These are the public objects:"+
                            "\n"+ ".");
    }

    public void printView(){
        printHand();
        printPlayerCardArea();
    }

    public void printHand(){
        for (String string : Hand){
            System.out.println(string);
        }
    };

    public void printPlayerCardArea(){
        for (String string : PlayerCardArea){
            System.out.println(string);
        }
    }

    public void Interpreter(String message){

    }
    public JSONObject getCardbyId(int id){
        JSONParser parser = new JSONParser();
        String[] filePaths = {
                "Card.json",
                "GoldCard.json",
                "ObjectiveCard.json",
                "StarterCard.json"
        };
        for (String filePath : filePaths) {
            try {
                JSONArray cards = (JSONArray) parser.parse(new FileReader(filePath));
                for (Object cardObj : cards) {
                    org.json.JSONObject card = (org.json.JSONObject) cardObj;
                    if ((long) card.get("id") == id) {
                        return card;
                    }
                }
            } catch (Exception e) {
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
        System.out.println("Type: " + card.get("type"));
        System.out.println("Card Resource: " + card.get("cardres"));
        System.out.println("Points: " + card.get("points"));
        System.out.println("Position: " + card.get("cardposition"));
        JSONObject side = (JSONObject) card.get("side");
        System.out.println("Side: " + side.get("side"));
        JSONArray front = (JSONArray) side.get("front");
        System.out.println("Front Side Details:");
        for (Object corner : front) {
            JSONObject cornerDetails = (JSONObject) corner;
            System.out.println("Position: " + cornerDetails.get("Position") + ", Property: " + cornerDetails.get("PropertiesCorner"));
        }
    }
}
