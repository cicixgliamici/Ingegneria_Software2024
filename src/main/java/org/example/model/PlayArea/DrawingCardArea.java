package org.example.model.PlayArea;
import org.example.model.deck.*;
import org.example.model.deck.enumeration.Type;
import org.json.simple.parser.ParseException;
import java.util.*;
import org.example.model.PlayArea.Player;

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
    public Card drawCardFromDeck(Type type) {
        switch(type) {
            case RESOURCES:
                return resourceDeck.drawCard();
            case GOLD:
                return goldDeck.drawCard();
            default:
                return null;
        }
    }
    public Card drawCardFromVisible(Type type, int i) {
        Card drawnCard = null;
        switch(type) {
            case RESOURCES:
                if (i >= 0 && i < visibleReCard.length && !visibleReCard[i].isEmpty()) {
                    drawnCard = visibleReCard[i].remove(0);
                    if (!resourceDeck.getCards().isEmpty()) {
                        Card newCard = resourceDeck.getCards().remove(0);
                        visibleReCard[i].add(newCard);
                    }
                }
                break;
            case GOLD:
                if (i >= 0 && i < visibleGoCard.length && !visibleGoCard[i].isEmpty()) {
                    drawnCard = visibleGoCard[i].remove(0);
                    if (!goldDeck.getCards().isEmpty()) {
                        Card newCard = goldDeck.getCards().remove(0);
                        visibleGoCard[i].add(newCard);
                    }
                }
                break;
        }
        return drawnCard;
    }
}
