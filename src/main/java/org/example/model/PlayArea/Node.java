package org.example.model.PlayArea;

import org.example.model.deck.Card;

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

    private static List<Node> AvailableNode = new ArrayList<>();

    public Node(Card carta, Node topL, Node topR, Node botL, Node botR, int x, int y) {
        this.carta = carta;
        this.topL = topL;
        this.topR = topR;
        this.botL = botL;
        this.botR = botR;
        this.x = x;
        this.y = y;
    }

    public void searchAvailableNode(Node node){
        if(node.botL == null){
            AvailableNode.add(node.botL);
        }
        else {
            searchAvailableNode(node.botL);
        }
        if (node.botR == null){
            AvailableNode.add(node.botR);
        }
        else {
            searchAvailableNode(node.botR);
        }
        if (node.topL == null){
            AvailableNode.add(node.topL);
        }
        else {
            searchAvailableNode(node.topL);
        }
        if (node.topR == null){
            AvailableNode.add(node.topR);
        }
        else {
            searchAvailableNode(node.topR);
        }
    }

}
