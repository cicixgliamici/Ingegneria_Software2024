package org.example.model.PlayArea;

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


    public Node(Card card, int x, int y, List<PlaceHolder> placeHolderList, List<Node> AvailableNodes, List<Node> AllNodes ) {
        super (x,y);
        this.card = card;
        this.SetPlaceHolderByCard(AvailableNodes, placeHolderList);
        this.SetNullNode(AvailableNodes);
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

    /**  The node that is created is positioned 
    *    mirror-image of the original one
    */

    public Node NullNodeTopR(){
        int x = this.x+1;
        int y= this.y+1;
        return new Node(null, null, null, this, null,  x, y);
    }
    public Node NullNodeTopL(){
        int x = this.x-1;
        int y= this.y+1;
        return new Node(null, null, null, null, this,  x, y);
    }
    public Node NullNodeBotR(){
        int x = this.x+1;
        int y= this.y-1;
        return new Node(null, this, null, null, null,  x, y);
    }
    public Node NullNodeBotL(){
        int x = this.x-1;
        int y= this.y-1;
        return new Node(null, null, this , null, null,  x, y);
    }

    /** CornerIsHidden return True if is Hidden
     *
     */
    public void SetNullNode(List<Node> AvailableNodes) {
        if(this.botR==null) {
            this.botR = NullNodeBotR();
            AvailableNodes.add((Node) this.botR);
        }

        if (this.botL == null) {
            this.botL = NullNodeBotL();
            AvailableNodes.add((Node) this.botL);
        }

        if (this.topL == null) {
            this.topL = NullNodeTopL();
            AvailableNodes.add((Node) this.topL);
        }

        if (this.topR == null) {
            this.topR = NullNodeTopR();
            AvailableNodes.add((Node) this.topR);
        }
    }

    public void SetPlaceHolderByCard(List<Node> AvailableNodes, List<PlaceHolder> PlaceHolders){
        //controllo che l'angolo destro non sia hidden, se lo è devo:
        if(this.getCard().BOTRCornerIsHidden()) {
            //creo un nuovo placeholder da posizionare nella griglia di gioco alla posizione corretta e se cerano altri nodi vuoti li sostituisco con un placeholder
            if(this.botR==null) {
                this.botR = new PlaceHolder(this.x + 1, this.y - 1);
                PlaceHolders.add(this.botR);
                //controllo nella lista di tutti i nodi disponibili se ce n'è uno alla posizione del nuovo placeholder
                if(AvailableNodes!=null && !AvailableNodes.isEmpty()) {
                    for (Node node : AvailableNodes) {
                        if(node.x==this.x+1 && node.y==this.y-1){
                            //se lo trovo devo andare a sostituire in tutti i nodi piazzati che hanno quel nodo il nuovo placeholder
                            if(node.getTopR()!= null){
                                (node.getTopR()).setBotL(this.botR);
                            }
                            if(node.getBotL()!= null){
                                (node.getBotL()).setTopR(this.botR);
                            }
                            if(node.getBotR()!= null){
                                (node.getBotR()).setTopL(this.botR);
                            }
                        }
                    }
                }
            }
        }

        if(this.getCard().BOTLCornerIsHidden()) {
            if(this.botL==null) {
                this.botL = new PlaceHolder(this.x - 1, this.y - 1);
                PlaceHolders.add(this.botL);
                if(AvailableNodes!=null && !AvailableNodes.isEmpty()) {
                    for (Node node : AvailableNodes) {
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
                        }
                    }
                }
            }
        }

        if(this.getCard().TOPRCornerIsHidden()) {
            if(this.topR==null) {
                this.topR = new PlaceHolder(this.x + 1, this.y + 1);
                PlaceHolders.add(this.topR);
                if(AvailableNodes!=null && !AvailableNodes.isEmpty()) {
                    for (Node node : AvailableNodes) {
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
                        }
                    }
                }
            }


        }
        if(this.getCard().TOPLCornerIsHidden()) {
            if(this.topL==null) {
                this.topL = new PlaceHolder(this.x - 1, this.y + 1);
                PlaceHolders.add(this.topL);
                if(AvailableNodes!=null && !AvailableNodes.isEmpty()) {
                    for (Node node : AvailableNodes) {
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
                        }
                    }
                }
            }
        }
    }

    //metodo che verifica che i 4 nuovi nodi che andrai a creare non vadano a sovrapporsi a un placeholder e nel casso gli assegna il place holder
    public void SetNodePlaceHolder(List<PlaceHolder> placeHolderList) {
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

    // metodo che deve verificare che il nuovo nodo non vada a sovrapporsi con nessuna carta
    public void SetNodeForExistingCard(List<Node> AllNodes) {
        if(this.botR==null) {
            for (Node node : AllNodes) {
                if(node.x==this.x+1 && node.y==this.y-1) {
                    this.botR = node;
                    this.getCard().setCoveredCornerByCard(this.getCard().getCoveredCornerByCard() + 1);
                }
            }
        }
        if(this.botL==null) {
            for (Node node : AllNodes) {
                if(node.x==this.x-1 && node.y==this.y-1) {
                    this.botL = node;
                    this.getCard().setCoveredCornerByCard(this.getCard().getCoveredCornerByCard() + 1);
                }
            }
        }
        if(this.topR==null) {
            for (Node node : AllNodes) {
                if(node.x==this.x+1 && node.y==this.y+1) {
                    this.topR = node;
                    this.getCard().setCoveredCornerByCard(this.getCard().getCoveredCornerByCard() + 1);
                }
            }
        }
        if(this.topL==null) {
            for (Node node : AllNodes) {
                if(node.x==this.x-1 && node.y==this.y+1) {
                    this.topL = node;
                    this.getCard().setCoveredCornerByCard(this.getCard().getCoveredCornerByCard() + 1);
                }
            }
        }

    }

    //metodo che verifica che il nuovo nodo non vada a sovrapporsi a nessun nodo libero gia esistente
    public void SetNodeForNewCard(List<Node> AvailableNodes){
        // metodo che deve verificare che il nuovo nodo non vada a sovrapporsi con un nodo gia esistente
        if(this.botR==null) {
            for (Node node : AvailableNodes) {
                if(node.x==this.x+1 && node.y==this.y-1) {
                    this.botR = node;
                }
            }
        }
        if(this.botL==null) {
            for (Node node : AvailableNodes) {
                if(node.x==this.x-1 && node.y==this.y-1) {
                    this.botL = node;
                }
            }
        }
        if(this.topR==null) {
            for (Node node : AvailableNodes) {
                if(node.x==this.x+1 && node.y==this.y+1) {
                    this.topR = node;
                }
            }
        }
        if(this.topL==null) {
            for (Node node : AvailableNodes) {
                if(node.x==this.x-1 && node.y==this.y+1) {
                    this.topL = node;
                }
            }
        }
    }

    //ritorna 1 se la giocata non è andata a buon fine
    public void SetCardNode(Card c, List<PlaceHolder> placeHolderList, List<Node> AvailableNodes, List<Node> AllNodes){
        //Assegno la carta al nuovo nodo
        this.card=c;
        this.card.setCoveredCornerByCard(1);
        this.SetPlaceHolderByCard(AvailableNodes,placeHolderList);
        this.SetNodePlaceHolder(placeHolderList);
        this.SetNodeForExistingCard(AllNodes);
        this.SetNodeForNewCard(AvailableNodes);
        this.SetNullNode(AvailableNodes);
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

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
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


