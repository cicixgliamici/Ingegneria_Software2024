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
    private List<Card> visibleReCard;
    private List<Card> visibleGoCard;

    public DrawingCardArea() throws IOException, ParseException {
        this.resourceDeck = new Deck(Type.RESOURCES);   //covered resource deck
        this.goldDeck = new Deck(Type.GOLD);    //covered gold deck
        visibleGoCard = new ArrayList<>();   //2 visible gold cards
        visibleReCard = new ArrayList<>();   //2 visible resource cards
        initializeVReCard();
        initializeVGoCard();
    }

    private void initializeVReCard(){   //create the 2 visible resource cards
        for(int i=0; i<2;i++){
            Card card = resourceDeck.getCards().remove(0);
            visibleReCard.add(card);
        }
    }
    private void initializeVGoCard(){   //create the 2 visible gold cards
        for(int i=0; i<2; i++){
            Card card = goldDeck.getCards().remove(0);
            visibleGoCard.add(card);
        }
    }
    public Card drawCardFromDeck(Type type) {   //draw card from the covered deck
        switch(type) {
            case RESOURCES:
                return resourceDeck.drawCard();
            case GOLD:
                return goldDeck.drawCard();
            default:
                return null;
        }
    }
    public Card drawCardFromVisible(Type type, int i) {    //draw from one of the 4 visible cards
        Card drawnCard = null;
        switch(type) {
            case RESOURCES:
                if (i >= 0 && i < visibleReCard.size() && visibleReCard.get(i) != null) {
                    drawnCard = visibleReCard.remove(0); //?????
                    if (!resourceDeck.getCards().isEmpty()) {
                        Card newCard = resourceDeck.getCards().remove(0);
                        visibleReCard.add(newCard);
                    }
                }
                break;
            case GOLD:
                if (i >= 0 && i < visibleGoCard.size() && visibleGoCard.get(i) != null) {
                    drawnCard = visibleGoCard.remove(0);
                    if (!goldDeck.getCards().isEmpty()) {
                        Card newCard = goldDeck.getCards().remove(0);
                        visibleGoCard.add(newCard);
                    }
                }
                break;
        }
        return drawnCard;
    }
    public void DisplayVisibleCard (){
        System.out.println("There are these cards: \n");
        for (int i = 0; i < visibleReCard.size(); i++){
            System.out.println((i + 1) + ":"+ visibleReCard.get(i));
        }
        for (int i = 0; i <visibleGoCard.size(); i++){
            System.out.println((i + 1) + ":" + visibleGoCard.get(i));
        }
    }

    public int searchCardInVC(Card card) throws IllegalArgumentException{
        switch(card.getType()) {
            case RESOURCES:
                for (int i = 0; i < visibleReCard.size(); i++){
                    if (card == visibleReCard.get(i)){
                        return i;
                    }
                }
            case GOLD:
                for (int i = 0; i < visibleGoCard.size(); i++){
                    if (card == visibleGoCard.get(i)){
                        return i;
                    }
                }
        }
        throw new IllegalArgumentException("Card doesn't exist!\n");
    }

    public void RemoveCardFromVC(Card card) throws IllegalArgumentException{
        switch (card.getType()){
            case RESOURCES:
                visibleReCard.remove(searchCardInVC(card));
            case GOLD:
                visibleGoCard.remove(searchCardInVC(card));
        }
        throw new IllegalArgumentException("Nothing to delete!\n");
    }
}
