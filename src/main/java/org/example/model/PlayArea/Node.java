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
    private int x;
    private int y;

    //todo cambiare la lista, non puo essere static senno anche gli altri giocatori che istanziano un nodo vedono questa lista, metterla come attributo di player
    //public static List<Node> AvailableNode = new ArrayList<>();

    public Node(Card card, int x, int y) {
        this.card = card;
        this.x = x;
        this.y = y;
        this.SetNullNode();
    }
    public Node (Card card, PlaceHolder topL, PlaceHolder topR, PlaceHolder botL, PlaceHolder botR, int x, int y){
        this.card=card;
        this.topL= topL;
        this.topR= topR;
        this.botL= botL;
        this.botR= botR;
        this.x= x;
        this.y= y;
    }
    public Node(Card card) {
        this.card = card;
    }

    /**  The node that is created is positioned 
    *    mirror-image of the original one
    */
    
    public Node NullNodeTopR(){
        int x = this.x+1;
        int y= this.y+1;
        PlaceHolder placeHolder = new PlaceHolder();
        return new Node(null, null, null, placeHolder, null,  x, y);
    }
    public Node NullNodeTopL(){
        int x = this.x-1;
        int y= this.y+1;
        PlaceHolder placeHolder = new PlaceHolder();
        return new Node(null, null, null, null, placeHolder,  x, y);
    }
    public Node NullNodeBotR(){
        int x = this.x+1;
        int y= this.y-1;
        PlaceHolder placeHolder = new PlaceHolder();
        return new Node(null, placeHolder, null, null, null,  x, y);
    }
    public Node NullNodeBotL(){
        int x = this.x-1;
        int y= this.y-1;
        PlaceHolder placeHolder = new PlaceHolder();
        return new Node(null, null, placeHolder , null, null,  x, y);
    }
    public void SetNullNode(){
        //if(this.getCard().)
        //todo serve un metodo di carta che ti ritorni l'angolo desiderato tenendo conto del lato scelto della carta
        if (this.botR==null) this.botR = NullNodeBotR();
        if (this.botL==null) this.botL = NullNodeBotL();
        if (this.topR ==null) this.topR = NullNodeTopR();
        if (this.topL ==null) this.topL = NullNodeTopL();
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

    public Node getTopL() {
        return topL;
    }

    public Node getTopR() {
        return topR;
    }

    public Node getBotL() {
        return botL;
    }

    public Node getBotR() {
        return botR;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
