package org.example.model.PlayArea;

import org.example.model.deck.Card;
import org.example.model.deck.Corner;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Node {
    private Card card;
    private Node topL;
    private Node topR;
    private Node botL;
    private Node botR;
    private int x;
    private int y;

    //todo cambiare la lista, non puo essere static senno anche gli altri giocatori che istanziano un nodo vedono questa lista, metterla come attributo di player
    public static List<Node> AvailableNode = new ArrayList<>();

    public Node(Card card, int x, int y) {
        this.card = card;
        this.x = x;
        this.y = y;
        this.SetNullNode();
    }
    public Node (Card card, Node topL, Node topR, Node botL, Node botR, int x, int y){
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

    public Node NullNodeTopR(){
        int x = this.x+1;
        int y= this.y+1;
        Node emptynode= new EmptyNode(null);
        return new Node(null, null, null, emptynode, null,  x, y);
    }
    public Node NullNodeTopL(){
        int x = this.x-1;
        int y= this.y+1;
        Node emptynode= new EmptyNode(null);
        return new Node(null, null, null, null, emptynode,  x, y);
    }
    public Node NullNodeBotR(){
        int x = this.x+1;
        int y= this.y-1;
        Node emptynode= new EmptyNode(null);
        return new Node(null, emptynode, null, null, null,  x, y);
    }
    public Node NullNodeBotL(){
        int x = this.x-1;
        int y= this.y-1;
        Node emptynode= new EmptyNode(null);
        return new Node(null, null, emptynode, null, null,  x, y);
    }
    public void SetNullNode(){
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
    public void searchAvailableNode(){
        if(this.botL.card == null && !(this.botL instanceof EmptyNode)){
            AvailableNode.add(this.botL);
        }
        else {
            if(!(this.botL instanceof EmptyNode)) this.botL.searchAvailableNode();
        }


        if (this.botR.card == null && !(this.botR instanceof EmptyNode)){
            AvailableNode.add(this.botR);
        }
        else {
            if(!(this.botR instanceof EmptyNode))this.botR.searchAvailableNode();
        }


        if (this.topL.card == null && !(this.topL instanceof EmptyNode)){
            AvailableNode.add(this.topL);
        }
        else {
            if(!(this.topL instanceof EmptyNode)) this.topL.searchAvailableNode();
        }


        if (this.topR.card == null && !(this.topR instanceof EmptyNode)){
            AvailableNode.add(this.topR);
        }
        else {
            if(!(this.topR instanceof EmptyNode)) this.topR.searchAvailableNode();
        }
    }

    public Node printAndChooseNode() {
        System.out.println("Available Nodes:");
        for (int i = 0; i < AvailableNode.size(); i++) {
            Node node = AvailableNode.get(i);
            System.out.println((i + 1) + ": Nodo alla posizione (" + node.x + ", " + node.y + ")");
        }
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.print("Scegli il numero del nodo (1-" + AvailableNode.size() + "): ");
        choice = scanner.nextInt();
        return AvailableNode.get(choice - 1);
    }

    public static List<Node> getAvailableNode() {
        return AvailableNode;
    }

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