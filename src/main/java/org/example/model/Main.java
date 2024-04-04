package org.example.model;

import org.example.model.PlayArea.Player;
import org.example.model.deck.Card;
import org.example.model.deck.Deck;
import org.example.model.deck.enumeration.Side;
import org.example.model.deck.enumeration.Type;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {

        Deck deck1 = new Deck(Type.RESOURCES);
        //deck1.printAllCards();
        deck1.printCard(0);
        deck1.shuffle();
        deck1.printCard(0);
        Card card = deck1.drawCard(deck1);
        card.print();
        Deck deck2 = new Deck(Type.GOLD);
        Deck deck3 = new Deck(Type.OBJECT);
        Deck deck4 = new Deck(Type.STARTER);
        Card cartaInizialePlayer1 = deck4.drawCard(deck4);
        Player player1 = new Player(cartaInizialePlayer1, Side.FRONT);

    }
}
