package org.example;

import junit.framework.TestCase;
import org.example.model.deck.*;
import org.example.enumeration.PropertiesCorner;
import org.example.enumeration.Type;
import org.json.simple.parser.ParseException;

import java.io.IOException;

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

    public void testGetChosenList(){
        Corner c1 = new Corner();
        Corner c2 = new Corner();
        Corner c3 = new Corner();
        Corner c4 = new Corner();
        
        List<Corner> frontCorners = new ArrayList<>();
        frontCorners.add(c1);
        frontCorners.add(c2);
        List<Corner> backCorners = new ArrayList<>();
        backCorners.add(c3);
        backCorners.add(c4);
        
        SideCard sideCardFront = new SideCard(Side.FRONT, frontCorners, backCorners);
        SideCard sideCardBack = new SideCard(Side.BACK, frontCorners, backCorners);
        
        assertEquals(frontCorners, sideCardFront.getChosenList());
        assertEquals(backCorners, sideCardBack.getChosenList());
    }
}
