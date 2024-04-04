package org.example.model.PlayArea;
import org.example.model.deck.*;
import org.example.model.deck.enumeration.*;
import org.example.model.PlayArea.*;
import org.example.model.*;

import java.util.List;

public class Player {

    List<Card> hand;

    Card cartaIniziale;

    public Player (Card cartaIniziale, Side choosenSide){
        this.cartaIniziale=cartaIniziale;
        cartaIniziale.getSide().setSide(choosenSide);
        DrawCard();
    }
    //il player deve scegliere il side della carta iniziale da giocarla prima di creare l'area di gioco
    //creare un metodo che cambi l'attributo Side di SideCard inizialmente a BOTH facendo scegliere se front o back
    //per ora messo di default a fro

    PlayerCardArea AreaDiGioco = new PlayerCardArea(cartaIniziale);

    public void DrawCard(Deck deckFromDraw){
        hand.add(deckFromDraw.drawCard());
    }
}

//controller del player