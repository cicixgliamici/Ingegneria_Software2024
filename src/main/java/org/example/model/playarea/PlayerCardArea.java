package org.example.model.playarea;

import org.example.enumeration.CardRes;
import org.example.enumeration.GoldenPoint;
import org.example.enumeration.Side;
import org.example.enumeration.Type;
import org.example.model.Model;
import org.example.model.deck.*;
import org.example.enumeration.cast.CastCardRes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** It is connected to a player through a Hash Map, and contains his Hand and the
 * cards he has played.
 *
 */
public class PlayerCardArea {
    private final List<Card> hand = new ArrayList<>();
    private List<Card> tempSecretObjective;
    private Card secretObjective;
    private final Counter counter = new Counter();
    private final List<PlaceHolder> allNodes;

    private final List<PlaceHolder> availableNodes;
    private final List<PlaceHolder> placeHolders;
    private List<PlaceHolder> alreadyUsed;
    private Card cardStarter;

    /*
    public PlayerCardArea(Card cardStarter) {
        this.AvailableNodes =new ArrayList<>();
        this.AllNodes=new ArrayList<>();
        this.PlaceHolders=new ArrayList<>();
        Node starter = new Node(cardStarter, 0, 0, PlaceHolders, AvailableNodes, AllNodes);
        UpdateCounter(cardStarter);
        this.AlreadyUsed = new ArrayList<>();
    }

     */



    public PlayerCardArea(){
        this.availableNodes =new ArrayList<>();
        this.allNodes =new ArrayList<>();
        this.placeHolders =new ArrayList<>();
        this.alreadyUsed = new ArrayList<>();
        this.tempSecretObjective=new ArrayList<>();
    }

    public void setStarterNode(){
        Node starter = new Node(this.cardStarter, 0, 0, placeHolders, availableNodes, allNodes);
        updateCounter(cardStarter);
    }

    /**
     * Places the card on the node chosen by the player
     */
    public void playACard(Card card, PlaceHolder placeHolder){
        //PlaceHolder chosenNode = printAndChooseNode();
        modifyGameArea(card, placeHolder);
    }

    /**
     * Modifies the game area by adding the card on the chosen node
     * and updating the counters and points
     */
    public void modifyGameArea(Card card, PlaceHolder node){
        //metodo incaricato di gestire una giocata di un player
        //chiama il metodo di node che imposta la carta scelta al nodo scelto e aggiunge i nodi di default
        node.setCardNode(card, placeHolders, availableNodes, allNodes);
        availableNodes.remove(node);

        //Aggiornamento delle risorse
        updateCounter(card);
        updatePoints(card);
        removeResources(node);
    }

    /**
     * Updates the counter by adding all the resources on the 4 corners
     * and in case it's a resource or gold card placed on it's back side
     * the method also adds the card resource
     */
    public void updateCounter(Card card) {
        counter.addResource(card.getSide().getChosenList().get(0).getPropertiesCorner());


        counter.addResource(card.getSide().getChosenList().get(1).getPropertiesCorner());


        counter.addResource(card.getSide().getChosenList().get(2).getPropertiesCorner());


        counter.addResource(card.getSide().getChosenList().get(3).getPropertiesCorner());


        //se risorsa o gold e ho scelto il back allora aggiungi card res
        if(card.getSide().getSide().equals(Side.BACK) && (card.getType()==Type.RESOURCES || card.getType()==Type.GOLD)){
            CardRes cardRes= card.getCardRes();
            CastCardRes castCardRes= new CastCardRes(cardRes);
            counter.addResource(castCardRes.getPropertiesCorner());
        }
        //se starter e hai scelto il front allora add require gold
        if((card.getType()==Type.STARTER)&&(card.getSide().getSide()== Side.FRONT)){
            for (CardRes cardRes: card.getRequireGold()){
                CastCardRes castCardRes= new CastCardRes(cardRes);
                counter.addResource(castCardRes.getPropertiesCorner());
            }
        }
    }

    /**
     * Updates the counter by removing a resource when it
     * gets covered by another card
     */
    //Facciamo una lista chiedendo al Prof. della disconnessione ed eventualmente procediamo a levarla e fare con una ricorsione
    public void removeResources(PlaceHolder node){
        for (PlaceHolder node1 : allNodes){
            if(node1.getTopR()==node){
                counter.removeResource(node1.getCard().getSide().getChosenList().get(1).getPropertiesCorner());
            }
            if(node1.getTopL()==node){
                counter.removeResource(node1.getCard().getSide().getChosenList().get(0).getPropertiesCorner());
            }
            if(node1.getBotR()==node){
                counter.removeResource(node1.getCard().getSide().getChosenList().get(2).getPropertiesCorner());
            }
            if(node1.getBotL()==node){
                counter.removeResource(node1.getCard().getSide().getChosenList().get(3).getPropertiesCorner());
            }
        }
    }


    // public PlaceHolder printAndChooseNode() {
        /*System.out.println("Available Nodes:");
        //for (int i = 0; i < AvailableNodes.size(); i++) {
            //PlaceHolder node = AvailableNodes.get(i);
            //System.out.println((i + 1) + ": Node at position (" + node.getX() + ", " + node.getY() + ")");
        //}
        //Scanner scanner = new Scanner(System.in);
        //int choice;
        //System.out.print("Choose the node number (1-" + AvailableNodes.size() + "): ");
        //choice = scanner.nextInt();
        return AvailableNodes.get(choice - 1);*/
    // }

    /**
     * Calculates and adds the correct amount of points
     */
    public void updatePoints(Card card) {
        switch (card.getType()){
            case RESOURCES:
                counter.addPoint(card.getPoints());
                break;
            case GOLD:
                if(card.getGoldenPoint() == GoldenPoint.NULL){
                    counter.addPoint(card.getPoints());
                } else if (card.getGoldenPoint() == GoldenPoint.CORNER){
                    counter.addPoint(card.getCoveredCornerByCard() * card.getPoints());
                } else if (card.getGoldenPoint() == GoldenPoint.INKWELL){
                    counter.addPoint(card.getPoints() * counter.getInkwellCounter());
                } else if (card.getGoldenPoint() == GoldenPoint.MANUSCRIPT){
                    counter.addPoint(card.getPoints() * counter.getManuscriptCounter());
                } else if (card.getGoldenPoint() == GoldenPoint.QUILL){
                    counter.addPoint(card.getPoints() * counter.getQuillCounter());
                }
        }
    }

    /**
     * Checks whether a player can place a gold card
     * Returns true if he can't
     */
    public boolean checkPlayForGold(Card card){
        List<CardRes> cardResList= Arrays.asList(card.getRequireGold());
            for (CardRes cardRes : card.getRequireGold()) {
                int requiredAmount = (int) cardResList.stream()
                        .filter(res -> res.equals(cardRes))
                        .count();
                if (!counter.isPresent(cardRes, requiredAmount))
                    return true;
            }
            return false;
    }


    /**
     * Checks whether a player has completed the public objectives
     * and if so adds the points to the player scoreboard
     */
    public void publicObjective(Model model) {
        int points=0;
        for (Card card : model.getPublicObjective()) {
            switch (card.getObjectivePoints()) {
                case DIAG:
                    switch (card.getCardRes()) {
                        case FUNGI:
                            for (PlaceHolder node : allNodes) {
                                if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.FUNGI && findDiagonal(node, alreadyUsed)) {
                                    counter.addPoint(2);
                                    counter.addObjectiveCounter();
                                }
                            }
                            alreadyUsed.clear();
                            break;

                        case PLANT:
                            for (PlaceHolder node : allNodes) {
                                if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.PLANT && findDiagonal(node, alreadyUsed)) {
                                    counter.addPoint(2);
                                    counter.addObjectiveCounter();
                                }
                            }
                            alreadyUsed.clear();
                            break;

                        case INSECT:
                            for (PlaceHolder node : allNodes) {
                                if (!alreadyUsed.contains(node)&&node.getCard().getCardRes() == CardRes.INSECT && findDiagonal(node, alreadyUsed)) {
                                    counter.addPoint(2);
                                    counter.addObjectiveCounter();
                                }
                            }
                            alreadyUsed.clear();
                            break;

                        case ANIMAL:
                            for (PlaceHolder node : allNodes) {
                                if (!alreadyUsed.contains(node)&&node.getCard().getCardRes() == CardRes.ANIMAL && findDiagonal(node, alreadyUsed)) {
                                    counter.addPoint(2);
                                    counter.addObjectiveCounter();
                                }
                            }
                            alreadyUsed.clear();
                            break;
                    }
                    break;
                case RES:
                    switch (card.getCardRes()) {
                        case FUNGI:
                            int FungiCounter = counter.getFungiCounter();
                            while (FungiCounter >= 3) {
                                points += 2;
                                FungiCounter = -3;
                                counter.addObjectiveCounter();
                            }
                            counter.addPoint(points);

                            break;
                        case PLANT:
                            int PlantCounter = counter.getPlantCounter();
                            while (PlantCounter >= 3) {
                                points += 2;
                                PlantCounter = -3;
                                counter.addObjectiveCounter();
                            }
                            counter.addPoint(points);

                            break;
                        case INSECT:
                            int InsectCounter = counter.getInsectCounter();
                            while (InsectCounter >= 3) {
                                points += 2;
                                InsectCounter = -3;
                                counter.addObjectiveCounter();
                            }
                            counter.addPoint(points);

                            break;
                        case ANIMAL:
                            int AnimalCounter = counter.getAnimalCounter();
                            while (AnimalCounter >= 3) {
                                points += 2;
                                AnimalCounter = -3;
                                counter.addObjectiveCounter();
                            }
                            counter.addPoint(points);

                            break;
                        case QUILL:
                            int QuillCounter = counter.getQuillCounter();
                            while (QuillCounter >= 3) {
                                points += 2;
                                QuillCounter = -3;
                                counter.addObjectiveCounter();
                            }
                            counter.addPoint(points);

                            break;
                        case INKWELL:
                            int InkwellCounter = counter.getInkwellCounter();
                            while (InkwellCounter >= 3) {
                                points += 2;
                                InkwellCounter = -3;
                                counter.addObjectiveCounter();
                            }
                            counter.addPoint(points);

                            break;
                        case MANUSCRIPT:
                            int ManuscriptCounter = counter.getManuscriptCounter();
                            while (ManuscriptCounter >= 3) {
                                points += 2;
                                ManuscriptCounter = -3;
                                counter.addObjectiveCounter();
                            }
                            counter.addPoint(points);
                            break;
                    }
                    break;
                case REDGREEN:
                    for (PlaceHolder node : allNodes) {
                        if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.FUNGI && findRedGreen(node, alreadyUsed)) {
                            counter.addPoint(3);
                            counter.addObjectiveCounter();
                            alreadyUsed.clear();
                        }
                    }
                    break;
                case GREENPURPLE:
                    for (PlaceHolder node : allNodes) {
                        if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.PLANT && findGreenPurple(node, alreadyUsed)) {
                            counter.addPoint(3);
                            counter.addObjectiveCounter();
                            alreadyUsed.clear();
                        }
                    }
                    break;
                case BLUERED:
                    for (PlaceHolder node : allNodes) {
                        if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.ANIMAL && findBlueRed(node, alreadyUsed)) {
                            counter.addPoint(3);
                            counter.addObjectiveCounter();
                            alreadyUsed.clear();
                        }
                    }
                    break;
                case PURPLEBLUE:
                    for (PlaceHolder node : allNodes) {
                        if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.INSECT && findPurpleBlue(node, alreadyUsed)) {
                            counter.addPoint(3);
                            counter.addObjectiveCounter();
                            alreadyUsed.clear();
                        }
                    }
                    break;
                case MIX:
                    int QuillCounter = counter.getQuillCounter();
                    int InkwellCounter = counter.getInkwellCounter();
                    int ManuscriptCounter = counter.getManuscriptCounter();
                    while (ManuscriptCounter > 0 && InkwellCounter > 0 && QuillCounter > 0) {
                        counter.addPoint(3);
                        counter.addObjectiveCounter();
                        ManuscriptCounter--;
                        InkwellCounter--;
                        QuillCounter--;
                    }
                    break;
            }
        }
    }
    /**
     * Checks whether a player has completed his private objectives
     * and if so adds the points to the player scoreboard
     */
    public void privateObjective() {
        alreadyUsed =new ArrayList<>();
        int points=0;
        switch (secretObjective.getObjectivePoints()) {
            case DIAG:
                switch (secretObjective.getCardRes()) {
                    case FUNGI:
                        for (PlaceHolder node : allNodes) {
                            if(!alreadyUsed.contains(node) && node.getCard().getCardRes()==CardRes.FUNGI && findDiagonal(node, alreadyUsed)){
                                counter.addPoint(2);
                                counter.addObjectiveCounter();
                            }
                        }
                        alreadyUsed.clear();
                        break;
                    case PLANT:
                        for (PlaceHolder node : allNodes) {
                            if(!alreadyUsed.contains(node) && node.getCard().getCardRes()==CardRes.PLANT && findDiagonal(node, alreadyUsed)){
                                counter.addPoint(2);
                                counter.addObjectiveCounter();
                            }
                        }
                        alreadyUsed.clear();
                        break;

                    case INSECT:
                        for (PlaceHolder node : allNodes) {
                            if(!alreadyUsed.contains(node) && node.getCard().getCardRes()==CardRes.INSECT && findDiagonal(node, alreadyUsed)){
                                counter.addPoint(2);
                                counter.addObjectiveCounter();
                            }
                        }
                        alreadyUsed.clear();
                        break;

                    case ANIMAL:
                        for (PlaceHolder node : allNodes) {
                            if(!alreadyUsed.contains(node) && node.getCard().getCardRes()==CardRes.ANIMAL && findDiagonal(node, alreadyUsed)){
                                counter.addPoint(2);
                                counter.addObjectiveCounter();
                            }
                        }
                        alreadyUsed.clear();
                        break;
                }
                break;
            case RES:
                switch (secretObjective.getCardRes()) {
                    case FUNGI:
                        int FungiCounter= counter.getFungiCounter();
                        while (FungiCounter >= 3){
                            points+=2;
                            FungiCounter=-3;
                            counter.addObjectiveCounter();
                        }
                        counter.addPoint(points);

                        break;
                    case PLANT:
                        int PlantCounter = counter.getPlantCounter();
                        while (PlantCounter >= 3) {
                            points += 2;
                            PlantCounter = -3;
                            counter.addObjectiveCounter();
                        }
                        counter.addPoint(points);
                        break;
                    case INSECT:
                        int InsectCounter= counter.getInsectCounter();
                        while (InsectCounter >= 3){
                            points+=2;
                            InsectCounter=-3;
                            counter.addObjectiveCounter();
                        }
                        counter.addPoint(points);
                        break;
                    case ANIMAL:
                        int AnimalCounter= counter.getAnimalCounter();
                        while (AnimalCounter >= 3){
                            points+=2;
                            AnimalCounter=-3;
                            counter.addObjectiveCounter();
                        }
                        counter.addPoint(points);
                        break;
                    case QUILL:
                        int QuillCounter= counter.getQuillCounter();
                        while (QuillCounter >= 3){
                            points+=2;
                            QuillCounter=-3;
                            counter.addObjectiveCounter();
                        }
                        counter.addPoint(points);
                        break;
                    case INKWELL:
                        int InkwellCounter = counter.getInkwellCounter();
                        while (InkwellCounter >= 3){
                            points+=2;
                            InkwellCounter=-3;
                            counter.addObjectiveCounter();
                        }
                        counter.addPoint(points);
                        break;
                    case MANUSCRIPT:
                        int ManuscriptCounter= counter.getManuscriptCounter();
                        while (ManuscriptCounter >= 3){
                            points+=2;
                            ManuscriptCounter=-3;
                            counter.addObjectiveCounter();
                        }
                        counter.addPoint(points);
                        break;
                }
                break;
            case REDGREEN:
                for (PlaceHolder node : allNodes) {
                    if(!alreadyUsed.contains(node) && node.getCard().getCardRes()==CardRes.FUNGI && findRedGreen(node, alreadyUsed)){
                        counter.addPoint(3);
                        counter.addObjectiveCounter();
                    }
                }
                alreadyUsed.clear();
                break;
            case GREENPURPLE:
                for (PlaceHolder node : allNodes) {
                    if(!alreadyUsed.contains(node) && node.getCard().getCardRes()==CardRes.PLANT && findGreenPurple(node, alreadyUsed)){
                        counter.addPoint(3);
                        counter.addObjectiveCounter();
                        alreadyUsed.clear();
                    }
                }
                break;
            case BLUERED:
                for (PlaceHolder node : allNodes) {
                    if(!alreadyUsed.contains(node) && node.getCard().getCardRes()==CardRes.ANIMAL && findBlueRed(node, alreadyUsed)){
                        counter.addPoint(3);
                        counter.addObjectiveCounter();
                        alreadyUsed.clear();
                    }
                }
                break;
            case PURPLEBLUE:
                for (PlaceHolder node : allNodes) {
                    if(!alreadyUsed.contains(node) && node.getCard().getCardRes()==CardRes.INSECT && findPurpleBlue(node, alreadyUsed)){
                        counter.addPoint(3);
                        counter.addObjectiveCounter();
                        alreadyUsed.clear();
                    }
                }
                break;
            case MIX:
                int QuillCounter= counter.getQuillCounter();
                int InkwellCounter = counter.getInkwellCounter();
                int ManuscriptCounter= counter.getManuscriptCounter();
                while (ManuscriptCounter > 0 && InkwellCounter > 0 && QuillCounter>0){
                    counter.addPoint(3);
                    counter.addObjectiveCounter();
                    ManuscriptCounter--;
                    InkwellCounter--;
                    QuillCounter--;
                }
                break;
        }
    }

    /**
     * Returns true if three cards of the same type are placed on a diagonal
     * Useful for calculating the diagonal objectives
     */
    public boolean findDiagonal(PlaceHolder node, List<PlaceHolder> AlreadyUsed){
        for (PlaceHolder node1 : allNodes) {
            //topR
            if(node1.getX()==node.getX()+1 && node1.getY()==node.getY()+1 && !AlreadyUsed.contains(node1) && sameType(node,node1)){
                for (PlaceHolder node2 : allNodes) {
                    if (node2.getX()==node1.getX()+1 && node2.getY()==node1.getY()+1 && !AlreadyUsed.contains(node2) && sameType(node,node1)) {
                        AlreadyUsed.add(node);
                        AlreadyUsed.add(node1);
                        AlreadyUsed.add(node2);
                        return true;
                    }
                }
            }
            //topL
            if(node1.getX()==node.getX()-1 && node1.getY()==node.getY()+1 && !AlreadyUsed.contains(node1) && sameType(node,node1)){
                for (PlaceHolder node2 : allNodes) {
                    if (node2.getX()==node1.getX()-1 && node2.getY()==node1.getY()+1 && !AlreadyUsed.contains(node2) && sameType(node,node1)) {
                        AlreadyUsed.add(node);
                        AlreadyUsed.add(node1);
                        AlreadyUsed.add(node2);
                        return true;
                    }
                }
            }
            //botR
            if(node1.getX()==node.getX()+1 && node1.getY()==node.getY()-1 && !AlreadyUsed.contains(node1) && sameType(node,node1)){
                for (PlaceHolder node2 : allNodes) {
                    if (node2.getX()==node1.getX()+1 && node2.getY()==node1.getY()-1 && !AlreadyUsed.contains(node2) && sameType(node,node1)) {
                        AlreadyUsed.add(node);
                        AlreadyUsed.add(node1);
                        AlreadyUsed.add(node2);
                        return true;
                    }
                }
            }
            //botL
            if(node1.getX()==node.getX()-1 && node1.getY()==node.getY()-1 && !AlreadyUsed.contains(node1) && sameType(node,node1)){
                for (PlaceHolder node2 : allNodes) {
                    if (node2.getX()==node1.getX()-1 && node2.getY()==node1.getY()-1 && !AlreadyUsed.contains(node2) && sameType(node,node1)) {
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

    /**
     * Returns true od two cards are of the same type
     */
    public boolean sameType(PlaceHolder node1, PlaceHolder node2) {
        return node1.getCard().getCardRes() == node2.getCard().getCardRes();
    }

    /**
     * The following methods are used to calculate L objectives
     */
    public boolean findRedGreen(PlaceHolder node, List<PlaceHolder> AlreadyUsed){
        for (PlaceHolder node1 : allNodes) {
            if(node1.getX()==node.getX() && node1.getY()==node.getY()-2 && !AlreadyUsed.contains(node1) && sameType(node,node1)){
                for (PlaceHolder node2 : allNodes) {
                    if(node2.getX()==node1.getX()+1 && node2.getY()==node1.getY()-1 && !AlreadyUsed.contains(node2) && node2.getCard().getCardRes()==CardRes.PLANT){
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
    public boolean findGreenPurple(PlaceHolder node, List<PlaceHolder> AlreadyUsed){
        for (PlaceHolder node1 : allNodes) {
            if(node1.getX()==node.getX() && node1.getY()==node.getY()-2 && !AlreadyUsed.contains(node1) && sameType(node,node1)){
                for (PlaceHolder node2 : allNodes) {
                    if(node2.getX()==node1.getX()-1 && node2.getY()==node1.getY()-1 && !AlreadyUsed.contains(node2) && node2.getCard().getCardRes()==CardRes.INSECT){
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
    public boolean findBlueRed(PlaceHolder node, List<PlaceHolder> AlreadyUsed){
        for (PlaceHolder node1 : allNodes) {
            if(node1.getX()==node.getX() && node1.getY()==node.getY()+2 && !AlreadyUsed.contains(node1) && sameType(node,node1)){
                for (PlaceHolder node2 : allNodes) {
                    if(node2.getX()==node1.getX()+1 && node2.getY()==node1.getY()+1 && !AlreadyUsed.contains(node2) && node2.getCard().getCardRes()==CardRes.FUNGI){
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
    public boolean findPurpleBlue(PlaceHolder node, List<PlaceHolder> AlreadyUsed){
        for (PlaceHolder node1 : allNodes) {
            if(node1.getX()==node.getX() && node1.getY()==node.getY()+2 && !AlreadyUsed.contains(node1) && sameType(node,node1)){
                for (PlaceHolder node2 : allNodes) {
                    if(node2.getX()==node1.getX()-1 && node2.getY()==node1.getY()+1 && !AlreadyUsed.contains(node2) && node2.getCard().getCardRes()==CardRes.ANIMAL){
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


    /** getter e setter zone
     */

    public List<Card> getHand() {
        return hand;
    }

    public Counter getCounter() {
        return counter;
    }

    public PlaceHolder getNodeByXY(int x, int y){
        for (PlaceHolder p: allNodes){
            if(p.getX()==x && p.getY()==y){
                return p;
            }
        }
        return null;
    }

    //todo modificare il metodo in modo tale che il giocatore mandi la sua scelta dopo averla vista !
    public void setSecretObjective(Card secretObjective) {
        this.secretObjective = secretObjective;
    }

    public Card getSecretObjective() {
        return secretObjective;
    }

    public List<PlaceHolder> getAvailableNodes() {
        return availableNodes;
    }

    public List<PlaceHolder> getPlaceHolders() {
        return placeHolders;
    }

    public List<PlaceHolder> getAlreadyUsed() {
        return alreadyUsed;
    }

    public List<PlaceHolder> getAllNodes() {
        return allNodes;
    }

    public List<Card> getTempSecretObjective() {
        return tempSecretObjective;
    }

    public Card getCardStarter() {
        return cardStarter;
    }

    public void setCardStarter(Card cardStarter) {
        this.cardStarter = cardStarter;
    }
}