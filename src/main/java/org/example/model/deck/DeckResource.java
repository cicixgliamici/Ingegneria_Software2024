package org.example.model.deck;


import java.util.ArrayList;
import java.util.List;

public class DeckResource implements Deck{

        public int CardsNumber;
        List<Card> deckRes;
        public void initializeDeck(Type type){
                deckRes= new ArrayList<>();
                CardsNumber=40;
                ResourceCard Card1= new ResourceCard(
                        CardRes.FUNGI,
                        PropertiesCorner.EMPTY,
                        PropertiesCorner.INSECT,
                        PropertiesCorner.INSECT,
                        PropertiesCorner.ANIMAL,
                        PropertiesCorner.MANUSCRIPT,
                        PropertiesCorner.MANUSCRIPT,
                        PropertiesCorner.ANIMAL,
                        PropertiesCorner.EMPTY,
                        8);
                deckRes.add(Card1);

        }
        public void shuffle(){

        }
}

