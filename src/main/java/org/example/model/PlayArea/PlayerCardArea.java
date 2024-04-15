package org.example.model.PlayArea;
//* import com.sun.java.swing.plaf.windows.TMSchema;
import org.example.enumeration.CardRes;
import org.example.enumeration.GoldenPoint;
import org.example.enumeration.Side;
import org.example.enumeration.Type;
import org.example.model.Model;
import org.example.model.deck.*;
import org.example.enumeration.cast.CastCardRes;
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
    private Card SecretObjective;
    private Node Starter;
    private Counter counter = new Counter();
    private List<Node> AllNodes; //tutti i nodi inseriti

    private List<Node> AvailableNodes; //tutti i nodi disponibili per ospitare una nuova carta (non contiene start ma topl topr etc di start)
    private List<PlaceHolder> PlaceHolders;
    private List<Node> AlreadyUsed;

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
        Node chosenNode = printAndChooseNode(); //scegliamo il nodo su cui giocare la carta
        ModifyGameArea(card, chosenNode);
    }

    public void ModifyGameArea (Card card, Node node){
        //metodo incaricato di gestire una giocata di un player
        //chiama il metodo di node che imposta la carta scelta al nodo scelto e aggiunge i nodi di default
        node.SetCardNode(card, PlaceHolders, AvailableNodes, AllNodes);
        AvailableNodes.remove(node);

        //Aggiornamento delle risorse
        UpdateCounter(card);
        UpdatePoints(card);
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
        if((card.getType()==Type.STARTER)&&(card.getSide().getSide()== Side.BACK)){
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


    public Node printAndChooseNode() {
        System.out.println("Available Nodes:");
        for (int i = 0; i < AvailableNodes.size(); i++) {
            Node node = AvailableNodes.get(i);
            System.out.println((i + 1) + ": Node at position (" + node.getX() + ", " + node.getY() + ")");
        }
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.print("Choose the node number (1-" + AvailableNodes.size() + "): ");
        choice = scanner.nextInt();
        return AvailableNodes.get(choice - 1);
    }

    public void UpdatePoints(Card card) {
        switch (card.getType()){
            case RESOURCES:
                counter.AddPoint(card.getPoints());
                break;
            case GOLD:
                if(card.getGoldenPoint() == GoldenPoint.NULL){
                    counter.AddPoint(card.getPoints());
                } else if (card.getGoldenPoint() == GoldenPoint.CORNER){
                    counter.AddPoint(card.getCoveredCornerByCard() * card.getPoints());
                } else if (card.getGoldenPoint() == GoldenPoint.INKWELL){
                    counter.AddPoint(card.getPoints() * counter.getInkwellCounter());
                } else if (card.getGoldenPoint() == GoldenPoint.MANUSCRIPT){
                    counter.AddPoint(card.getPoints() * counter.getManuscriptCounter());
                } else if (card.getGoldenPoint() == GoldenPoint.QUILL){
                    counter.AddPoint(card.getPoints() * counter.getQuillCounter());
                }
        }

    }

    //todo ancora da usare--> manca una verifica: posso inserire quella carta oro?
    public boolean CheckPlayForGold(Card card){
        if(card.getType()==Type.GOLD){
            for (CardRes cardRes: card.getRequireGold()){
                if (!counter.IsPresent(cardRes))return false;
            }
        }
        return true;
    }

    // todo public Node GetNodeAtXY
    public Node GetNodeAtXY(int x, int y) {
        for (Node node : AllNodes) {
            if (node.getX() == x && node.getY() == y) {
                return node;
            }
        }
        return null;
    }

    public void publicObjects(Model model) {
        int points=0;
        for (Card card : model.getPublicObjective()) {
            switch (card.getObjectivePoints()) {
                case DIAG:
                    switch (card.getCardRes()) {
                        case FUNGI:
                            for (Node node : AllNodes) {
                                if (node.getCard().getCardRes() == CardRes.FUNGI && FindDiagonal(node, AlreadyUsed)) {
                                    counter.AddPoint(2);
                                }
                            }
                            AlreadyUsed.clear();
                            break;

                        case PLANT:
                            for (Node node : AllNodes) {
                                if (node.getCard().getCardRes() == CardRes.PLANT && FindDiagonal(node, AlreadyUsed)) {
                                    counter.AddPoint(2);
                                }
                            }
                            AlreadyUsed.clear();
                            break;

                        case INSECT:
                            for (Node node : AllNodes) {
                                if (node.getCard().getCardRes() == CardRes.INSECT && FindDiagonal(node, AlreadyUsed)) {
                                    counter.AddPoint(2);
                                }
                            }
                            AlreadyUsed.clear();
                            break;

                        case ANIMAL:
                            for (Node node : AllNodes) {
                                if (node.getCard().getCardRes() == CardRes.ANIMAL && FindDiagonal(node, AlreadyUsed)) {
                                    counter.AddPoint(2);
                                }
                            }
                            AlreadyUsed.clear();
                            break;
                    }
                case RES:
                    switch (card.getCardRes()) {
                        case FUNGI:
                            //TODO PER ORA AGGIUNGO I PUNTI A COUNTER MA FORSE HA PIU SENSO AGGIUNGERLI SOLO AL TABELLONE?
                            int FungiCounter = counter.getFungiCounter();
                            while (FungiCounter >= 3) {
                                points += 2;
                                FungiCounter = -3;
                            }
                            counter.AddPoint(points);
                            break;
                        case PLANT:
                            int PlantCounter = counter.getPlantCounter();
                            while (PlantCounter >= 3) {
                                points += 2;
                                PlantCounter = -3;
                            }
                            counter.AddPoint(points);
                            break;
                        case INSECT:
                            int InsectCounter = counter.getInsectCounter();
                            while (InsectCounter >= 3) {
                                points += 2;
                                InsectCounter = -3;
                            }
                            counter.AddPoint(points);
                            break;
                        case ANIMAL:
                            int AnimalCounter = counter.getAnimalCounter();
                            while (AnimalCounter >= 3) {
                                points += 2;
                                AnimalCounter = -3;
                            }
                            counter.AddPoint(points);
                            break;
                        case QUILL:
                            int QuillCounter = counter.getQuillCounter();
                            while (QuillCounter >= 3) {
                                points += 2;
                                QuillCounter = -3;
                            }
                            counter.AddPoint(points);
                            break;
                        case INKWELL:
                            int InkwellCounter = counter.getInkwellCounter();
                            while (InkwellCounter >= 3) {
                                points += 2;
                                InkwellCounter = -3;
                            }
                            counter.AddPoint(points);
                            break;
                        case MANUSCRIPT:
                            int ManuscriptCounter = counter.getManuscriptCounter();
                            while (ManuscriptCounter >= 3) {
                                points += 2;
                                ManuscriptCounter = -3;
                            }
                            counter.AddPoint(points);
                            break;
                    }
                case REDGREEN:
                    for (Node node : AllNodes) {
                        if (node.getCard().getCardRes() == CardRes.FUNGI && FindRedGreen(node, AlreadyUsed)) {
                            counter.AddPoint(3);
                            AlreadyUsed.clear();
                        }
                    }
                    break;
                case GREENPURPLE:
                    for (Node node : AllNodes) {
                        if (node.getCard().getCardRes() == CardRes.PLANT && FindGreenPurple(node, AlreadyUsed)) {
                            counter.AddPoint(3);
                            AlreadyUsed.clear();
                        }
                    }
                    break;
                case BLUERED:
                    for (Node node : AllNodes) {
                        if (node.getCard().getCardRes() == CardRes.ANIMAL && FindBlueRed(node, AlreadyUsed)) {
                            counter.AddPoint(3);
                            AlreadyUsed.clear();
                        }
                    }
                    break;
                case PURPLEBLUE:
                    for (Node node : AllNodes) {
                        if (node.getCard().getCardRes() == CardRes.INSECT && FindPurpleBlue(node, AlreadyUsed)) {
                            counter.AddPoint(3);
                            AlreadyUsed.clear();
                        }
                    }
                    break;
                case MIX:
                    int QuillCounter = counter.getQuillCounter();
                    int InkwellCounter = counter.getInkwellCounter();
                    int ManuscriptCounter = counter.getManuscriptCounter();
                    while (ManuscriptCounter > 0 && InkwellCounter > 0 && QuillCounter > 0) {
                        counter.AddPoint(3);
                        ManuscriptCounter--;
                        InkwellCounter--;
                        QuillCounter--;
                    }
                    break;
            }
        }
    }

    public void privateObject(Model model) {
        //todo creiamo una nuova lista in cui inseriamo i nodi gia usati e la svuotiamo prima di ogni obbiettivo
        AlreadyUsed=new ArrayList<>();
        int points=0;
        switch (SecretObjective.getObjectivePoints()) {
            case DIAG:
                switch (SecretObjective.getCardRes()) {
                    case FUNGI:
                        for (Node node : AllNodes) {
                            if(node.getCard().getCardRes()==CardRes.FUNGI && FindDiagonal(node, AlreadyUsed)){
                                counter.AddPoint(2);
                            }
                        }
                        AlreadyUsed.clear();
                        break;

                    case PLANT:
                        for (Node node : AllNodes) {
                            if(node.getCard().getCardRes()==CardRes.PLANT && FindDiagonal(node, AlreadyUsed)){
                                counter.AddPoint(2);
                            }
                        }
                        AlreadyUsed.clear();
                        break;

                    case INSECT:
                        for (Node node : AllNodes) {
                            if(node.getCard().getCardRes()==CardRes.INSECT && FindDiagonal(node, AlreadyUsed)){
                                counter.AddPoint(2);
                            }
                        }
                        AlreadyUsed.clear();
                        break;

                    case ANIMAL:
                        for (Node node : AllNodes) {
                            if(node.getCard().getCardRes()==CardRes.ANIMAL && FindDiagonal(node, AlreadyUsed)){
                                counter.AddPoint(2);
                            }
                        }
                        AlreadyUsed.clear();
                        break;
                }
            case RES:
                switch (SecretObjective.getCardRes()) {
                    case FUNGI:
                        //TODO PER ORA AGGIUNGO I PUNTI A COUNTER MA FORSE HA PIU SENSO AGGIUNGERLI SOLO AL TABELLONE?
                        int FungiCounter= counter.getFungiCounter();
                        while (FungiCounter >= 3){
                            points+=2;
                            FungiCounter=-3;
                        }
                        counter.AddPoint(points);
                        break;
                    case PLANT:
                        int PlantCounter = counter.getPlantCounter();
                        while (PlantCounter >= 3) {
                            points += 2;
                            PlantCounter = -3;
                        }
                        counter.AddPoint(points);
                        break;
                    case INSECT:
                        int InsectCounter= counter.getInsectCounter();
                        while (InsectCounter >= 3){
                            points+=2;
                            InsectCounter=-3;
                        }
                        counter.AddPoint(points);
                        break;
                    case ANIMAL:
                        int AnimalCounter= counter.getAnimalCounter();
                        while (AnimalCounter >= 3){
                            points+=2;
                            AnimalCounter=-3;
                        }
                        counter.AddPoint(points);
                        break;
                    case QUILL:
                        int QuillCounter= counter.getQuillCounter();
                        while (QuillCounter >= 3){
                            points+=2;
                            QuillCounter=-3;
                        }
                        counter.AddPoint(points);
                        break;
                    case INKWELL:
                        int InkwellCounter = counter.getInkwellCounter();
                        while (InkwellCounter >= 3){
                            points+=2;
                            InkwellCounter=-3;
                        }
                        counter.AddPoint(points);
                        break;
                    case MANUSCRIPT:
                        int ManuscriptCounter= counter.getManuscriptCounter();
                        while (ManuscriptCounter >= 3){
                            points+=2;
                            ManuscriptCounter=-3;
                        }
                        counter.AddPoint(points);
                        break;
                }
            case REDGREEN:
                for (Node node : AllNodes) {
                    if(node.getCard().getCardRes()==CardRes.FUNGI && FindRedGreen(node, AlreadyUsed)){
                        counter.AddPoint(3);
                        AlreadyUsed.clear();
                    }
                }
                break;
            case GREENPURPLE:
                for (Node node : AllNodes) {
                    if(node.getCard().getCardRes()==CardRes.PLANT && FindGreenPurple(node, AlreadyUsed)){
                        counter.AddPoint(3);
                        AlreadyUsed.clear();
                    }
                }
                break;
            case BLUERED:
                for (Node node : AllNodes) {
                    if(node.getCard().getCardRes()==CardRes.ANIMAL && FindBlueRed(node, AlreadyUsed)){
                        counter.AddPoint(3);
                        AlreadyUsed.clear();
                    }
                }
                break;
            case PURPLEBLUE:
                for (Node node : AllNodes) {
                    if(node.getCard().getCardRes()==CardRes.INSECT && FindPurpleBlue(node, AlreadyUsed)){
                        counter.AddPoint(3);
                        AlreadyUsed.clear();
                    }
                }
                break;
            case MIX:
                int QuillCounter= counter.getQuillCounter();
                int InkwellCounter = counter.getInkwellCounter();
                int ManuscriptCounter= counter.getManuscriptCounter();
                while (ManuscriptCounter > 0 && InkwellCounter > 0 && QuillCounter>0){
                    counter.AddPoint(3);
                    ManuscriptCounter--;
                    InkwellCounter--;
                    QuillCounter--;
                }
                break;
        }
    }

    private boolean FindDiagonal (Node node, List<Node> AlreadyUsed){
        for (Node node1 : AllNodes) {
            //topR
            if(node1.getX()==node.getX()+1 && node1.getY()==node.getY()+1 && !AlreadyUsed.contains(node1) && SameType(node,node1)){
                for (Node node2 : AllNodes) {
                    if (node1.getX()==node2.getX()+1 && node1.getY()==node2.getY()+1 && !AlreadyUsed.contains(node2) && SameType(node,node1)) {
                        AlreadyUsed.add(node);
                        AlreadyUsed.add(node1);
                        AlreadyUsed.add(node2);
                        return true;
                    }
                }
            }
            //topL
            if(node1.getX()==node.getX()-1 && node1.getY()==node.getY()+1 && !AlreadyUsed.contains(node1) && SameType(node,node1)){
                for (Node node2 : AllNodes) {
                    if (node1.getX()==node2.getX()-1 && node1.getY()==node2.getY()+1 && !AlreadyUsed.contains(node2) && SameType(node,node1)) {
                        AlreadyUsed.add(node);
                        AlreadyUsed.add(node1);
                        AlreadyUsed.add(node2);
                        return true;
                    }
                }
            }
            //botR
            if(node1.getX()==node.getX()+1 && node1.getY()==node.getY()-1 && !AlreadyUsed.contains(node1) && SameType(node,node1)){
                for (Node node2 : AllNodes) {
                    if (node1.getX()==node2.getX()+1 && node1.getY()==node2.getY()-1 && !AlreadyUsed.contains(node2) && SameType(node,node1)) {
                        AlreadyUsed.add(node);
                        AlreadyUsed.add(node1);
                        AlreadyUsed.add(node2);
                        return true;
                    }
                }
            }
            //botL
            if(node1.getX()==node.getX()-1 && node1.getY()==node.getY()-1 && !AlreadyUsed.contains(node1) && SameType(node,node1)){
                for (Node node2 : AllNodes) {
                    if (node1.getX()==node2.getX()-1 && node1.getY()==node2.getY()-1 && !AlreadyUsed.contains(node2) && SameType(node,node1)) {
                        AlreadyUsed.add(node);
                        AlreadyUsed.add(node1);
                        AlreadyUsed.add(node2);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean SameType(Node node1, Node node2) {
        return node1.getCard().getCardRes() == node2.getCard().getCardRes();
    }
    private boolean FindRedGreen (Node node, List<Node> AlreadyUsed){
        for (Node node1 : AllNodes) {
            if(node1.getX()==node.getX() && node1.getY()==node.getY()-2 && !AlreadyUsed.contains(node1) && SameType(node,node1)){
                for (Node node2 : AllNodes) {
                    if(node1.getX()==node.getX()+1 && node1.getY()==node.getY()-1 && !AlreadyUsed.contains(node1) && node2.getCard().getCardRes()==CardRes.PLANT){
                        AlreadyUsed.add(node);
                        AlreadyUsed.add(node1);
                        AlreadyUsed.add(node2);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean FindGreenPurple (Node node, List<Node> AlreadyUsed){
        for (Node node1 : AllNodes) {
            if(node1.getX()==node.getX() && node1.getY()==node.getY()-2 && !AlreadyUsed.contains(node1) && SameType(node,node1)){
                for (Node node2 : AllNodes) {
                    if(node1.getX()==node.getX()-1 && node1.getY()==node.getY()-1 && !AlreadyUsed.contains(node1) && node2.getCard().getCardRes()==CardRes.INSECT){
                        AlreadyUsed.add(node);
                        AlreadyUsed.add(node1);
                        AlreadyUsed.add(node2);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean FindBlueRed (Node node, List<Node> AlreadyUsed){
        for (Node node1 : AllNodes) {
            if(node1.getX()==node.getX() && node1.getY()==node.getY()+2 && !AlreadyUsed.contains(node1) && SameType(node,node1)){
                for (Node node2 : AllNodes) {
                    if(node1.getX()==node.getX()+1 && node1.getY()==node.getY()+1 && !AlreadyUsed.contains(node1) && node2.getCard().getCardRes()==CardRes.FUNGI){
                        AlreadyUsed.add(node);
                        AlreadyUsed.add(node1);
                        AlreadyUsed.add(node2);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean FindPurpleBlue (Node node, List<Node> AlreadyUsed){
        for (Node node1 : AllNodes) {
            if(node1.getX()==node.getX() && node1.getY()==node.getY()+2 && !AlreadyUsed.contains(node1) && SameType(node,node1)){
                for (Node node2 : AllNodes) {
                    if(node1.getX()==node.getX()-1 && node1.getY()==node.getY()+1 && !AlreadyUsed.contains(node1) && node2.getCard().getCardRes()==CardRes.ANIMAL){
                        AlreadyUsed.add(node);
                        AlreadyUsed.add(node1);
                        AlreadyUsed.add(node2);
                        return true;
                    }
                }
            }
        }
        return false;
    }



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

    public Counter getCounter() {
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


    public void setInitialCard(Card initialCard) {
        InitialCard = initialCard;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public void setStarter(Node starter) {
        Starter = starter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public void setAllNodes(List<Node> allNodes) {
        AllNodes = allNodes;
    }

    public void setAvailableNodes(List<Node> availableNodes) {
        AvailableNodes = availableNodes;
    }

    public void setPlaceHolders(List<PlaceHolder> placeHolders) {
        PlaceHolders = placeHolders;
    }

    public Card getSecretObjective() {
        return SecretObjective;
    }

    public void setSecretObjective(Card secretObjective) {
        SecretObjective = secretObjective;
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


