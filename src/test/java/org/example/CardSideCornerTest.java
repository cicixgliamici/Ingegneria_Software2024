package org.example;

import junit.framework.TestCase;
import org.example.enumeration.*;
import org.example.model.deck.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardSideCornerTest extends TestCase {
    /**
     * Card Zone Tests
     */
    public void testCardType() throws IOException, ParseException {
        for (Type type : Type.values()) {
            Deck deck = new Deck(type);
            Card card = deck.drawCard();
            assertEquals(type, card.getType());
        }
    }

    public void testPropCorn() throws IOException, ParseException {
        for (Type type : Type.values()) {
            Deck deck = new Deck(type);
            Card card = deck.drawCard();
            switch (type) {
                case RESOURCES:
                    assertEquals(PropertiesCorner.FUNGI, card.getFRONTPropCorn(0));
                    break;
                case GOLD:
                    assertEquals(PropertiesCorner.HIDDEN, card.getFRONTPropCorn(0));
                    break;
                case STARTER:
                    assertEquals(PropertiesCorner.EMPTY, card.getFRONTPropCorn(0));
                    break;
                default:
                    break;
            }
        }
    }
    
    public void testCornerIsHidden(){
        Corner c1 = new Corner(Position.BOTTOML, PropertiesCorner.HIDDEN);
        Corner c2 = new Corner(Position.TOPL, PropertiesCorner.HIDDEN);
        Corner c3 = new Corner(Position.BOTTOMR, PropertiesCorner.HIDDEN);
        Corner c4 = new Corner(Position.TOPR, PropertiesCorner.HIDDEN);
        List<Corner> front = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
        Corner c5 = new Corner(Position.TOPR, PropertiesCorner.HIDDEN);
        Corner c6 = new Corner(Position.BOTTOMR, PropertiesCorner.HIDDEN);
        Corner c7 = new Corner(Position.BOTTOML, PropertiesCorner.HIDDEN);
        Corner c8 = new Corner(Position.TOPL, PropertiesCorner.HIDDEN);
        List<Corner> back = new ArrayList<>(Arrays.asList(c5,c6,c7,c8));
        SideCard side = new SideCard(Side.FRONT, front, back);
        Card card = new Card(side);
        assertTrue(card.BOTLCornerIsHidden());
        assertTrue(card.BOTRCornerIsHidden());
        assertTrue(card.TOPLCornerIsHidden());
        assertTrue(card.TOPRCornerIsHidden());
    }

    public void testSetSide(){
        Corner c1 = new Corner(Position.BOTTOML, PropertiesCorner.HIDDEN);
        Corner c2 = new Corner(Position.TOPL, PropertiesCorner.HIDDEN);
        Corner c3 = new Corner(Position.BOTTOMR, PropertiesCorner.HIDDEN);
        Corner c4 = new Corner(Position.TOPR, PropertiesCorner.HIDDEN);
        List<Corner> front = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
        Corner c5 = new Corner(Position.TOPR, PropertiesCorner.HIDDEN);
        Corner c6 = new Corner(Position.BOTTOMR, PropertiesCorner.HIDDEN);
        Corner c7 = new Corner(Position.BOTTOML, PropertiesCorner.HIDDEN);
        Corner c8 = new Corner(Position.TOPL, PropertiesCorner.HIDDEN);
        List<Corner> back = new ArrayList<>(Arrays.asList(c5,c6,c7,c8));
        SideCard side = new SideCard(Side.FRONT, front, back);
        Card card = new Card(side);

        card.setSide(side);
        assertEquals(Side.FRONT, card.getSide().getSide());

        card.setSide(1);
        assertEquals(Side.FRONT, card.getSide().getSide());

        card.setSide(2);
        assertEquals(Side.BACK, card.getSide().getSide());
    }

    public void testGetChosenListAndGetters(){
        List<Corner> frontCorners = new ArrayList<>();
        List<Corner> backCorners = new ArrayList<>();
        SideCard sideCardFront = new SideCard(Side.FRONT, frontCorners, backCorners);
        SideCard sideCardBack = new SideCard(Side.BACK, frontCorners, backCorners);
        //getChosenList()
        assertEquals(frontCorners, sideCardFront.getChosenList());
        assertEquals(backCorners, sideCardBack.getChosenList());
        //getSide()
        assertEquals(Side.FRONT, sideCardFront.getSide());
        assertEquals(Side.BACK, sideCardBack.getSide());
        //getFrontCorners()
        assertEquals(frontCorners, sideCardFront.getFrontCorners());
        assertEquals(frontCorners, sideCardBack.getFrontCorners());
        //getBackCorners()
        assertEquals(backCorners, sideCardFront.getBackCorners());
        assertEquals(backCorners, sideCardBack.getBackCorners());
    }

    public void testCardGetSet(){
        Corner c1 = new Corner(Position.BOTTOML, PropertiesCorner.QUILL);
        Corner c2 = new Corner(Position.TOPL, PropertiesCorner.PLANT);
        Corner c3 = new Corner(Position.BOTTOMR, PropertiesCorner.ANIMAL);
        Corner c4 = new Corner(Position.TOPR, PropertiesCorner.HIDDEN);
        List<Corner> front = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
        List<Corner> back = new ArrayList<>();
        SideCard side = new SideCard(Side.FRONT, front, back);
        Card c = new Card(side);
        assertEquals(PropertiesCorner.QUILL, c.getFRONTPropCorn(0));
        assertEquals(PropertiesCorner.PLANT, c.getFRONTPropCorn(1));
        assertEquals(PropertiesCorner.ANIMAL, c.getFRONTPropCorn(2));
        assertEquals(PropertiesCorner.HIDDEN, c.getFRONTPropCorn(3));
        Type type = Type.RESOURCES;
        CardRes cardRes = CardRes.ANIMAL;
        int id = 1;
        CardRes[] requireGold = new CardRes[]{CardRes.QUILL, CardRes.INSECT};
        Integer points = 2;
        GoldenPoint goldenPoint = GoldenPoint.CORNER;
        ObjectivePoints objectivePoints = ObjectivePoints.BLUERED;
        CardPosition cardPosition = CardPosition.HAND;
        Card card1 = new Card(id, type, cardRes, requireGold, points, goldenPoint, objectivePoints, cardPosition, side);
        assertEquals(id, card1.getId());
        assertEquals(type, card1.getType());
        assertEquals(cardRes, card1.getCardRes());
        assertEquals(requireGold, card1.getRequireGold());
        assertEquals(points, card1.getPoints());
        assertEquals(goldenPoint, card1.getGoldenPoint());
        assertEquals(objectivePoints, card1.getObjectivePoints());
        assertEquals(cardPosition, card1.getCardPosition());
        assertEquals(side, card1.getSide());
        c.setType(type);
        assertEquals(Type.RESOURCES, c.getType());
        c.setCardRes(cardRes);
        assertEquals(CardRes.ANIMAL, c.getCardRes());
        c.setPoints(points);
        assertEquals(points,c.getPoints());
        c.setGoldenPoint(goldenPoint);
        assertEquals(GoldenPoint.CORNER, c.getGoldenPoint());
        c.setObjectivePoints(objectivePoints);
        assertEquals(ObjectivePoints.BLUERED, c.getObjectivePoints());
        c.setCardPosition(cardPosition);
        assertEquals(CardPosition.HAND, c.getCardPosition());
        c.setId(id);
        assertEquals(1, c.getId());
        int coveredCornerByCard = 2;
        c.setCoveredCornerByCard(coveredCornerByCard);
        assertEquals(2, c.getCoveredCornerByCard());
    }

    public void testCornerGet(){
        Position position = Position.BOTTOML;
        PropertiesCorner propertiesCorner = PropertiesCorner.QUILL;
        Corner corner = new Corner(position, propertiesCorner);
        assertEquals(Position.BOTTOML, corner.getPosition());
        assertEquals(PropertiesCorner.QUILL, corner.getPropertiesCorner());
    }
}
