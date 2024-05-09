package org.example.controller;

import org.example.enumeration.Type;
import org.example.exception.PlaceholderNotValid;
import org.example.exception.InvalidCardException;
import org.example.model.Model;
import org.example.model.playarea.PlayerCardArea;
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

    /** Associates to each player
     * their own player area
     */
    public void setObjStarter(Model model, int choice, int obj) { //choice è il lato di starter e obj è l'obbiettivo segreto scelto
        if (choice==1){
            model.getPlayerCardArea(this).getCardStarter().setSide(1);
        } else {
            model.getPlayerCardArea(this).getCardStarter().setSide(2);
        }
        model.getPlayerCardArea(this).setStarterNode();
        if(obj==1){
            model.getPlayerCardArea(this).setSecretObjective(model.getPlayerCardArea(this).getTempSecretObjective().get(0));
            model.getDrawingCardArea().getObjectDeck().getCards().add(model.getPlayerCardArea(this).getTempSecretObjective().get(1));
            model.getPlayerCardArea(this).getTempSecretObjective().clear();
        }
        else {
            model.getPlayerCardArea(this).setSecretObjective(model.getPlayerCardArea(this).getTempSecretObjective().get(1));
            model.getDrawingCardArea().getObjectDeck().getCards().add(model.getPlayerCardArea(this).getTempSecretObjective().get(0));
            model.getPlayerCardArea(this).getTempSecretObjective().clear();
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
            model.notifyModelChange(this.username,  "playedCard:" + card.getId() + "," + x + "," + y,
                                                    "hasPlayed:" + username + "," + card.getId());
        } catch (Exception e) {
            model.notifyModelSpecific(this.username, "unplayable:" + username + "," + card.getId() + "," + x + "," + y);
            throw e; // Rilancia l'eccezione per ulteriore gestione degli errori
        }

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
        model.notifyModelChange(this.username,  "drawnCard:" + card.getId(),
                                                "hasDrawn:" + username + ","+ card.getId());
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