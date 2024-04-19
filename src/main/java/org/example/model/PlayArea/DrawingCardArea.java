package org.example.model.PlayArea;
import org.example.model.deck.*;
import org.example.enumeration.Type;
import org.json.simple.parser.ParseException;
import java.util.*;

import java.io.IOException;

/** Class that manipulates Decks and everything that is needed.
 *  Generates decks and puts them on the ground.
 *  Generates the drawing area with the four cards that you can draw.
 */

public class DrawingCardArea {
    private final Deck resourceDeck;
    private final Deck goldDeck;
    private final Deck objectDeck;
    private final Deck starterDeck;
    private final List<Card> visibleReCard;
    private final List<Card> visibleGoCard;

    public DrawingCardArea() throws IOException, ParseException {
        this.resourceDeck = new Deck(Type.RESOURCES);   //covered resource deck
        this.resourceDeck.shuffle();
        this.goldDeck = new Deck(Type.GOLD);            //covered gold deck
        this.goldDeck.shuffle();
        this.objectDeck = new Deck(Type.OBJECT);        //object deck
        this.objectDeck.shuffle();
        this.starterDeck = new Deck(Type.STARTER);      //starter deck
        this.starterDeck.shuffle();
        visibleGoCard = new ArrayList<>();       //2 visible gold cards
        visibleReCard = new ArrayList<>();       //2 visible resource cards
        initializeVGoCard();
        initializeVReCard();
    }

    /** Creates the 2 visible resource cards, taking them from the firsts two of the generated deck
     *
     */
    private void initializeVReCard(){
        for(int i=0; i<2 ; i++){
            visibleReCard.add(resourceDeck.drawCard());
        }
    }

    /** Creates the 2 visible gold cards, taking them from the firsts two of the generated deck
     *
     */
    private void initializeVGoCard(){
        for(int i=0; i<2; i++){
            visibleGoCard.add(goldDeck.drawCard());
        }
    }
    /** Draw a card from the covered deck you choose
     */
    public Card drawCardFromDeck(Type type) {
        switch(type) {
            case RESOURCES:
                return resourceDeck.drawCard();
            case GOLD:
                return goldDeck.drawCard();
            case STARTER:
                return starterDeck.drawCard();
            case OBJECT:
                return objectDeck.drawCard();
            default:
                return null;
        }
    }

    /** Draw from one of the 4 visible cards,
     * the player chooses which card to draw and
     * the type of the card that will replace the drawn card.
     * The int is for which of the 2 cards you want.
     * The index of the list is variable, because you can choose to get
     * resource and replace with a gold.
     * */
    public Card drawCardFromVisible(Type type, int i) {
        Card drawnCard = null;
        switch(type) {
            case RESOURCES:
                if (i >= 0 && i < visibleReCard.size() && visibleReCard.get(i) != null) {
                    drawnCard = visibleReCard.remove(i);
                    if (!resourceDeck.getCards().isEmpty()) {
                        visibleReCard.add(resourceDeck.drawCard());
                    }
                }
                break;
            case GOLD:
                if (i >= 0 && i < visibleGoCard.size() && visibleGoCard.get(i) != null) {
                    drawnCard = visibleGoCard.remove(i);
                    if (!goldDeck.getCards().isEmpty()) {
                        visibleGoCard.add(goldDeck.drawCard());
                    }
                }
                break;
        }
        return drawnCard;
    }

    /** Method for the TUI so the player can choose the card.
     *
     */
    public void DisplayVisibleCard (){
        //System.out.println("There are these cards: \n");
        for (int i = 0; i < visibleReCard.size(); i++){
            //System.out.println((i + 1) + ":"+ visibleReCard.get(i));
        }
        for (int i = 0; i <visibleGoCard.size(); i++){
            //System.out.println((i + 1) + ":" + visibleGoCard.get(i));
        }
    }

    //getter and setter

    public Deck getObjectDeck() {
        return objectDeck;
    }

    public List<Card> getVisibleReCard() {
        return visibleReCard;
    }

    public List<Card> getVisibleGoCard() {
        return visibleGoCard;
    }
}
