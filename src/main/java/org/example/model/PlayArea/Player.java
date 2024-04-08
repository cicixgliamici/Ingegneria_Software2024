package org.example.model.PlayArea;
import org.example.model.deck.*;
import org.example.model.deck.enumeration.*;

import java.util.List;
import java.util.Scanner;

/** This class reference the player in the Model, in order to keep his resources and the PlayerCardArea, the real player
 * is in the Controller
 */

public class Player {

    private List<Card> hand;

    private Card InitialCard;

    public Player (Card InitialCard){
        this.InitialCard = InitialCard;
    }
    //il player deve scegliere il side della carta iniziale da giocarla prima di creare l'area di gioco
    //creare un metodo che cambi l'attributo Side di SideCard inizialmente a BOTH facendo scegliere se front o back
    //per ora messo di default a fro

    PlayerCardArea gameArea = new PlayerCardArea(InitialCard);

    public void DrawCard(Deck deckFromDraw){

        if (hand.size()==2)
            hand.add(deckFromDraw.drawCard());
    }

    public void addCard(Card c){
        if (hand.size()==2)
            hand.add(c);
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
        return hand.remove(choice - 1);
    }




    public void setInitialCard(Card initialCard) {
        InitialCard = initialCard;
    }

    public void ModifyGameArea (){
        //metodo incaricato di gestire una giocata di un player
        //fa scegliere al player su che nodo giocare la carta e la carta da giocare dalla sua mano
        Card CardToPlay = this.ChoseACard();
        Node ChoosenNode = gameArea.getStarter().printAndChooseNode();
        //chiama il metodo di node che imposta la carta scelta al nodo scelto e aggiunge i nodi di default
        ChoosenNode.SetCardNode(CardToPlay);
        Node nodo_prova= ChoosenNode.printAndChooseNode();
    }
    /*
    public void printListCard (PlayerCardArea gameArea){
        gameArea.
    }


 */
}

//controller del player