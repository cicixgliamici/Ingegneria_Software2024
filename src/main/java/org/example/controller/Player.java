package org.example.controller;

import org.example.enumeration.Type;
import org.example.exception.PlaceholderNotValid;
import org.example.exception.InvalidCardException;
import org.example.model.Model;
import org.example.model.playarea.ScoreBoard;
import org.example.model.deck.*;
import org.example.model.playarea.PlaceHolder;

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

    //metodo che verifica la validità di una carta scelta
    public Card CheckChosenCard(Model model, Card card) throws InvalidCardException {
        boolean isValidCard = model.getPlayerCardArea(this).CheckPlayForGold(card);
        if (isValidCard) {
            throw new InvalidCardException("La carta selezionata non è valida.");
        } else {
            return card;
        }
    }

    public void Play (Model model, int choice, int side, int x, int y) throws PlaceholderNotValid, InvalidCardException {
        Card card = model.getPlayerCardArea(this).getHand().get(choice);
        card.setSide(side);
        PlaceHolder placeHolder=null; //todo il nodo su cui giocare viene scelto dal client
        try {
            for(PlaceHolder p : model.getPlayerCardArea(this).getAvailableNodes()){
                if(p.x==x && p.y==y){
                    placeHolder=p;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(placeHolder==null) throw new PlaceholderNotValid("placeholder not valid");
        Card chosencard= CheckChosenCard(model, card); //todo se lancia eccezione il server deve dire al client che la carta oro scelta non è posizionabile
        try {
            // Tentativo di giocare una carta
            model.getPlayerCardArea(this).PlayACard(card, placeHolder);
            model.getPlayerCardArea(this).getHand().remove(card);
            model.notifyModelChange(this.username, "You played: " + card, username + " has played :" + card);
        } catch (Exception e) {
            model.notifyModelSpecific(this.username, "You cannot play: " + card + "in:" + x + "," + y);
            throw e; // Rilancia l'eccezione per ulteriore gestione degli errori
        }

    }

    public int ChooseStarterSide(){
        /*System.out.println("Pick your starter card side 1 - front , 2 - back");
        Scanner scanner= new Scanner(System.in);
        return scanner.nextInt();*/
        return 1;
    }

    public void Draw (Model model, int choice){
        //todo gestire l'eccezione di un inserimento non valido
        Card card=null;
        model.getDrawingCardArea().DisplayVisibleCard();
        switch (choice) {
            case 0:
                card = model.getDrawingCardArea().drawCardFromDeck(Type.RESOURCES);
                model.getPlayerCardArea(this).getHand().add(card);
                break;
            case 1:
                card = model.getDrawingCardArea().drawCardFromDeck(Type.GOLD);
                model.getPlayerCardArea(this).getHand().add(card);
                break;
            case 2:
                card = model.getDrawingCardArea().drawCardFromVisible(Type.RESOURCES, 0);
                model.getPlayerCardArea(this).getHand().add(card);
                break;
            case 3:
                card = model.getDrawingCardArea().drawCardFromVisible(Type.RESOURCES, 1);
                model.getPlayerCardArea(this).getHand().add(card);
                break;
            case 4:
                card = model.getDrawingCardArea().drawCardFromVisible(Type.GOLD, 0);
                model.getPlayerCardArea(this).getHand().add(card);
                break;
            case 5:
                card = model.getDrawingCardArea().drawCardFromVisible(Type.GOLD, 1);
                model.getPlayerCardArea(this).getHand().add(card);
                break;
        }
        assert card != null;
        model.notifyModelChange(this.username, "You drew: " + card, username + " has drawn a card.");
    }

    @Override
    public String toString() {
        return this.username;
    }

    public String getUsername() {
        return username;
    }

    public void updateScoreboardPoints(Model model){
        ScoreBoard scoreBoard= model.getScoreBoard();
        if(scoreBoard.getPlayerPoint(this) < model.getPlayerCardArea(this).getCounter().getPointCounter()){
            scoreBoard.updatePlayerPoint(this, model.getPlayerCardArea(this).getCounter().getPointCounter());
        }
    }
}