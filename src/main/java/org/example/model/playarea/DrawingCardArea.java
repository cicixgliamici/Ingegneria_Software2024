package org.example.model.playarea;

import org.example.model.deck.*;
import org.example.enumeration.Type;
import org.json.simple.parser.ParseException;
import java.util.*;
import java.io.IOException;

/**
 * Class that manipulates Decks and everything that is needed.
 * Generates decks and puts them on the ground.
 * Generates the drawing area with the four cards that you can draw.
 */
public class DrawingCardArea {
    private static Deck resourceDeck;
    private static Deck goldDeck;
    private static Deck objectDeck;
    private static Deck starterDeck;
    private static List<Card> visibleReCard;
    private static List<Card> visibleGoCard;

    /**
     * Constructs a DrawingCardArea, initializes and shuffles the decks,
     * and sets up the initial visible cards.
     *
     * @throws IOException if there is an error reading the JSON files
     * @throws ParseException if there is an error parsing the JSON files
     */
    public DrawingCardArea() throws IOException, ParseException {
        this.resourceDeck = new Deck(Type.RESOURCES);   // covered resource deck
        this.resourceDeck.shuffle();
        this.goldDeck = new Deck(Type.GOLD);            // covered gold deck
        this.goldDeck.shuffle();
        this.objectDeck = new Deck(Type.OBJECT);        // object deck
        this.objectDeck.shuffle();
        this.starterDeck = new Deck(Type.STARTER);      // starter deck
        this.starterDeck.shuffle();
        visibleGoCard = new ArrayList<>();              // 2 visible gold cards
        visibleReCard = new ArrayList<>();              // 2 visible resource cards
        initializeVGoCard();
        initializeVReCard();
    }

    /**
     * Creates the 2 visible resource cards, taking them from the first two of the generated deck.
     */
    public void initializeVReCard() {
        for (int i = 0; i < 2; i++) {
            visibleReCard.add(resourceDeck.drawCard());
        }
    }

    /**
     * Creates the 2 visible gold cards, taking them from the first two of the generated deck.
     */
    public void initializeVGoCard() {
        for (int i = 0; i < 2; i++) {
            visibleGoCard.add(goldDeck.drawCard());
        }
    }

    /**
     * Draws a card from the covered deck specified by type.
     *
     * @param type the type of deck to draw from
     * @return the drawn card
     */
    public Card drawCardFromDeck(Type type) {
        switch (type) {
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

    /**
     * Gets the ID of the top card from the specified deck type.
     *
     * @param type the type of deck
     * @return the ID of the top card
     */
    public static int getCardIdFromDeck(Type type) {
        switch (type) {
            case RESOURCES:
                return resourceDeck.getCards().get(0).getId();
            case GOLD:
                return goldDeck.getCards().get(0).getId();
            case STARTER:
                return starterDeck.getCards().get(0).getId();
            case OBJECT:
                return objectDeck.getCards().get(0).getId();
            default:
                return 0;
        }
    }

    /**
     * Draws a card from the visible cards of the specified type.
     *
     * @param type the type of card to draw
     * @param i the index of the visible card to draw
     * @return the drawn card
     */
    public Card drawCardFromVisible(Type type, int i) {
        Card drawnCard = null;
        switch (type) {
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

    /**
     * Displays the visible cards for the TUI, allowing the player to choose a card.
     */
    public void displayVisibleCard() {
        for (int i = 0; i < visibleReCard.size(); i++) {
            // System.out.println((i + 1) + ":" + visibleReCard.get(i));
        }
        for (int i = 0; i < visibleGoCard.size(); i++) {
            // System.out.println((i + 1) + ":" + visibleGoCard.get(i));
        }
    }

    /**
     * Getter for the object deck.
     *
     * @return the object deck
     */
    public Deck getObjectDeck() {
        return objectDeck;
    }

    /**
     * Getter for the visible resource cards.
     *
     * @return the list of visible resource cards
     */
    public static List<Card> getVisibleReCard() {
        return visibleReCard;
    }

    /**
     * Getter for the visible gold cards.
     *
     * @return the list of visible gold cards
     */
    public static List<Card> getVisibleGoCard() {
        return visibleGoCard;
    }

    /**
     * Getter for the resource deck.
     *
     * @return the resource deck
     */
    public Deck getResourceDeck() {
        return resourceDeck;
    }

    /**
     * Getter for the gold deck.
     *
     * @return the gold deck
     */
    public Deck getGoldDeck() {
        return goldDeck;
    }

    /**
     * Getter for the starter deck.
     *
     * @return the starter deck
     */
    public Deck getStarterDeck() {
        return starterDeck;
    }
}
