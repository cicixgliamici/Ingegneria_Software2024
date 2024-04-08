package org.example.model.PlayArea;
import org.example.model.deck.*;
import org.example.model.deck.enumeration.Type;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;

import java.io.IOException;

public class DrawingCardArea {
    private Deck resourceDeck;
    private Deck goldDeck;
    private ArrayList<Card>[] visibleReCard;
    private ArrayList<Card>[] visibleGoCard;

    public DrawingCardArea() throws IOException, ParseException {
        this.resourceDeck = new Deck(Type.RESOURCES);
        this.goldDeck = new Deck(Type.GOLD);
        visibleGoCard = new ArrayList[2];
        visibleReCard = new ArrayList[2];
        initializeVReCard();
        initializeVGoCard();
    }

    private void initializeVReCard(){
        for(int i=0; i<2;i++){
            Card card = resourceDeck.getCards().remove(0);
            visibleReCard[i].add(card);
        }
    }
    private void initializeVGoCard(){
        for(int i=0; i<2; i++){
            Card card = goldDeck.getCards().remove(0);
            visibleGoCard[i].add(card);
        }
    }
}
