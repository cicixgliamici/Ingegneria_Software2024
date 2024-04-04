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

        Deck ResourcesDeck = new Deck(Type.RESOURCES);
        //ResourcesDeck.printAllCards();
        ResourcesDeck.printCard(0);
        ResourcesDeck.shuffle();
        ResourcesDeck.printCard(0);
        Card card = ResourcesDeck.drawCard();
        card.print();
        Deck GoldDeck = new Deck(Type.GOLD);
        Deck ObjectDeck = new Deck(Type.OBJECT);
        Deck StarterDeck = new Deck(Type.STARTER);
        Card cartaInizialePlayer1 = StarterDeck.drawCard();
        Player player1 = new Player(cartaInizialePlayer1, Side.FRONT);

    }
}
