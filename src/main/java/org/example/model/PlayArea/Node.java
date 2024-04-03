package org.example.model.PlayArea;

import org.example.model.deck.Card;

public class Node {
    private Card carta;
    private Node topL;
    private Node topR;
    private Node botL;
    private Node botR;
    int x;
    int y;

    public Node(Card carta, Node topL, Node topR, Node botL, Node botR, int x, int y) {
        this.carta = carta;
        this.topL = topL;
        this.topR = topR;
        this.botL = botL;
        this.botR = botR;
        this.x = x;
        this.y = y;
    }

}
