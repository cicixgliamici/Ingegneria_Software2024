package org.example.model.deck;
import org.example.model.deck.enumeration.*;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
                    // Leggi il file JSON
                    FileReader reader = new FileReader("src/main/resources/Card.json");

                    // Converte il contenuto del file in un oggetto JSON
                    org.json.JSONArray jsonArray = new org.json.JSONArray(reader);

                    // Crea una lista per memorizzare le carte
                    List<Card> cards = new ArrayList<>();

                    // Itera attraverso ogni oggetto nel JSONArray
                    for (int i = 0; i < jsonArray.length(); i++) {
                        org.json.JSONObject jsonObject = jsonArray.getJSONObject(i);

                        // Leggi i campi dell'oggetto JSON
                        Type type = Type.valueOf(jsonObject.getString("type"));
                        CardRes cardres = CardRes.valueOf(jsonObject.getString("cardres"));
                        org.json.JSONArray requireGoldArray = jsonObject.getJSONArray("requireGold");
                        CardRes[] requireGold = new CardRes[requireGoldArray.length()];
                        for (int j = 0; j < requireGoldArray.length(); j++) {
                            requireGold[j] = CardRes.valueOf(requireGoldArray.getString(j));
                        }
                        int points = jsonObject.getInt("points");
                        CardPosition cardposition = CardPosition.valueOf(jsonObject.getString("cardposition"));

                        // Leggi e crea l'oggetto SideCard
                        JSONObject sideObject = jsonObject.getJSONObject("side");
                        Side side = Side.valueOf(sideObject.getString("side"));
                        List<Corner> frontCorners = readCorners(sideObject.getJSONArray("front"));
                        List<Corner> backCorners = readCorners(sideObject.getJSONArray("back"));
                        SideCard sideCard = new SideCard(side, frontCorners, backCorners);

                        // Crea l'oggetto Card e aggiungilo alla lista
                        cards.add(new Card(type, cardres, null, points, null, cardposition, sideCard));
                    }

                    // Chiudi il lettore
                    reader.close();

                    // Stampa le carte lette
                    for (Card card : cards) {
                        System.out.println(card);
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                break;

            case GOLD:
                this.CardNumbers = 40;
                try {
                    // Leggi il file JSON
                    JSONParser parser = new JSONParser();
                    JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/resources/GoldCard.json"));

                    // Crea una lista per memorizzare le carte
                    List<Card> cards = new ArrayList<>();

                    // Itera attraverso ogni oggetto nel JSONArray
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

                        GoldenPoint goldenPoint = GoldenPoint.valueOf((String) card.get("goldenPoint"));

                        CardPosition cardposition = CardPosition.valueOf((String) card.get("cardposition"));

                        // Leggi e crea l'oggetto SideCard
                        JSONObject sideObject = (JSONObject) card.get("side");
                        Side side = Side.valueOf((String) sideObject.get("side"));
                        List<Corner> frontCorners = readCorners((JSONArray) sideObject.get("front"));
                        List<Corner> backCorners = readCorners((JSONArray) sideObject.get("back"));
                        SideCard sideCard = new SideCard(side, frontCorners, backCorners);

                        // Crea l'oggetto Card e aggiungilo alla lista
                        cards.add(new Card(type, cardres, requireGold, points, goldenPoint, cardposition, sideCard));
                    }

                    // Stampa le carte lette
                    printAllCards(cards);
                } catch (org.json.simple.parser.ParseException e) {
                    throw new RuntimeException(e);
                }
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
    public static List<Corner> readCorners(org.json.JSONArray jsonArray) throws JSONException {
        List<Corner> corners = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject cornerObject = jsonArray.getJSONObject(i);
            String pos = cornerObject.getString("Position");
            Position position = Position.valueOf(pos);
            String propCorn = cornerObject.getString("PropertiesCorner");
            PropertiesCorner propertiesCorner = PropertiesCorner.valueOf(propCorn);
            corners.add(new Corner(position, propertiesCorner ));
        }
    return corners;
    }

    public int getCardNumbers() {
         return CardNumbers;
    }

}


