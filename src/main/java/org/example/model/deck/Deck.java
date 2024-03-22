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

    private Type type;
    private int CardNumbers;

    JSONParser parser = new JSONParser();
    JSONArray a = (JSONArray) parser.parse(new FileReader("// INSERIRE NOME FILE"));

     public Deck(Type type) throws IOException, ParseException {
        this.type = type;
        switch (type) {
            case RESOURCES:
                this.CardNumbers = 40;
                // CHIAMARE FUNZIONE GENERA CARTE
                for (Object o : a)
                {
                    JSONObject person = (JSONObject) o;

                    String name = (String) person.get("name");
                    System.out.println(name);

                    String city = (String) person.get("city");
                    System.out.println(city);

                    String job = (String) person.get("job");
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

    public Type getType() {
        return type;
    }

    public int getCardNumbers() {
        return CardNumbers;
    }
}

