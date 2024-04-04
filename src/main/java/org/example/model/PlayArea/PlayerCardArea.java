package org.example.model.PlayArea;
import com.sun.java.swing.plaf.windows.TMSchema;
import org.example.model.deck.*;
import org.example.model.deck.enumeration.*;
import org.example.model.deck.enumeration.cast.CastCardRes;

public class PlayerCardArea {
    private Node Starter;
    private CounterResources counter = new CounterResources();

    //questa funzione verrà chiamata dal controller del player per inizializzare la propria area di gioco
    public PlayerCardArea(Card cardStarter) {
        this.Starter = new Node(cardStarter, null, null, null, null, 0, 0);
        this.Starter.searchAvailableNode();
        if ((cardStarter.getSide().getChoosenList().get(1).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (cardStarter.getSide().getBackCorners().get(1).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
            counter.AddResource(cardStarter.getSide().getBackCorners().get(1).getPropertiesCorner());
        } else if ((cardStarter.getSide().getBackCorners().get(2).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (cardStarter.getSide().getBackCorners().get(2).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
            counter.AddResource(cardStarter.getPropCorn(2));
        } else if ((cardStarter.getSide().getBackCorners().get(3).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (cardStarter.getSide().getBackCorners().get(3).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
            counter.AddResource(cardStarter.getPropCorn(3));
        } else if ((cardStarter.getSide().getBackCorners().get(4).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (cardStarter.getSide().getBackCorners().get(4).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
            counter.AddResource(cardStarter.getPropCorn(4));
        }

        //aggiunta nuova classe per fare il cast
        for (CardRes cardRes : cardStarter.getRequireGold()) {
            CastCardRes castCardRes = new CastCardRes(cardRes);
            counter.AddResource(castCardRes.getPropertiesCorner());
        }


    }

    public void addCard(Card NewCard, Node ChoosenNode){
        //devo cercare nella lista degli angoli disponibili, l'angolo che ho scelto e la touchedcard

    }
}
