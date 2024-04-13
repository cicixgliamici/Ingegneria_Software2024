package org.example.controller;
import org.example.model.Model;
import org.example.model.PlayArea.PlayerCardArea;
import org.example.model.deck.*;

import java.util.List;
import java.util.Scanner;

/** All the attributes and methods for the management of the Player.
 *  The player is in the controller and talks to the PlayArea in the model.
 *  It doesn't have any data saved (Hand, Decks and his PlayArea)
 */

public class Player {
    String username;
    public Player(String username) {
        this.username= username;
    }

    /** Method for the TUI to see the Player's hand
     *
     */
    public void SeeHand(Model model){
        //todo metodo che stampa la mano del player su cui viene chiamato
        List<Card> hand= model.getPlayerArea(this).getHand();
        for (Card c: hand){
            System.out.println(c);
        }
    }

    public Card ChosenCard (Model model) {
        SeeHand(model);
        System.out.println("Chose the number of the card you want to play");
        Scanner scanner= new Scanner(System.in);
        int choice = scanner.nextInt();
        return model.getPlayerArea(this).getHand().get(choice);
    }

    public void Play (Model model){
        //todo metodo chiamato dal gameflow per indicare al player di fare una giocata
        //mostra nodi
        model.getPlayerArea(this).PlayACard(ChosenCard(model));
    }

    public int ChooseStarterSide(PlayerCardArea playerCardArea){
        //todo implementare un metodo per mostrare una carta
        System.out.println("Pick your starter card side 1 - front , 2 - back");
        Scanner scanner= new Scanner(System.in);
        return scanner.nextInt();
    }

    public void Draw (Model model){
        //todo implementare la pescata di una carta, facendo scegliere al player da dove vuole pescare e nel caso voglia pescare dal dalle carte scoperte, quale voglia pescare
    }

    public boolean CheckPoints(Model model){
        //todo controlla per il player i punti e ritorna true se i punti sono più di venti senno ritorna false
        return false;
    }












    /*

    private String username; // TODO mettere nel costruttore al posto della carta iniziale il nome del giocatore


    /** The initial card of the player is drawn from the starter deck
     *

      public Player (String username){
        this.hand  = new ArrayList<>();
        this.counter = new CounterResources();
        this.username= username;

        //this.gameArea = new PlayerCardArea()
    }

    /** Method for the TUI so that the player can choose the side of the starter card and
     * the model can initialize his PlayArea

    public void InitializeGameArea(){
        this.gameArea = new PlayerCardArea(InitialCard);
        System.out.println("Choose a side for your Starter card: 1- front, 2-back");
        Scanner scanner = new Scanner(System.in);
        int choice= scanner.nextInt();
        InitialCard.setSide(choice);
        gameArea.UpdateCounter(InitialCard);
    }

    /** Method for the TUI where the player can see his
     * hand each turn and choose what card he wants to
     * play.

    public Card ChoseACard (){
        System.out.println("Your hand is: ");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ": " + hand.get(i));
        }
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.print("Choose the number of the card you want to play (1-" + hand.size() + "): ");
        choice = scanner.nextInt();
        System.out.println("Choose a side for your Starter card: 1- front, 2-back");
        int side= scanner.nextInt();
        hand.get(choice-1).setSide(side);
        return hand.remove(choice - 1);
    }



    public void ModifyGameArea (){
        //metodo incaricato di gestire una giocata di un player

        //fa scegliere al player su che nodo giocare la carta e la carta da giocare dalla sua mano
        Card CardToPlay = this.ChoseACard();
        Node ChoosenNode = gameArea.printAndChooseNode();
        //todo gameArea.removeResources(ChoosenNode);

        //chiama il metodo di node che imposta la carta scelta al nodo scelto e aggiunge i nodi di default

        ChoosenNode.SetCardNode(CardToPlay);
        gameArea.UpdateCounter(CardToPlay);
        gameArea.UpdatePoints(CardToPlay);
        System.out.println(
                "fatto cacca"
        );
        //TODO aggiornare le risorse

    }


    //altri metodi

    public void drawCard(DrawingCardArea drawingCardArea, Type type) {
        Card card = drawingCardArea.drawCardFromDeck(type);
        hand.add(card);
    }

    public void drawCardFromVisible(DrawingCardArea drawingCardArea, Type type, int i) {
        Card card = drawingCardArea.drawCardFromVisible(type, i);
        if (card != null) {
            hand.add(card);
        } else {
            System.out.println("Card doesn't exist");
        }
    }

    public void addCard(Card c){
        hand.add(c);
    }


    /** Setter Area

    public void setInitialCard(Card initialCard) {
        InitialCard = initialCard;
    }

    /** Getter Area

    public List<Card> getHand() {
        return hand;
    }

    public Card getInitialCard() {
        return InitialCard;
    }

    public PlayerCardArea getGameArea() {
        return gameArea;
    }


    */


}

//controller del player
