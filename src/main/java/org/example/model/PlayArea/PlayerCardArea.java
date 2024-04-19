package org.example.model.PlayArea;

import org.example.enumeration.CardRes;
import org.example.enumeration.GoldenPoint;
import org.example.enumeration.Side;
import org.example.enumeration.Type;
import org.example.model.Model;
import org.example.model.deck.*;
import org.example.enumeration.cast.CastCardRes;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** It is connected to a player through a Hash Map, and contains his Hand and the
 * cards he has played.
 *
 */
public class PlayerCardArea {
    private final List<Card> hand = new ArrayList<>(); //non so se serve
    private Card SecretObjective;
    private final Counter counter = new Counter();
    private final List<PlaceHolder> AllNodes; //tutti i nodi inseriti

    private final List<PlaceHolder> AvailableNodes; //tutti i nodi disponibili per ospitare una nuova carta (non contiene start ma topl topr etc di start)
    private final List<PlaceHolder> PlaceHolders;
    private List<PlaceHolder> AlreadyUsed;

    public PlayerCardArea(Card cardStarter) {
        this.AvailableNodes =new ArrayList<>();
        this.AllNodes=new ArrayList<>();
        this.PlaceHolders=new ArrayList<>();
        Node starter = new Node(cardStarter, 0, 0, PlaceHolders, AvailableNodes, AllNodes);
        UpdateCounter(cardStarter);
    }

    public void PlayACard (Card card){
        //schiera la carta passata come parametro dal player in uno dei nodi che scegli il player
        PlaceHolder chosenNode = printAndChooseNode(); //scegliamo il nodo su cui giocare la carta
        ModifyGameArea(card, chosenNode);
    }

    public void ModifyGameArea (Card card, PlaceHolder node){
        //metodo incaricato di gestire una giocata di un player
        //chiama il metodo di node che imposta la carta scelta al nodo scelto e aggiunge i nodi di default
        node.SetCardNode(card, PlaceHolders, AvailableNodes, AllNodes);
        AvailableNodes.remove(node);

        //Aggiornamento delle risorse
        UpdateCounter(card);
        UpdatePoints(card);
        removeResources(node);
    }


    public void UpdateCounter(Card card) {
        counter.AddResource(card.getSide().getChosenList().get(0).getPropertiesCorner());
        counter.AddResource(card.getSide().getChosenList().get(1).getPropertiesCorner());
        counter.AddResource(card.getSide().getChosenList().get(2).getPropertiesCorner());
        counter.AddResource(card.getSide().getChosenList().get(3).getPropertiesCorner());

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
    public void removeResources(PlaceHolder node){
        for (PlaceHolder node1 : AllNodes){
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


    //public PlaceHolder printAndChooseNode() {
        //System.out.println("Available Nodes:");
        //for (int i = 0; i < AvailableNodes.size(); i++) {
            //PlaceHolder node = AvailableNodes.get(i);
            //System.out.println((i + 1) + ": Node at position (" + node.getX() + ", " + node.getY() + ")");
        //}
        //Scanner scanner = new Scanner(System.in);
        //int choice;
        //System.out.print("Choose the node number (1-" + AvailableNodes.size() + "): ");
        //choice = scanner.nextInt();
        //return AvailableNodes.get(choice - 1);
    //}

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

    public boolean CheckPlayForGold(Card card){
        if(card.getType()==Type.GOLD){
            for (CardRes cardRes: card.getRequireGold()){
                if (!counter.IsPresent(cardRes))return true;
            }
        }
        return false;
    }


    public void publicObjects(Model model) {
        int points=0;
        for (Card card : model.getPublicObjective()) {
            switch (card.getObjectivePoints()) {
                case DIAG:
                    switch (card.getCardRes()) {
                        case FUNGI:
                            for (PlaceHolder node : AllNodes) {
                                if (node.getCard().getCardRes() == CardRes.FUNGI && FindDiagonal(node, AlreadyUsed)) {
                                    counter.AddPoint(2);
                                }
                            }
                            AlreadyUsed.clear();
                            break;

                        case PLANT:
                            for (PlaceHolder node : AllNodes) {
                                if (node.getCard().getCardRes() == CardRes.PLANT && FindDiagonal(node, AlreadyUsed)) {
                                    counter.AddPoint(2);
                                }
                            }
                            AlreadyUsed.clear();
                            break;

                        case INSECT:
                            for (PlaceHolder node : AllNodes) {
                                if (node.getCard().getCardRes() == CardRes.INSECT && FindDiagonal(node, AlreadyUsed)) {
                                    counter.AddPoint(2);
                                }
                            }
                            AlreadyUsed.clear();
                            break;

                        case ANIMAL:
                            for (PlaceHolder node : AllNodes) {
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
                    for (PlaceHolder node : AllNodes) {
                        if (node.getCard().getCardRes() == CardRes.FUNGI && FindRedGreen(node, AlreadyUsed)) {
                            counter.AddPoint(3);
                            AlreadyUsed.clear();
                        }
                    }
                    break;
                case GREENPURPLE:
                    for (PlaceHolder node : AllNodes) {
                        if (node.getCard().getCardRes() == CardRes.PLANT && FindGreenPurple(node, AlreadyUsed)) {
                            counter.AddPoint(3);
                            AlreadyUsed.clear();
                        }
                    }
                    break;
                case BLUERED:
                    for (PlaceHolder node : AllNodes) {
                        if (node.getCard().getCardRes() == CardRes.ANIMAL && FindBlueRed(node, AlreadyUsed)) {
                            counter.AddPoint(3);
                            AlreadyUsed.clear();
                        }
                    }
                    break;
                case PURPLEBLUE:
                    for (PlaceHolder node : AllNodes) {
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

    public void privateObject() {
        AlreadyUsed=new ArrayList<>();
        int points=0;
        switch (SecretObjective.getObjectivePoints()) {
            case DIAG:
                switch (SecretObjective.getCardRes()) {
                    case FUNGI:
                        for (PlaceHolder node : AllNodes) {
                            if(node.getCard().getCardRes()==CardRes.FUNGI && FindDiagonal(node, AlreadyUsed)){
                                counter.AddPoint(2);
                            }
                        }
                        AlreadyUsed.clear();
                        break;

                    case PLANT:
                        for (PlaceHolder node : AllNodes) {
                            if(node.getCard().getCardRes()==CardRes.PLANT && FindDiagonal(node, AlreadyUsed)){
                                counter.AddPoint(2);
                            }
                        }
                        AlreadyUsed.clear();
                        break;

                    case INSECT:
                        for (PlaceHolder node : AllNodes) {
                            if(node.getCard().getCardRes()==CardRes.INSECT && FindDiagonal(node, AlreadyUsed)){
                                counter.AddPoint(2);
                            }
                        }
                        AlreadyUsed.clear();
                        break;

                    case ANIMAL:
                        for (PlaceHolder node : AllNodes) {
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
                for (PlaceHolder node : AllNodes) {
                    if(node.getCard().getCardRes()==CardRes.FUNGI && FindRedGreen(node, AlreadyUsed)){
                        counter.AddPoint(3);
                        AlreadyUsed.clear();
                    }
                }
                break;
            case GREENPURPLE:
                for (PlaceHolder node : AllNodes) {
                    if(node.getCard().getCardRes()==CardRes.PLANT && FindGreenPurple(node, AlreadyUsed)){
                        counter.AddPoint(3);
                        AlreadyUsed.clear();
                    }
                }
                break;
            case BLUERED:
                for (PlaceHolder node : AllNodes) {
                    if(node.getCard().getCardRes()==CardRes.ANIMAL && FindBlueRed(node, AlreadyUsed)){
                        counter.AddPoint(3);
                        AlreadyUsed.clear();
                    }
                }
                break;
            case PURPLEBLUE:
                for (PlaceHolder node : AllNodes) {
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

    private boolean FindDiagonal (PlaceHolder node, List<PlaceHolder> AlreadyUsed){
        for (PlaceHolder node1 : AllNodes) {
            //topR
            if(node1.getX()==node.getX()+1 && node1.getY()==node.getY()+1 && !AlreadyUsed.contains(node1) && SameType(node,node1)){
                for (PlaceHolder node2 : AllNodes) {
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
                for (PlaceHolder node2 : AllNodes) {
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
                for (PlaceHolder node2 : AllNodes) {
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
                for (PlaceHolder node2 : AllNodes) {
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

    public boolean SameType(PlaceHolder node1, PlaceHolder node2) {
        return node1.getCard().getCardRes() == node2.getCard().getCardRes();
    }
    private boolean FindRedGreen (PlaceHolder node, List<PlaceHolder> AlreadyUsed){
        for (PlaceHolder node1 : AllNodes) {
            if(node1.getX()==node.getX() && node1.getY()==node.getY()-2 && !AlreadyUsed.contains(node1) && SameType(node,node1)){
                for (PlaceHolder node2 : AllNodes) {
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
    private boolean FindGreenPurple (PlaceHolder node, List<PlaceHolder> AlreadyUsed){
        for (PlaceHolder node1 : AllNodes) {
            if(node1.getX()==node.getX() && node1.getY()==node.getY()-2 && !AlreadyUsed.contains(node1) && SameType(node,node1)){
                for (PlaceHolder node2 : AllNodes) {
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
    private boolean FindBlueRed (PlaceHolder node, List<PlaceHolder> AlreadyUsed){
        for (PlaceHolder node1 : AllNodes) {
            if(node1.getX()==node.getX() && node1.getY()==node.getY()+2 && !AlreadyUsed.contains(node1) && SameType(node,node1)){
                for (PlaceHolder node2 : AllNodes) {
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
    private boolean FindPurpleBlue (PlaceHolder node, List<PlaceHolder> AlreadyUsed){
        for (PlaceHolder node1 : AllNodes) {
            if(node1.getX()==node.getX() && node1.getY()==node.getY()+2 && !AlreadyUsed.contains(node1) && SameType(node,node1)){
                for (PlaceHolder node2 : AllNodes) {
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

    public List<Card> getHand() {
        return hand;
    }

    public Counter getCounter() {
        return counter;
    }

    public void setSecretObjective(Card secretObjective) {
        SecretObjective = secretObjective;
    }
}
