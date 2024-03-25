package org.example.model.deck;

import org.example.model.deck.enumeration.Type;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {

        Deck deck1 = new Deck(Type.RESOURCES);
        Deck deck2 = new Deck(Type.GOLD);
        Deck deck3 = new Deck(Type.OBJECT);
        Deck deck4 = new Deck(Type.STARTER);

    }
}
