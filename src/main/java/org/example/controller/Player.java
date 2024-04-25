package org.example.controller;

import org.example.enumeration.Type;
import org.example.exception.PlaceholderNotValid;
import org.example.server.Server;
import org.example.exception.InvalidCardException;
import org.example.model.Model;
import org.example.model.playarea.ScoreBoard;
import org.example.model.deck.*;
import org.example.model.playarea.PlaceHolder;
import org.example.model.playarea.ScoreBoard;

import java.awt.peer.PanelPeer;
import java.util.List;

/** All the attributes and methods for the management of the Player.
 *  The player is in the controller and talks to the PlayArea in the model.
 *  It doesn't have any data saved (Hand, Decks and his PlayArea)
 */

public class Player {
    String username;
    public Player(String username) {
        this.username = username;
    }

    // For Test
    public Player(){

    }

    /** Method for the TUI to see the Player's hand
     *
     */
    public void SeeHand(Model model){
        List<Card> hand= model.getPlayerArea(this).getHand();
        //System.out.println("this is your hand: \n");
        //for (Card c: hand){
        //    System.out.println(c);
        //}
    }

    //metodo che verifica la validità di una carta scelta
    public Card CheckChosenCard(Model model, Card card) throws InvalidCardException {
        boolean isValidCard = model.getPlayerArea(this).CheckPlayForGold(card);
        if (!isValidCard) {
            throw new InvalidCardException("La carta selezionata non è valida.");
        } else {
            return card;
        }
    }


    public void Play (Model model, int choice, int x, int y) throws PlaceholderNotValid {
        Card card = model.getPlayerArea(this).getHand().get(choice);
        PlaceHolder placeHolder=null; //todo il nodo su cui giocare viene scelto dal client
        try {
            for(PlaceHolder p : model.getPlayerArea(this).getAvailableNodes()){
                if(p.x==x && p.y==y){
                    placeHolder=p;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(placeHolder==null) throw new PlaceholderNotValid("placeholder not valid");
        //Card chosencard= CheckChosenCard(model, card); //todo questo check va fatto prima, nel server
        model.getPlayerArea(this).PlayACard(card, placeHolder);
        model.getPlayerArea(this).getHand().remove(card);
    }

    public int ChooseStarterSide(){
        /*System.out.println("Pick your starter card side 1 - front , 2 - back");
        Scanner scanner= new Scanner(System.in);
        return scanner.nextInt();*/
        return 1;
    }

    public void Draw (Model model, int choice){
        //todo gestire l'eccezione di un inserimento non valido
        Card card;
        model.getDrawingCardArea().DisplayVisibleCard();
        switch (choice) {
            case 0:
                card = model.getDrawingCardArea().drawCardFromDeck(Type.RESOURCES);
                model.getPlayerArea(this).getHand().add(card);
                break;
            case 1:
                card = model.getDrawingCardArea().drawCardFromDeck(Type.GOLD);
                model.getPlayerArea(this).getHand().add(card);
                break;
            case 2:
                card = model.getDrawingCardArea().drawCardFromVisible(Type.RESOURCES, 0);
                model.getPlayerArea(this).getHand().add(card);
                break;
            case 3:
                card = model.getDrawingCardArea().drawCardFromVisible(Type.RESOURCES, 1);
                model.getPlayerArea(this).getHand().add(card);
                break;
            case 4:
                card = model.getDrawingCardArea().drawCardFromVisible(Type.GOLD, 0);
                model.getPlayerArea(this).getHand().add(card);
                break;
            case 5:
                card = model.getDrawingCardArea().drawCardFromVisible(Type.GOLD, 1);
                model.getPlayerArea(this).getHand().add(card);
                break;
        }
    }

    @Override
    public String toString() {
        return this.username;
    }

    public void UpdateScoreboardPoints(Model model){
        ScoreBoard scoreBoard= model.getScoreBoard();
        if(scoreBoard.getPlayerPoint(this) < model.getPlayerArea(this).getCounter().getPointCounter()){
            scoreBoard.UpdatePlayerPoint(this, model.getPlayerArea(this).getCounter().getPointCounter());
        }
    }
}


