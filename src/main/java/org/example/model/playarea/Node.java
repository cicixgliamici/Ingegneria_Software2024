package org.example.model.playarea;

import org.example.model.deck.Card;
import java.util.List;

/**
 * Class for the placement of cards.
 * The node is an Object with 4 connections to other nodes.
 * You can go to every node only from the beginner.
 */

public class Node extends PlaceHolder{
    private Card card;
    private PlaceHolder topL;
    private PlaceHolder topR;
    private PlaceHolder botL;
    private PlaceHolder botR;


    public Node(Card card, int x, int y, List<PlaceHolder> placeHolderList, List<PlaceHolder> AvailableNodes, List<PlaceHolder> AllNodes ) {
        super (x,y);
        this.card = card;
        this.setPlaceHolderByCard(AvailableNodes, placeHolderList);
        this.setNullNode(AvailableNodes);
        AllNodes.add(this);
    }
    public Node (Card card, PlaceHolder topL, PlaceHolder topR, PlaceHolder botL, PlaceHolder botR, int x, int y){
        super(x,y);
        this.card=card;
        this.topL= topL;
        this.topR= topR;
        this.botL= botL;
        this.botR= botR;
    }

    /**
     * Creates an empty and unusable node
     * for when the corner is hidden
     */
    public Node nullNodeTopR(){
        int x = this.x+1;
        int y= this.y+1;
        return new Node(null, null, null, this, null,  x, y);
    }
    public Node nullNodeTopL(){
        int x = this.x-1;
        int y= this.y+1;
        return new Node(null, null, null, null, this,  x, y);
    }
    public Node nullNodeBotR(){
        int x = this.x+1;
        int y= this.y-1;
        return new Node(null, this, null, null, null,  x, y);
    }
    public Node nullNodeBotL(){
        int x = this.x-1;
        int y= this.y-1;
        return new Node(null, null, this , null, null,  x, y);
    }

    /** CornerIsHidden return True if is Hidden
     *
     */
    public void setNullNode(List<PlaceHolder> AvailableNodes) {
        if(this.botR==null) {
            this.botR = nullNodeBotR();
            //todo metodo in placeholder a cui passi una lista e se è un nodo lo aggiunge alla lista oppure togliere nodi
            AvailableNodes.add((Node) this.botR);
        }

        if (this.botL == null) {
            this.botL = nullNodeBotL();
            AvailableNodes.add((Node) this.botL);
        }

        if (this.topL == null) {
            this.topL = nullNodeTopL();
            AvailableNodes.add((Node) this.topL);
        }

        if (this.topR == null) {
            this.topR = nullNodeTopR();
            AvailableNodes.add((Node) this.topR);
        }
    }

    public void setPlaceHolderByCard(List<PlaceHolder> AvailableNodes, List<PlaceHolder> PlaceHolders){
        //Check whether the right corner isn't hidden, if so:
        if(this.getCard().BOTRCornerIsHidden()) {
            System.out.println("creato il placeholder per angolo botR");
            //create a new placeholder to be placed on the grid in the correct position and if there were other empty nodes they get replaced with a placeholder
            if(this.botR==null) {
                this.botR = new PlaceHolder(this.x + 1, this.y - 1);
                PlaceHolders.add(this.botR);
                //Check in the availableNodes list whether there is a node in the same position of the placeholder
                if(AvailableNodes!=null && !AvailableNodes.isEmpty()) {
                    for (PlaceHolder node : AvailableNodes) {
                        if(node.x==this.x+1 && node.y==this.y-1){
                            if(node.getTopR()!= null){
                                (node.getTopR()).setBotL(this.botR);
                            }
                            if(node.getBotL()!= null){
                                (node.getBotL()).setTopR(this.botR);
                            }
                            if(node.getBotR()!= null){
                                (node.getBotR()).setTopL(this.botR);
                            }
                            AvailableNodes.remove(node);
                        }

                    }
                }
            }
        }

        if(this.getCard().BOTLCornerIsHidden()) {
            System.out.println("creato il placeholder per angolo botL");
            if(this.botL==null) {
                this.botL = new PlaceHolder(this.x - 1, this.y - 1);
                PlaceHolders.add(this.botL);
                if(AvailableNodes!=null && !AvailableNodes.isEmpty()) {
                    for (PlaceHolder node : AvailableNodes) {
                        if(node.x==this.x-1 && node.y==this.y-1){
                            if(node.getTopL()!= null){
                                (node.getTopL()).setBotR(this.botL);
                            }
                            if(node.getBotL()!= null){
                                (node.getBotL()).setTopR(this.botL);
                            }
                            if(node.getBotR()!= null){
                                (node.getBotR()).setTopL(this.botL);
                            }
                            AvailableNodes.remove(node);
                        }

                    }
                }
            }
        }

        if(this.getCard().TOPRCornerIsHidden()) {
            System.out.println("creato il placeholder per angolo topR");
            if(this.topR==null) {
                this.topR = new PlaceHolder(this.x + 1, this.y + 1);
                PlaceHolders.add(this.topR);
                if(AvailableNodes!=null && !AvailableNodes.isEmpty()) {
                    for (PlaceHolder node : AvailableNodes) {
                        if(node.x==this.x+1 && node.y==this.y+1){
                            if(node.getTopR()!= null){
                                node.getTopR().setBotL(this.topR);
                            }
                            if(node.getBotR()!= null){
                                node.getBotR().setTopL(this.topR);
                            }
                            if(node.getTopL()!= null){
                                node.getTopL().setBotR(this.topR);
                            }
                            AvailableNodes.remove(node);
                        }

                    }
                }
            }


        }
        if(this.getCard().TOPLCornerIsHidden()) {
            System.out.println("creato il placeholder per angolo topL");
            if(this.topL==null) {
                this.topL = new PlaceHolder(this.x - 1, this.y + 1);
                PlaceHolders.add(this.topL);
                if(AvailableNodes!=null && !AvailableNodes.isEmpty()) {
                    for (PlaceHolder node : AvailableNodes) {
                        if(node.x==this.x-1 && node.y==this.y+1){
                            if(node.getTopL()!= null){
                                node.getTopL().setBotR(this.topL);
                            }
                            if(node.getBotL()!= null){
                                node.getBotL().setTopR(this.topL);
                            }
                            if(node.getTopR()!= null){
                                node.getTopR().setBotL(this.topL);
                            }
                            AvailableNodes.remove(node);
                        }

                    }
                }
            }
        }
    }

    /**
     * Checks whether the 4 new nodes don't overlap with any placeholders and
     * if so assigns the placeholder
     */
    public void setNodePlaceHolder(List<PlaceHolder> placeHolderList) {
        if(this.botR==null) {
            for (PlaceHolder placeHolder : placeHolderList) {
                if(placeHolder.x==this.x+1 && placeHolder.y==this.y-1) {
                    this.botR = placeHolder;
                }
            }
        }
        if(this.botL==null) {
            for (PlaceHolder placeHolder : placeHolderList) {
                if(placeHolder.x==this.x-1 && placeHolder.y==this.y-1) {
                    this.botL = placeHolder;
                }
            }
        }
        if(this.topR==null) {
            for (PlaceHolder placeHolder : placeHolderList) {
                if(placeHolder.x==this.x+1 && placeHolder.y==this.y+1) {
                    this.topR = placeHolder;
                }
            }
        }
        if(this.topL==null) {
            for (PlaceHolder placeHolder : placeHolderList) {
                if(placeHolder.x==this.x-1 && placeHolder.y==this.y-1) {
                    this.topL = placeHolder;
                }
            }
        }
    }


    /**
     * Checks whether the new node doesn't overlap with any placed card
     */
    public void setNodeForExistingCard(List<PlaceHolder> AllNodes) {
        if(this.botR==null) {
            for (PlaceHolder node : AllNodes) {
                if(node.x==this.x+1 && node.y==this.y-1) {
                    this.botR = node;
                    this.getCard().setCoveredCornerByCard(this.getCard().getCoveredCornerByCard() + 1);
                }
            }
        }
        if(this.botL==null) {
            for (PlaceHolder node : AllNodes) {
                if(node.x==this.x-1 && node.y==this.y-1) {
                    this.botL = node;
                    this.getCard().setCoveredCornerByCard(this.getCard().getCoveredCornerByCard() + 1);
                }
            }
        }
        if(this.topR==null) {
            for (PlaceHolder node : AllNodes) {
                if(node.x==this.x+1 && node.y==this.y+1) {
                    this.topR = node;
                    this.getCard().setCoveredCornerByCard(this.getCard().getCoveredCornerByCard() + 1);
                }
            }
        }
        if(this.topL==null) {
            for (PlaceHolder node : AllNodes) {
                if(node.x==this.x-1 && node.y==this.y+1) {
                    this.topL = node;
                    this.getCard().setCoveredCornerByCard(this.getCard().getCoveredCornerByCard() + 1);
                }
            }
        }

    }

    /**
     * Checks whether the new node doesn't overlap with any
     * already existing free node
     */
    public void setNodeForNewCard(List<PlaceHolder> AvailableNodes){
        if(this.botR==null) {
            for (PlaceHolder node : AvailableNodes) {
                if(node.x==this.x+1 && node.y==this.y-1) {
                    this.botR = node;
                }
            }
        }
        if(this.botL==null) {
            for (PlaceHolder node : AvailableNodes) {
                if(node.x==this.x-1 && node.y==this.y-1) {
                    this.botL = node;
                }
            }
        }
        if(this.topR==null) {
            for (PlaceHolder node : AvailableNodes) {
                if(node.x==this.x+1 && node.y==this.y+1) {
                    this.topR = node;
                }
            }
        }
        if(this.topL==null) {
            for (PlaceHolder node : AvailableNodes) {
                if(node.x==this.x-1 && node.y==this.y+1) {
                    this.topL = node;
                }
            }
        }
    }

    /**
     * Assigns the card to the new node
     */
    public void setCardNode(Card c, List<PlaceHolder> placeHolderList, List<PlaceHolder> AvailableNodes, List<PlaceHolder> AllNodes){
        this.card=c;
        this.card.setCoveredCornerByCard(1);
        this.setPlaceHolderByCard(AvailableNodes,placeHolderList);
        this.setNodePlaceHolder(placeHolderList);
        this.setNodeForExistingCard(AllNodes);
        this.setNodeForNewCard(AvailableNodes);
        this.setNullNode(AvailableNodes);
        AllNodes.add(this);
    }

    //getter and setter
    public Card getCard() {
        return card;
    }

    public PlaceHolder getTopL() {
        return topL;
    }

    public PlaceHolder getTopR() {
        return topR;
    }

    public PlaceHolder getBotL() {
        return botL;
    }

    public PlaceHolder getBotR() {
        return botR;
    }

    @Override
    public String toString() {
        return "nodo: " + x + " " + y;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setTopL(PlaceHolder topL) {
        this.topL = topL;
    }

    public void setTopR(PlaceHolder topR) {
        this.topR = topR;
    }

    public void setBotL(PlaceHolder botL) {
        this.botL = botL;
    }

    public void setBotR(PlaceHolder botR) {
        this.botR = botR;
    }
}