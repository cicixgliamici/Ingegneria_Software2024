package org.example.model.deck;

import org.example.enumeration.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class Deck that gets data from the JSON file.
 */
public class Deck {

    private Type typeDeck;
    private int cardNumbers;
    private List<Card> cards = new ArrayList<>();

    /**
     * Constructs a Deck of the specified type and loads cards from JSON files.
     *
     * @param typeDeck the type of the deck
     * @throws IOException if there is an error reading the JSON file
     */
    public Deck(Type typeDeck) throws IOException {
        this.typeDeck = typeDeck;

        switch (typeDeck) {
            case RESOURCES:
                this.cardNumbers = 40;
                try {
                    JSONParser parser = new JSONParser();
                    JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/resources/Card.json"));
                    for (Object obj : jsonArray) {
                        JSONObject card = (JSONObject) obj;
                        int id = Integer.parseInt(card.get("id").toString());
                        Type type = Type.valueOf((String) card.get("type"));
                        CardRes cardres = CardRes.valueOf((String) card.get("cardres"));
                        int points = Integer.parseInt(card.get("points").toString());
                        CardPosition cardposition = CardPosition.valueOf((String) card.get("cardposition"));
                        JSONObject sideObject = (JSONObject) card.get("side");
                        Side side = Side.valueOf((String) sideObject.get("side"));
                        List<Corner> frontCorners = readCorners((JSONArray) sideObject.get("front"));
                        List<Corner> backCorners = readCorners((JSONArray) sideObject.get("back"));
                        SideCard sideCard = new SideCard(side, frontCorners, backCorners);
                        cards.add(new Card(id, type, cardres, null, points, null, null, cardposition, sideCard));
                    }
                } catch (org.json.simple.parser.ParseException e) {
                    throw new RuntimeException(e);
                }
                break;

            case GOLD:
                this.cardNumbers = 40;
                try {
                    JSONParser parser = new JSONParser();
                    JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/resources/GoldCard.json"));
                    for (Object obj : jsonArray) {
                        JSONObject card = (JSONObject) obj;
                        int id = Integer.parseInt(card.get("id").toString());
                        Type type = Type.valueOf((String) card.get("type"));
                        CardRes cardres = CardRes.valueOf((String) card.get("cardres"));
                        JSONArray requireGoldArray = (JSONArray) card.get("requireGold");
                        CardRes[] requireGold = new CardRes[requireGoldArray.size()];
                        for (int j = 0; j < requireGoldArray.size(); j++) {
                            requireGold[j] = CardRes.valueOf((String) requireGoldArray.get(j));
                        }
                        int points = Integer.parseInt(card.get("points").toString());
                        GoldenPoint goldenPoint = GoldenPoint.valueOf((String) card.get("goldenPoint"));
                        CardPosition cardposition = CardPosition.valueOf((String) card.get("cardposition"));
                        JSONObject sideObject = (JSONObject) card.get("side");
                        Side side = Side.valueOf((String) sideObject.get("side"));
                        List<Corner> frontCorners = readCorners((JSONArray) sideObject.get("front"));
                        List<Corner> backCorners = readCorners((JSONArray) sideObject.get("back"));
                        SideCard sideCard = new SideCard(side, frontCorners, backCorners);
                        cards.add(new Card(id, type, cardres, requireGold, points, goldenPoint, null, cardposition, sideCard));
                    }
                } catch (org.json.simple.parser.ParseException e) {
                    throw new RuntimeException(e);
                }
                break;

            case OBJECT:
                this.cardNumbers = 16;
                try {
                    JSONParser parser = new JSONParser();
                    JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/resources/ObjectiveCard.json"));
                    for (Object obj : jsonArray) {
                        JSONObject card = (JSONObject) obj;
                        int id = Integer.parseInt(card.get("id").toString());
                        Type type = Type.valueOf((String) card.get("type"));
                        CardRes cardres = CardRes.valueOf((String) card.get("cardres"));
                        int points = Integer.parseInt(card.get("points").toString());
                        ObjectivePoints objectivePoints = ObjectivePoints.valueOf((String) card.get("objectivePoints"));
                        CardPosition cardposition = CardPosition.valueOf((String) card.get("cardposition"));
                        JSONObject sideObject = (JSONObject) card.get("side");
                        Side side = Side.valueOf((String) sideObject.get("side"));
                        SideCard sideCard = new SideCard(side, null, null);
                        cards.add(new Card(id, type, cardres, null, points, null, objectivePoints, cardposition, sideCard));
                    }
                } catch (org.json.simple.parser.ParseException e) {
                    throw new RuntimeException(e);
                }
                break;

            case STARTER:
                this.cardNumbers = 6;
                try {
                    JSONParser parser = new JSONParser();
                    JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/main/resources/StarterCard.json"));
                    for (Object obj : jsonArray) {
                        JSONObject card = (JSONObject) obj;
                        int id = Integer.parseInt(card.get("id").toString());
                        Type type = Type.valueOf((String) card.get("type"));
                        JSONArray requireGoldArray = (JSONArray) card.get("requireGold");
                        CardRes[] requireGold = new CardRes[requireGoldArray.size()];
                        for (int j = 0; j < requireGoldArray.size(); j++) {
                            requireGold[j] = CardRes.valueOf((String) requireGoldArray.get(j));
                        }
                        JSONObject sideObject = (JSONObject) card.get("side");
                        Side side = Side.valueOf((String) sideObject.get("side"));
                        List<Corner> frontCorners = readCorners((JSONArray) sideObject.get("front"));
                        List<Corner> backCorners = readCorners((JSONArray) sideObject.get("back"));
                        SideCard sideCard = new SideCard(side, frontCorners, backCorners);
                        cards.add(new Card(id, type, null, requireGold, null, null, null, null, sideCard));
                    }
                } catch (org.json.simple.parser.ParseException e) {
                    throw new RuntimeException(e);
                }
                break;

            default:
                this.cardNumbers = 0;
        }
    }

    /**
     * Reads corners from a JSON array.
     *
     * @param jsonArray the JSON array containing corner data
     * @return a list of corners
     */
    public static List<Corner> readCorners(JSONArray jsonArray) {
        List<Corner> corners = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject cornerObject = (JSONObject) obj;
            String pos = (String) cornerObject.get("Position");
            Position position = Position.valueOf(pos);
            String propCorn = (String) cornerObject.get("PropertiesCorner");
            PropertiesCorner propertiesCorner = PropertiesCorner.valueOf(propCorn);
            corners.add(new Corner(position, propertiesCorner));
        }
        return corners;
    }

    /**
     * Prints all cards in the deck (TUI Method).
     */
    public void printAllCards() {
        for (Card card : cards) {
            card.print();
        }
    }

    /**
     * Prints a specific card from the deck (TUI Method).
     *
     * @param numcard the index of the card to print
     */
    public void printCard(int numcard) {
        cards.get(numcard).print();
    }

    /**
     * Shuffles the deck.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Draws a card from the deck.
     *
     * @return the drawn card
     */
    public Card drawCard() {
        Card card = this.cards.get(0);
        this.cards.remove(0);
        this.cardNumbers--;
        return card;
    }

    /**
     * Returns the first card from the deck without removing it.
     *
     * @return the first card in the deck
     */
    public Card fakeDrawCard() {
        Card card = this.cards.get(0);
        return card;
    }

    /**
     * Getter for the type of the deck.
     *
     * @return the type of the deck
     */
    public Type getTypeDeck() {
        return typeDeck;
    }

    /**
     * Getter for the number of cards in the deck.
     *
     * @return the number of cards in the deck
     */
    public int getCardNumbers() {
        return cardNumbers;
    }

    /**
     * Getter for the list of cards in the deck.
     *
     * @return the list of cards in the deck
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Adds a card to the deck.
     *
     * @param card the card to add
     */
    public void addCard(Card card) {
        cards.add(card);
        cardNumbers++;
    }
}
