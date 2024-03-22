package org.example.model.deck;
import org.example.model.deck.enumeration.*;
import java.util.ArrayList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Class Deck that get data from the JSON file
 *
 */

public class Deck {
    
    private Type typeDeck;
    private int CardNumbers;

     public Deck(Type typeDeck) throws IOException, ParseException {
        this.typeDeck = typeDeck;
        JSONParser parser = new JSONParser();
        JSONArray a = (JSONArray) parser.parse(new FileReader("src/main/resources/Card.json"));
        switch (typeDeck) {
            case RESOURCES:
                this.CardNumbers = 1;
                for (Object o : a)
                {
                    JSONObject RiddenCard = (JSONObject) o;

                    Type type  = (Type) RiddenCard.get("type");
                    CardRes cardRes = (CardRes) RiddenCard.get("cardres");

                    JSONArray cardGoldRes = (JSONArray) RiddenCard.get("requireGold");
                    CardRes[] copiaCardGoldRes= new CardRes[cardGoldRes.size()];

                    for (int i = 0; i < cardGoldRes.size(); i++) {
                        try {
                            JSONObject jsonObject =  copiaCardGoldRes.getJSONObject(i);
                            String name = jsonObject.getString("name");
                            int age = jsonObject.getInt("age");
                            customArray[i] = new CustomObject(name, age);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    int Points = (int) RiddenCard.get("points");
                    CardPosition cardPosition = (CardPosition) RiddenCard.get("cardposition");

                    JSONArray side = (JSONArray) RiddenCard.get("side");

                    Card newCard= new Card(type, cardRes, cardGoldRes, Points, cardPosition, side);
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

    public Type getType() {
        return type;
    }

    public int getCardNumbers() {
         return CardNumbers;
    }
}

