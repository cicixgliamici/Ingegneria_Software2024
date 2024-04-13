package org.example.model.PlayArea;

import org.example.model.deck.Card;

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

    //todo cambiare la lista, non puo essere static senno anche gli altri giocatori che istanziano un nodo vedono questa lista, metterla come attributo di player
    //public static List<Node> AvailableNode = new ArrayList<>();

    public Node(Card card, int x, int y) {
        super (x,y);
        this.card = card;
        this.SetNullNode();
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
        //todo aggiungere il nuovo nodo alla lista di available node
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
    public void SetNullNode() {
        //todo verificare le condizioni logiche del metodo perche non sono sicuro che le condizioni degli if siano giuste
        if (this.getCard().BOTRCornerIsHidden()) {
            this.botR = new PlaceHolder(this.x + 1, this.y - 1);
        } else if (this.botR == null) this.botR = NullNodeBotR();

        if (this.getCard().BOTLCornerIsHidden()) {
            this.botL = new PlaceHolder(this.x - 1, this.y - 1);
        } else if (this.botL == null) this.botL = NullNodeBotL();

        if (this.getCard().TOPLCornerIsHidden()) {
            this.topL = new PlaceHolder(this.x - 1, this.y + 1);
        } else  if (this.topL == null) this.topL = NullNodeTopL();

        if (this.getCard().TOPRCornerIsHidden()) {
            this.topR = new PlaceHolder(this.x + 1, this.y + 1);
        } else if (this.topR == null) this.topR = NullNodeTopR();
    }



    public void SetCardNode(Card c){
        this.card=c;
        this.SetNullNode();
    }
    /** Search for every present card the availability of each node
     *
     */


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
}

