package org.example.controller;

import org.example.enumeration.Type;
import org.example.exception.PlaceholderNotValid;
import org.example.exception.InvalidCardException;
import org.example.model.Model;
import org.example.model.playarea.ScoreBoard;
import org.example.model.deck.*;
import org.example.model.playarea.PlaceHolder;

import java.rmi.RemoteException;

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

    /**
     * Checks the validity of a chosen card
     */
    public void checkChosenCard(Model model, Card card, PlaceHolder placeHolder) throws InvalidCardException {
        boolean isInvalidCard = model.getPlayerCardArea(this).checkPlayForGold(card);
        if (isInvalidCard) {
            throw new InvalidCardException("La carta selezionata non è valida.", card.getId(), placeHolder.getX(), placeHolder.getY());
        }
    }

    /**
     * Associates to each player
     * their own player area
     */
    public void setObjStarter(Model model, int choice, int obj) { //Choice indicates the side of the started card and obj the chosen objective card
        if (choice==1){
            model.getPlayerCardArea(this).getCardStarter().setSide(1); //front
        } else if (choice==2) {
            model.getPlayerCardArea(this).getCardStarter().setSide(2); //back
        }
        model.getPlayerCardArea(this).setStarterNode();
        if(obj==1){
            model.getPlayerCardArea(this).setSecretObjective(model.getPlayerCardArea(this).getTempSecretObjective().get(0));
            model.getDrawingCardArea().getObjectDeck().getCards().add(model.getPlayerCardArea(this).getTempSecretObjective().get(1));
            model.getPlayerCardArea(this).getTempSecretObjective().clear();
        }
        else if(obj==2) {
            model.getPlayerCardArea(this).setSecretObjective(model.getPlayerCardArea(this).getTempSecretObjective().get(1));
            model.getDrawingCardArea().getObjectDeck().getCards().add(model.getPlayerCardArea(this).getTempSecretObjective().get(0));
            model.getPlayerCardArea(this).getTempSecretObjective().clear();
        }
    }

    /**
     * Plays a card from the player's hand on the node with the passed coordinates
     * on the chosen side
     */
    public void play(Model model, int choice, int side, int x, int y) throws PlaceholderNotValid, InvalidCardException, RemoteException {
        Card card = model.getPlayerCardArea(this).getHand().get(choice);
        card.setSide(side);
        PlaceHolder placeHolder=null;
        try {
            for(PlaceHolder p : model.getPlayerCardArea(this).getAvailableNodes()){
                if(p.x==x && p.y==y){
                    placeHolder=p;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(placeHolder==null) throw new PlaceholderNotValid("placeholder not valid", x, y);
        checkChosenCard(model, card, placeHolder);
        model.getPlayerCardArea(this).playACard(card, placeHolder);
        model.getPlayerCardArea(this).getHand().remove(card);
        model.notifyModelChange(this.username,  "playedCard:" + card.getId() + "," + x + "," + y,
                                                "hasPlayed:" + username + "," + card.getId());
        model.notifyModelGeneric("points:"+ this.username + "," + model.getPlayerCardArea(this).getCounter().getPointCounter());
    }

    /**
     * Draws a card. Choice indicates the Deck to draw from
     *
     */
    public void draw(Model model, int choice) throws RemoteException {
        Card card=null;
        model.getDrawingCardArea().displayVisibleCard();
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

    public void chatS(Model model, String username, String message) throws RemoteException {
        model.addChat(username+":"+message);
        if (message.startsWith("[")) {
            // Se il messaggio inizia con '[', si tratta di un messaggio diretto a un utente specifico
            int endIndex = message.indexOf("]");
            if (endIndex != -1) {
                // Estrai l'username dall'interno delle parentesi quadre
                String targetUsername = message.substring(1, endIndex);
                // Estrai il messaggio dopo le parentesi quadre
                String actualMessage = message.substring(endIndex + 1);
                // Invia il messaggio all'utente specifico utilizzando notifyModelSpecific()
                if(targetUsername.equals(username)){
                    model.notifyModelSpecific(username, "chatC:" + "Server" + "," + "Why would you send messages to yourself?");
                    model.notifyModelSpecific(username, "chatC:" + username + "," + message);
                }
                else{
                    for(Player player : model.getPlayersList()){
                        if(targetUsername.equals(player.getUsername())){
                            model.notifyModelSpecific(targetUsername, "chatC:" + username + " [PVT]" + "," + actualMessage);
                            model.notifyModelSpecific(username, "chatC:" + username + "," + message);
                            return;
                        }
                    }
                    model.notifyModelSpecific(username, "chatC:" + "Server" + "," + " This player does not exist:" + "'" + targetUsername +  "'" );
                }
            }
        }
        else {
            model.notifyModelGeneric("chatC:" + username + "," + message);
        }
    }
}