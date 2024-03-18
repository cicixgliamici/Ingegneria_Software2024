package org.example.model.deck;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class deck {
    private List<Card> cards;
    private int CardsNumber;
    private Type type;


    public deck(Type type) {
        cards = new ArrayList<>();
        switch (type) {
            case RESOURCES:
                CardsNumber = 40;
                break;
            case GOLD:
                CardsNumber = 40;
                break;
            case OBJECT:
                CardsNumber = 16;
                break;
            default:
                CardsNumber = 6;
        }
    }

    public void createCards (deck deck, Type type){
         // GENERA CARTE
         // NUMERO IN BASE AL TIPO

    }
}


   /* public generateDeckResource() {

        cards[0] = generateCards(Empty, Empty, Empty, Empty);
    }


    private void generateCards() {

    }

    private void shuffle() {
        Collections.shuffle(cards);
    }

}
*/