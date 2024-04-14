package org.example.model.PlayArea;
//* import com.sun.java.swing.plaf.windows.TMSchema;
import org.example.model.deck.*;
import org.example.model.deck.enumeration.*;
import org.example.model.deck.enumeration.cast.CastCardRes;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** It is connected to a player through a Hash Map, and contains his Hand and the
 * cards he has played.
 *
 */
public class PlayerCardArea {
    private Card InitialCard; //inizializzata con la playercardarea ma è utile?
    private List<Card> hand = new ArrayList<>(); //non so se serve
    private Node Starter;
    private CounterResources counter = new CounterResources();
    private List<Node> AllNodes; //tutti i nodi inseriti

    private List<Node> AvailableNodes; //tutti i nodi disponibili per ospitare una nuova carta (non contiene start ma topl topr etc di start)

    private List<PlaceHolder> PlaceHolders;

    public static void main(String[] args) throws IOException, ParseException {
        Deck deckStarter= new Deck(Type.STARTER);
        Card card= deckStarter.getCards().get(5);
        card.setSide(1);
        PlayerCardArea playerCardArea= new PlayerCardArea(card);


        Deck deckRes = new Deck(Type.RESOURCES);
        Deck GoldDeck = new Deck(Type.GOLD);
        Card card1= deckRes.getCards().get(0);
        playerCardArea.getHand().add(card1);

        Card card2= deckRes.getCards().get(1);
        playerCardArea.getHand().add(card2);

        Card card3= GoldDeck.getCards().get(2);
        playerCardArea.getHand().add(card3);
        int j=0;
        for(Card c : playerCardArea.getHand()){
            System.out.println(j + ": \n" + c);
            j++;
        }
        /*
        card1.setSide(1);
        playerCardArea.PlayACard(card1);
        Card card2= deckRes.getCards().get(1);
        card2.setSide(2);
        playerCardArea.PlayACard(card2);
        Card card3= deckRes.getCards().get(2);
        card3.setSide(1);
        playerCardArea.PlayACard(card3);


        //debug
        int i = 1;
        System.out.println("Nodi vuoti disponibili: ");
        for (Node node : playerCardArea.getAvailableNodes()) {
            System.out.print(i + ": ");
            System.out.println(node);
            i++;
        }
        System.out.println();

        i=1;
        System.out.println("tutti i nodi: ");
        for (Node node : playerCardArea.getAllNodes()){
            System.out.print(i + ": ");
            System.out.println(node);
            i++;
        }
        System.out.println();

        i=1;
        System.out.println("Lista di placeholders: ");
        for (PlaceHolder placeHolder : playerCardArea.getPlaceHolders()){
            System.out.print(i + ": ");
            System.out.println(placeHolder);
            i++;
        }
        System.out.println();



         */
    }


    public PlayerCardArea(Card cardStarter) {
        this.InitialCard= cardStarter;
        this.AvailableNodes =new ArrayList<>();
        this.AllNodes=new ArrayList<>();
        this.PlaceHolders=new ArrayList<>();
        this.Starter = new Node(cardStarter, 0,0, PlaceHolders, AvailableNodes, AllNodes );
        UpdateCounter(cardStarter);
    }

    public void PlayACard (Card card){
        //schiera la carta passata come parametro dal player in uno dei nodi che scegli il player
        //todo implementare un ciclo che chieda al player su quale carta giocare finche la giocata non è valida oppure segnalare in qualche modo che la giocata non è valida
        Node chosenNode = printAndChoseNode(); //scegliamo il nodo su cui giocare la carta
        ModifyGameArea(card, chosenNode);
    }

    public void ModifyGameArea (Card card, Node node){
        //metodo incaricato di gestire una giocata di un player
        //chiama il metodo di node che imposta la carta scelta al nodo scelto e aggiunge i nodi di default
        node.SetCardNode(card, PlaceHolders, AvailableNodes, AllNodes);
        AvailableNodes.remove(node);

        //Aggiornamento delle risorse
        UpdateCounter(card);
        removeResources(node);

        //todo fare update point

    }


    public void UpdateCounter(Card card) {
        counter.AddResource(card.getSide().getChosenList().get(0).getPropertiesCorner());
        counter.AddResource(card.getSide().getChosenList().get(1).getPropertiesCorner());
        counter.AddResource(card.getSide().getChosenList().get(2).getPropertiesCorner());
        counter.AddResource(card.getSide().getChosenList().get(3).getPropertiesCorner());

        //todo implementare anche il counter di punti, dove lo facciamo?

        //se risorsa o gold e ho scelto il back allora aggiungi card res
        if(card.getCardRes()!= null){
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


    //Facciamo una lista chiedendo al Prof. della disconnessione ed eventualmente procediamo a levarla e fare con una ricorsione
    public void removeResources(Node node){
        for (Node node1 : AllNodes){
            if(node1.getTopR()==node){
                counter.RemoveResource(node1.getCard().getSide().getChosenList().get(1).getPropertiesCorner());
            }
            if(node1.getTopL()==node){
                counter.RemoveResource(node1.getCard().getSide().getChosenList().get(0).getPropertiesCorner());
            }
            if(node1.getBotR()==node){
                counter.RemoveResource(node1.getCard().getSide().getChosenList().get(2).getPropertiesCorner());
            }
            if(node1.getBotL()==node){
                counter.RemoveResource(node1.getCard().getSide().getChosenList().get(3).getPropertiesCorner());
            }
        }
    }


    public Node printAndChoseNode() {
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

    public boolean CheckPlayForGold(Card card){
        if(card.getType()==Type.GOLD){
            for (CardRes cardRes: card.getRequireGold()){
                if (!counter.IsPresent(cardRes))return false;
            }
        }
        return true;
    }


    // todo public Node GetNodeAtXY





    //getter e setter

    public Node getStarter() {
        return Starter;
    }

    public Card getInitialCard() {
        return InitialCard;
    }

    public List<Card> getHand() {
        return hand;
    }

    public CounterResources getCounter() {
        return counter;
    }

    public List<Node> getAllNodes() {
        return AllNodes;
    }

    public List<Node> getAvailableNodes() {
        return AvailableNodes;
    }

    public List<PlaceHolder> getPlaceHolders() {
        return PlaceHolders;
    }
}




/*
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


    public void UpdateAvailableNode(Node node){
        //todo metodo che aggiorna la lista di nodi disponibili, chiamato nel momento in cui setto un nuovo nodo
        if (node.getBotR() instanceof Node){
            AvailableNodes.add((Node)node.getBotR()); //verificare se il cast è corretto
        } else PlaceHolders.add((PlaceHolder)node.getBotR());
        if (node.getBotL() instanceof Node){
            AvailableNodes.add((Node)node.getBotL()); //verificare se il cast è corretto
        } else PlaceHolders.add((PlaceHolder)node.getBotL());
        if (node.getTopL() instanceof Node){
            AvailableNodes.add((Node)node.getTopL()); //verificare se il cast è corretto
        } else PlaceHolders.add((PlaceHolder)node.getTopL());
        if (node.getTopR() instanceof Node){
            AvailableNodes.add((Node)node.getTopR()); //verificare se il cast è corretto
        } else PlaceHolders.add((PlaceHolder)node.getTopR());
    }

    public void UpdateCounter(Card card) {
        if ((card.getSide().getChosenList().get(0).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (card.getSide().getBackCorners().get(0).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
            counter.AddResource(card.getSide().getBackCorners().get(0).getPropertiesCorner());
        } else if ((card.getSide().getBackCorners().get(1).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (card.getSide().getBackCorners().get(1).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
            counter.AddResource(card.getPropCorn(1));
        } else if ((card.getSide().getBackCorners().get(2).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (card.getSide().getBackCorners().get(2).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
            counter.AddResource(card.getPropCorn(2));
        } else if ((card.getSide().getBackCorners().get(3).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (card.getSide().getBackCorners().get(3).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
            counter.AddResource(card.getPropCorn(3));
        }
        //todo implementare anche il counter di punti, dove lo facciamo?

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
 */


