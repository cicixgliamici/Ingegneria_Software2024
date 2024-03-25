package org.example.model.deck;

import org.example.model.deck.enumeration.Type;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {
        Type resources = Type.GOLD;
        Deck deck = new Deck(resources);
    }
}
