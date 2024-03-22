package org.example.model.deck;
import org.example.model.deck.enumeration.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
                    JSONObject card = (JSONObject) o;
                    Type type  = (Type) card.get("type");
                    CardRes cardRes = (CardRes) card.get("cardres");

                    JSONArray  = (JSONArray) card.get("cars");

                    CardRes[] requireGold = (CardRes[]) card.get("requireGold");
                    System.out.println(job);

                    JSONArray cars = (JSONArray) person.get("cars");

                    for (Object c : cars)
                    {
                        System.out.println(c+"");
                    }
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

