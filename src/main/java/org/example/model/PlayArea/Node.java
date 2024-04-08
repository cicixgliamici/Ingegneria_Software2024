package org.example.model.PlayArea;

import org.example.model.deck.Card;
import org.example.model.deck.Corner;
import org.example.model.deck.enumeration.Position;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Card carta;
    private Node topL;
    private Node topR;
    private Node botL;
    private Node botR;
    private int x;
    private int y;

    public static List<Node> AvailableNode = new ArrayList<>();

    public Node(Card carta, Node topL, Node topR, Node botL, Node botR, int x, int y) {
        this.carta = carta;
        this.topL = topL;
        this.topR = topR;
        this.botL = botL;
        this.botR = botR;
        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        return super.toString();
    }

    /** Search for every present card the availability of each node
     *
     */
    public void searchAvailableNode(){
        if(this.botL == null){
            AvailableNode.add(this.botL);
        }
        else {
            this.botL.searchAvailableNode();
        }
        if (this.botR == null){
            AvailableNode.add(this.botR);
        }
        else {
            this.botR.searchAvailableNode();
        }
        if (this.topL == null){
            AvailableNode.add(this.topL);
        }
        else {
            this.topL.searchAvailableNode();
        }
        if (this.topR == null){
            AvailableNode.add(this.topR);
        }
        else {
            this.botR.searchAvailableNode();
        }
    }

    public void printList (){
        for (Node node : AvailableNode) {
            if(node.topL==null){
                System.out.printf("topL");
            }
            if(node.topR==null){
                System.out.printf("topR");
            }
            if(node.botL==null){
                System.out.printf("botL");
            }
            if(node.botR==null){
                System.out.printf("botR");
            }


        }
    }

   /* public void addNode(Node node){
        Position = node;


    }

    */

    public Node searchListAvailableNode (Card searchCard, Corner searchCorner){
        for(int i = 0; i < 100; i++){
            if(AvailableNode.get(i).carta.equals(searchCard)){
                return AvailableNode.get(i);
            }
        }
        return null;
    }

}
