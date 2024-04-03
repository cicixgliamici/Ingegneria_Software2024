package org.example.model.PlayArea;
import org.example.model.deck.*;
import org.example.model.deck.enumeration.*;

public class Player {

    Card cartaIniziale;

    public Player (Card cartaIniziale, Side choosenSide){
        this.cartaIniziale=cartaIniziale;
        cartaIniziale.getSide().setSide(choosenSide);
    }
    //il player deve scegliere il side della carta iniziale da giocarla prima di creare l'area di gioco
    //creare un metodo che cambi l'attributo Side di SideCard inizialmente a BOTH facendo scegliere se front o back
    //per ora messo di default a front


    PlayerCardArea AreaDiGioco = new PlayerCardArea(cartaIniziale);

}