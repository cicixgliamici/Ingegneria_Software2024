package org.example.model.PlayArea;

import org.example.model.deck.Card;
import org.example.model.deck.Corner;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Card card;
    private Node topL;
    private Node topR;
    private Node botL;
    private Node botR;
    private int x;
    private int y;

    public static List<Node> AvailableNode = new ArrayList<>();

    public Node(Card carta, int x, int y) {
        this.card = carta;
        this.x = x;
        this.y = y;
        this.SetNode();
    }


    public Node(Card carta) {
        this.card = carta;
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
    public void SetNode(){
        this.botR = NullNodeBotR();
        this.botL = NullNodeBotL();
        this.topR = NullNodeTopR();
        this.topL = NullNodeTopL();
    }

    public void ModifyNode (Card card){
        this.card = card;
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




    /*
    public Node searchListAvailableNode (Card searchCard, Corner searchCorner){
        for(int i = 0; i < 100; i++){
            if(AvailableNode.get(i).card.equals(searchCard)){
                return AvailableNode.get(i);
            }
        }
        return null;
    }



     */
    public void printAvailableNode() {
        System.out.println("Available Nodes:");
        for (Node node : AvailableNode) {
            System.out.println("Node at position (" + node.x + ", " + node.y + ")");
        }
    }


}
