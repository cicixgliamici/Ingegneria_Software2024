package org.example.model.PlayArea;
import org.example.model.deck.*;
import org.example.model.deck.enumeration.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** This class reference the player in the Model, in order to keep his resources and the PlayerCardArea, the real player
 * is in the Controller
 */

public class Player {

    private List<Card> hand;
    private CounterResources counter;
    private Card InitialCard;
    private PlayerCardArea gameArea;

    public Player (Card InitialCard){
        this.hand  = new ArrayList<>();
        this.InitialCard = InitialCard;
        this.counter = new CounterResources();
    }

    public void InitializeGameArea(){
        this.gameArea = new PlayerCardArea(InitialCard);
        System.out.println("scegli un lato della carta starter da giocare: 1- front, 2-retro");
        Scanner scanner = new Scanner(System.in);
        int choice= scanner.nextInt();
        InitialCard.setSide(choice);
        gameArea.UpdateCounter(InitialCard);
    }

    //ritorna
    public Card ChoseACard (){
        System.out.println("la tua mano attuale è: ");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ": " + hand.get(i));
        }
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.print("Scegli il numero della carta da giocare (1-" + hand.size() + "): ");
        choice = scanner.nextInt();
        System.out.print("Scegli il  lato della carta: 1-fronte, 2-retro");
        int side= scanner.nextInt();
        hand.get(choice-1).setSide(side);
        return hand.remove(choice - 1);
    }



    public void ModifyGameArea (){
        //metodo incaricato di gestire una giocata di un player

        //fa scegliere al player su che nodo giocare la carta e la carta da giocare dalla sua mano
        Card CardToPlay = this.ChoseACard();
        Node ChoosenNode = gameArea.getStarter().printAndChooseNode();
        gameArea.removeResources(ChoosenNode);

        //chiama il metodo di node che imposta la carta scelta al nodo scelto e aggiunge i nodi di default

        ChoosenNode.SetCardNode(CardToPlay);
        gameArea.UpdateCounter(CardToPlay);
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




    //getter e setter

    public void setInitialCard(Card initialCard) {
        InitialCard = initialCard;
    }

    public List<Card> getHand() {
        return hand;
    }

    public Card getInitialCard() {
        return InitialCard;
    }

    public PlayerCardArea getGameArea() {
        return gameArea;
    }



}

//controller del player
