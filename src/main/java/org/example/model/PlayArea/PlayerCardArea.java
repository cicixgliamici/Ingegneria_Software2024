package org.example.model.PlayArea;
//* import com.sun.java.swing.plaf.windows.TMSchema;
import org.example.model.deck.*;
import org.example.model.deck.enumeration.*;
import org.example.model.deck.enumeration.cast.CastCardRes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerCardArea {
    private Node Starter;
    private CounterResources counter = new CounterResources();
    private List<Node> AllNodes;

    private List<Node> AvailableNodes;

    //questa funzione verrà chiamata dal controller del player per inizializzare la propria area di gioco
    public PlayerCardArea(Card cardStarter) {
        this.Starter = new Node(cardStarter, 0,0 );
        this.AvailableNodes =new ArrayList<>();
        this.AllNodes=new ArrayList<>();
        this.searchAvailableNode(Starter);
        this.UpdateCounter(cardStarter);
        AllNodes.add(Starter);
    }

    public void UpdateCounter(Card card) {
        if ((card.getSide().getChoosenList().get(0).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (card.getSide().getBackCorners().get(0).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
            counter.AddResource(card.getSide().getBackCorners().get(0).getPropertiesCorner());
        } else if ((card.getSide().getBackCorners().get(1).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (card.getSide().getBackCorners().get(1).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
            counter.AddResource(card.getPropCorn(1));
        } else if ((card.getSide().getBackCorners().get(2).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (card.getSide().getBackCorners().get(2).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
            counter.AddResource(card.getPropCorn(2));
        } else if ((card.getSide().getBackCorners().get(3).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (card.getSide().getBackCorners().get(3).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
            counter.AddResource(card.getPropCorn(3));
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


    public void searchAvailableNode(Node node){

        if(node.getBotL().getCard() == null && !(node.getBotL() instanceof EmptyNode)){
            this.AvailableNodes.add(node.getBotL());
        }
        else {
            if(!(node.getBotL() instanceof EmptyNode)) searchAvailableNode(node.getBotL());
        }


        if(node.getBotR().getCard() == null && !(node.getBotR() instanceof EmptyNode)){
            this.AvailableNodes.add(node.getBotR());
        }
        else {
            if(!(node.getBotR() instanceof EmptyNode)) searchAvailableNode(node.getBotR());
        }


        if(node.getTopL().getCard() == null && !(node.getTopL() instanceof EmptyNode)){
            this.AvailableNodes.add(node.getTopL());
        }
        else {
            if(!(node.getTopL() instanceof EmptyNode)) searchAvailableNode(node.getTopL());
        }

        if(node.getTopR().getCard() == null && !(node.getTopR() instanceof EmptyNode)){
            this.AvailableNodes.add(node.getTopR());
        }
        else {
            if(!(node.getTopR()instanceof EmptyNode)) searchAvailableNode(node.getTopR());
        }
    }

    public Node printAndChooseNode() {
        System.out.println("Available Nodes:");
        for (int i = 0; i < AvailableNodes.size(); i++) {
            Node node = AvailableNodes.get(i);
            System.out.println((i + 1) + ": Nodo alla posizione (" + node.getX() + ", " + node.getY() + ")");
        }
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.print("Scegli il numero del nodo (1-" + AvailableNodes.size() + "): ");
        choice = scanner.nextInt();
        return AvailableNodes.get(choice - 1);
    }

    public void UpdatePoints(Card card) {
        counter.AddPoint(card.getPoints());
    }







    //getter e setter
    public List<Node> getAvailableNode() {
        return this.AvailableNodes;
    }

    public Node getStarter() {
        return Starter;
    }
}
