package org.example.model.PlayArea;
import org.example.model.deck.*;
import org.example.model.deck.enumeration.*;

import java.util.List;

/** This class reference the player in the Model, in order to keep his resources and the PlayerCardArea, the real player
 * is in the Controller
 */

public class Player {

    List<Card> hand;

    Card InitialCard;

    public Player (Card InitialCard){
        this.InitialCard = InitialCard;
    }
    //il player deve scegliere il side della carta iniziale da giocarla prima di creare l'area di gioco
    //creare un metodo che cambi l'attributo Side di SideCard inizialmente a BOTH facendo scegliere se front o back
    //per ora messo di default a fro

    PlayerCardArea gameArea = new PlayerCardArea(InitialCard);

    public void DrawCard(Deck deckFromDraw){
        if (hand.size()==2)
            hand.add(deckFromDraw.drawCard());
    }

    public void addCard(Card c){
        if (hand.size()==2)
            hand.add(c);
    }
/*
    public void printListCard (PlayerCardArea gameArea){
        gameArea.
    }

 */
}

//controller del player