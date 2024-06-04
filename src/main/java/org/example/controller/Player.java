package org.example.controller;

import org.example.enumeration.Type;
import org.example.exception.PlaceholderNotValid;
import org.example.exception.InvalidCardException;
import org.example.model.Model;
import org.example.model.playarea.ScoreBoard;
import org.example.model.deck.*;
import org.example.model.playarea.PlaceHolder;

import java.rmi.RemoteException;

/**
 * All the attributes and methods for the management of the Player.
 * The player is in the controller and interacts with the PlayArea in the model.
 * It doesn't have any data saved (Hand, Decks, and his PlayArea).
 */
public class Player {
    String username;

    /**
     * Constructor for the Player class with a username.
     * 
     * @param username The username of the player.
     */
    public Player(String username) {
        this.username = username;
    }

    // For Test
    public Player() {
    }

    /**
     * Checks the validity of a chosen card.
     * 
     * @param model The model instance to interact with.
     * @param card The card to be checked.
     * @param placeHolder The placeholder where the card is to be played.
     * @throws InvalidCardException If the card is invalid.
     */
    public void checkChosenCard(Model model, Card card, PlaceHolder placeHolder) throws InvalidCardException {
        System.out.println("Checking chosen card in checkChosenCard: " + card.getId());
        if (card.getType() == Type.GOLD) {
            boolean isInvalidCard = model.getPlayerCardArea(this).checkPlayForGold(card);
            if (isInvalidCard) {
                System.out.println("check failed");
                throw new InvalidCardException("The selected card is not valid.", card.getId(), placeHolder.getX(), placeHolder.getY());
            }
            System.out.println("check successful");
        }
    }

    /**
     * Associates each player with their own player area.
     * 
     * @param model The model instance to interact with.
     * @param choice Indicates the side of the starter card.
     * @param obj The chosen objective card.
     */
    public void setObjStarter(Model model, int choice, int obj) {
        if (choice == 1) {
            model.getPlayerCardArea(this).getCardStarter().setSide(1); // front
        } else if (choice == 2) {
            model.getPlayerCardArea(this).getCardStarter().setSide(2); // back
        }
        model.getPlayerCardArea(this).setStarterNode();
        if (obj == 1) {
            model.getPlayerCardArea(this).setSecretObjective(model.getPlayerCardArea(this).getTempSecretObjective().get(0));
            model.getDrawingCardArea().getObjectDeck().getCards().add(model.getPlayerCardArea(this).getTempSecretObjective().get(1));
            model.getPlayerCardArea(this).getTempSecretObjective().clear();
        } else if (obj == 2) {
            model.getPlayerCardArea(this).setSecretObjective(model.getPlayerCardArea(this).getTempSecretObjective().get(1));
            model.getDrawingCardArea().getObjectDeck().getCards().add(model.getPlayerCardArea(this).getTempSecretObjective().get(0));
            model.getPlayerCardArea(this).getTempSecretObjective().clear();
        }
    }

    /**
     * Plays a card from the player's hand on the node with the passed coordinates
     * on the chosen side.
     * 
     * @param model The model instance to interact with.
     * @param id The id of the card to be played.
     * @param side The side of the card to be played.
     * @param x The x-coordinate of the placeholder.
     * @param y The y-coordinate of the placeholder.
     * @throws RemoteException If a remote exception occurs.
     * @throws PlaceholderNotValid If the placeholder is not valid.
     * @throws InvalidCardException If the card is invalid.
     */
    public void play(Model model, int id, int side, int x, int y) throws RemoteException, PlaceholderNotValid, InvalidCardException {
        int choice = findIdinHand(model, id);
        System.out.println("Playing in Player: " + choice + " card(choice)");
        Card card = model.getPlayerCardArea(this).getHand().get(choice);
        System.out.println("playing in Player; " + card.getId() + " card.getId()");
        card.setSide(side);
        PlaceHolder placeHolder = null;
        System.out.println("List of nodes in Player: " + model.getPlayerCardArea(this).getAvailableNodes());
        for (PlaceHolder p : model.getPlayerCardArea(this).getAvailableNodes()) {
            if (p.x == x && p.y == y) {
                placeHolder = p;
                break;
            }
        }
        if (placeHolder == null) {
            System.out.println("placeholder failed");
            throw new PlaceholderNotValid("Placeholder not valid.", id, x, y);
        }
        checkChosenCard(model, card, placeHolder);
        model.getPlayerCardArea(this).playACard(card, placeHolder);
        model.getPlayerCardArea(this).getHand().remove(card);
        if (placeHolder != null) {
            model.notifyModelChange(this.username, "playedCard:" + card.getId() + "," + side + "," + x + "," + y,
                    "hasPlayed:" + username + "," + card.getId());
            model.notifyModelGeneric("points:" + this.username + "," + model.getPlayerCardArea(this).getCounter().getPointCounter());
        }
        System.out.println("Counter in Player: " + model.getPlayerCardArea(this).getCounter().getAnimalCounter() + "," +
                model.getPlayerCardArea(this).getCounter().getPlantCounter() + "," +
                model.getPlayerCardArea(this).getCounter().getInsectCounter() + "," +
                model.getPlayerCardArea(this).getCounter().getFungiCounter());
    }

    /**
     * Finds the index of a card in the player's hand by its ID.
     * 
     * @param model The model instance to interact with.
     * @param id The id of the card to find.
     * @return The index of the card in the player's hand.
     * @throws RemoteException If a remote exception occurs.
     */
    public int findIdinHand(Model model, int id) throws RemoteException {
        for (Card card : model.getPlayerCardArea(this).getHand()) {
            if (card.getId() == id) {
                return model.getPlayerCardArea(this).getHand().indexOf(card);
            }
        }
        return 0;
    }

    /**
     * Draws a card. The choice indicates the deck to draw from.
     * 
     * @param model The model instance to interact with.
     * @param choice The deck choice to draw from.
     * @throws RemoteException If a remote exception occurs.
     */
    public void draw(Model model, int choice) throws RemoteException {
        Card card = null;
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
        model.showArea();
        assert card != null;
        model.notifyModelChange(this.username, "drawnCard:" + card.getId(),
                "hasDrawn:" + username + "," + card.getId());
    }

    /**
     * Returns the player's username.
     * 
     * @return The player's username.
     */
    @Override
    public String toString() {
        return this.username;
    }

    /**
     * Gets the username of the player.
     * 
     * @return The username of the player.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Updates the player's points on the scoreboard.
     * 
     * @param model The model instance to interact with.
     */
    public void updateScoreboardPoints(Model model) {
        ScoreBoard scoreBoard = model.getScoreBoard();
        if (scoreBoard.getPlayerPoint(this) < model.getPlayerCardArea(this).getCounter().getPointCounter()) {
            scoreBoard.updatePlayerPoint(this, model.getPlayerCardArea(this).getCounter().getPointCounter());
        }
    }

    /**
     * Sends a chat message.
     * 
     * @param model The model instance to interact with.
     * @param username The username of the player sending the message.
     * @param message The message to be sent.
     * @throws RemoteException If a remote exception occurs.
     */
    public void chatS(Model model, String username, String message) throws RemoteException {
        model.addChat(username + ":" + message);
        if (message.startsWith("[")) {
            // If the message starts with '[', it is a direct message to a specific user
            int endIndex = message.indexOf("]");
            if (endIndex != -1) {
                // Extract the username inside the brackets
                String targetUsername = message.substring(1, endIndex);
                // Extract the message after the brackets
                String actualMessage = message.substring(endIndex + 1);
                // Send the message to the specific user using notifyModelSpecific()
                if (targetUsername.equals(username)) {
                    model.notifyModelSpecific(username, "chatC:" + "Server" + "," + "Why would you send messages to yourself?");
                    model.notifyModelSpecific(username, "chatC:" + username + "," + message);
                } else {
                    for (Player player : model.getPlayersList()) {
                        if (targetUsername.equals(player.getUsername())) {
                            model.notifyModelSpecific(targetUsername, "chatC:" + username + " [PVT]" + "," + actualMessage);
                            model.notifyModelSpecific(username, "chatC:" + username + "," + message);
                            return;
                        }
                    }
                    model.notifyModelSpecific(username, "chatC:" + "Server" + "," + "This player does not exist:" + "'" + targetUsername + "'");
                }
            }
        } else {
            model.notifyModelGeneric("chatC:" + username + "," + message);
        }
    }
}
