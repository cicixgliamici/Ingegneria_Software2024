package org.example.model.deck;
import org.example.model.deck.enumeration.*;

import java.text.ParseException;
import java.util.ArrayList;


import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.io.FileReader;
import java.io.IOException;
import java.util.List;


/**
 * Class Deck that get data from the JSON file
 *
 */

public class Deck {
    
    private Type typeDeck;
    private int CardNumbers;

     public Deck(Type typeDeck) throws IOException, ParseException {
        this.typeDeck = typeDeck;
        switch (typeDeck) {
            case RESOURCES:
                this.CardNumbers = 40;
                try {

                    JSONParser parser = new JSONParser();
                    JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/resources/Card.json"));

                    // Crea una lista per memorizzare le carte
                    List<Card> cards = new ArrayList<>();

                    for (Object obj : jsonArray) {
                        JSONObject card = (JSONObject) obj;

                        // Leggi i campi dell'oggetto JSON
                        Type type = Type.valueOf((String) card.get("type"));
                        CardRes cardres = CardRes.valueOf((String) card.get("cardres"));
                        JSONArray requireGoldArray = (JSONArray) card.get("requireGold");
                        CardRes[] requireGold = new CardRes[requireGoldArray.size()];
                        for (int j = 0; j < requireGoldArray.size(); j++) {
                            requireGold[j] = CardRes.valueOf((String) requireGoldArray.get(j));
                        }
                        int points = Integer.parseInt(card.get("points").toString());
                        CardPosition cardposition = CardPosition.valueOf((String) card.get("cardposition"));


                        // Leggi e crea l'oggetto SideCard
                        JSONObject sideObject = (JSONObject) card.get("side");
                        Side side = Side.valueOf((String) sideObject.get("side"));
                        List<Corner> frontCorners = readCorners((JSONArray) sideObject.get("front"));
                        List<Corner> backCorners = readCorners((JSONArray) sideObject.get("back"));
                        SideCard sideCard = new SideCard(side, frontCorners, backCorners);

                        // Crea l'oggetto Card e aggiungilo alla lista
                        cards.add(new Card(type, cardres, requireGold, points, cardposition, sideCard));

                    }
                    System.out.println("FEIN! FEIN! FEIN!");
                    // Stampa le carte lette
                    printAllCards(cards);
                } catch (org.json.simple.parser.ParseException e) {
                    throw new RuntimeException(e);
                }

                break;

            case GOLD:
                this.CardNumbers = 40; 
                 // CHIAMARE FUNZIONE GENERA CARTE
                break;

            case OBJECT:
                this.CardNumbers = 16; 
                 // CHIAMARE FUNZIONE GENERA CARTE
                break;

            case STARTER:
                this.CardNumbers = 6; 
                 // CHIAMARE FUNZIONE GENERA CARTE
                break;

            default:
                this.CardNumbers = 0; 
        }

    }
    public static List<Corner> readCorners(JSONArray jsonArray) {
            List<Corner> corners = new ArrayList<>();
            for (Object obj : jsonArray) {
                JSONObject cornerObject = (JSONObject) obj;
                String pos = (String) cornerObject.get("Position");
                Position position = Position.valueOf(pos);
                String propCorn = (String) cornerObject.get("PropertiesCorner");
                PropertiesCorner propertiesCorner = PropertiesCorner.valueOf(propCorn);
                corners.add(new Corner(position, propertiesCorner));
            }
            return corners;
     }

    public int getCardNumbers() {
         return CardNumbers;
    }
    public void printAllCards(List<Card> cards) {
        for (Card card : cards) {
            System.out.println("Type: " + card.getType());
            System.out.println("Card Resource: " + card.getCardRes());
            System.out.print("Required Gold: ");
            CardRes[] requireGold = card.getRequireGold();
            for (int i = 0; i < requireGold.length; i++) {
                System.out.print(requireGold[i]);
                if (i < requireGold.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
            System.out.println("Points: " + card.getPoints());
            System.out.println("Card Position: " + card.getCardPosition());
            System.out.println("Side: " + card.getSide().getSide());
            System.out.println("Front Corners:");
            List<Corner> frontCorners = card.getSide().getFrontCorners();
            for (Corner corner : frontCorners) {
                System.out.println("Position: " + corner.getPosition() + ", PropertiesCorner: " + corner.getPropertiesCorner());
            }
            System.out.println("Back Corners:");
            List<Corner> backCorners = card.getSide().getBackCorners();
            for (Corner corner : backCorners) {
                System.out.println("Position: " + corner.getPosition() + ", PropertiesCorner: " + corner.getPropertiesCorner());
            }
            System.out.println();
        }
    }


}


