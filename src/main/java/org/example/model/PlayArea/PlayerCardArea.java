package org.example.model.PlayArea;
//* import com.sun.java.swing.plaf.windows.TMSchema;
import org.example.model.deck.*;
import org.example.model.deck.enumeration.*;
import org.example.model.deck.enumeration.cast.CastCardRes;

import java.util.ArrayList;
import java.util.List;

public class PlayerCardArea {
    private Node Starter;
    private CounterResources counter = new CounterResources();
    private List<Node> AllNodes;

    //questa funzione verrà chiamata dal controller del player per inizializzare la propria area di gioco
    public PlayerCardArea(Card cardStarter) {
        this.Starter = new Node(cardStarter, 0,0 );
        this.Starter.searchAvailableNode();
        this.UpdateCounter(cardStarter);
        AllNodes.add(Starter);
    }



    public void UpdateCounter(Card card) {
        if ((card.getSide().getChoosenList().get(1).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (card.getSide().getBackCorners().get(1).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
            counter.AddResource(card.getSide().getBackCorners().get(1).getPropertiesCorner());
        } else if ((card.getSide().getBackCorners().get(2).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (card.getSide().getBackCorners().get(2).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
            counter.AddResource(card.getPropCorn(2));
        } else if ((card.getSide().getBackCorners().get(3).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (card.getSide().getBackCorners().get(3).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
            counter.AddResource(card.getPropCorn(3));
        } else if ((card.getSide().getBackCorners().get(4).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (card.getSide().getBackCorners().get(4).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
            counter.AddResource(card.getPropCorn(4));
        }
        //todo implementare anche il counter di punti

        //se risorsa o gold e ho scelto il back allora aggiungi card res
        if((card.getType()==Type.RESOURCES || card.getType()==Type.GOLD) && (card.getSide().getSide()==Side.BACK)){
            CardRes cardRes= card.getCardRes();
            CastCardRes castCardRes= new CastCardRes(cardRes);
            counter.AddResource(castCardRes.getPropertiesCorner());
        }

        //se starter e hai scelto il back allora add require gold
        if((card.getType()==Type.STARTER)&&(card.getSide().getSide()==Side.BACK)){
            for (CardRes cardRes: card.getRequireGold()){
                CastCardRes castCardRes= new CastCardRes(cardRes);
                counter.AddResource(castCardRes.getPropertiesCorner());
            }
        }
    }


    //todo implementare un controllo per verificare che la carta inserita non copra altre carte

    //Facciamo una lista chiedendo al Prof. della disconnessione ed eventualmente procediamo a levarla e fare con una ricorsione
    public void removeResources(Node node){
        Node current = Starter;
        Node nextNode= Starter;
        while (nextNode != node){






        }

    }

    public Node getStarter() {
        return Starter;
    }
}
