package org.example.controller;

import org.example.enumeration.*;
import org.example.model.Model;
import org.example.model.PlayArea.ScoreBoard;
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

    // For Test
    public Player(){

    }

    /** Method for the TUI to see the Player's hand
     *
     */
    public void SeeHand(Model model){
        List<Card> hand= model.getPlayerArea(this).getHand();
        System.out.println("this is your hand: \n");
        for (Card c: hand){
            System.out.println(c);
        }
    }

    public Card ChosenCard (Model model) {
        boolean InvalidCard= true;
        Card card=null;
        while (InvalidCard){
            SeeHand(model);
            System.out.println("Chose the number of the card you want to play");
            Scanner scanner= new Scanner(System.in);
            //todo verificare che il numero inserito sia valido
            int choice = scanner.nextInt();
            card = model.getPlayerArea(this).getHand().get(choice);
            InvalidCard= model.getPlayerArea(this).CheckPlayForGold(card);
        }
        return card;
    }

    public void Play (Model model){
        //mostra nodi
        Card chosencard= ChosenCard(model);
        model.getPlayerArea(this).PlayACard(chosencard);
        model.getPlayerArea(this).getHand().remove(chosencard);
    }

    public int ChooseStarterSide(){
        System.out.println("Pick your starter card side 1 - front , 2 - back");
        Scanner scanner= new Scanner(System.in);
        return scanner.nextInt();
    }

    public void Draw (Model model){
        //todo gestire l'eccezione di un inserimento non valido
        Card card;
        model.getDrawingCardArea().DisplayVisibleCard();
        Scanner scanner=new Scanner(System.in);
        int choice;
        System.out.println("Press:" +
                "\n1 to draw from the deck" +
                "\n2 to draw from the visible cards");
        choice= scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                System.out.println("Press:" +
                "\n1 to draw from the resource deck" +
                "\n2 to draw from the gold deck");
                choice= scanner.nextInt();
                scanner.nextLine();
                if(choice==1){
                    card = model.getDrawingCardArea().drawCardFromDeck(Type.RESOURCES);
                    model.getPlayerArea(this).getHand().add(card);
                }
                if (choice==2) {
                    card = model.getDrawingCardArea().drawCardFromDeck(Type.GOLD);
                    model.getPlayerArea(this).getHand().add(card);
                }
                break;

            case 2:
                System.out.println("Press:" +
                        "\n1 to draw from the resource area" +
                        "\n2 to draw from the gold area");
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1) {
                    int maxIndex = model.getDrawingCardArea().getVisibleReCard().size() - 1;
                    System.out.println("Enter the number of the resource card you want to draw (0-" + maxIndex + "):");
                    int resourceIndex = scanner.nextInt();
                    if (resourceIndex >= 0 && resourceIndex <= maxIndex) {
                        card = model.getDrawingCardArea().drawCardFromVisible(Type.RESOURCES, resourceIndex);
                        model.getPlayerArea(this).getHand().add(card);
                    }
                }
                if (choice == 2) {
                    int maxIndex = model.getDrawingCardArea().getVisibleGoCard().size() - 1;
                    System.out.println("Enter the number of the gold card you want to draw (0-" + maxIndex + "):");
                    int goldIndex = scanner.nextInt();
                    if (goldIndex >= 0 && goldIndex <= maxIndex) {
                        card = model.getDrawingCardArea().drawCardFromVisible(Type.GOLD, goldIndex);
                        model.getPlayerArea(this).getHand().add(card);
                    }
                    break;
                }
        }


    }

    @Override
    public String toString() {
        return this.username;
    }

    public void UpdateScoreboardPoints(Model model) throws IllegalArgumentException{
        ScoreBoard scoreBoard= model.getScoreBoard();
        if(scoreBoard.GetPlayerPoint(this)<model.getPlayerArea(this).getCounter().getPointCounter()){
            scoreBoard.UpdatePlayerPoint(this, model.getPlayerArea(this).getCounter().getPointCounter());
        } else {
            throw new IllegalArgumentException();
        }
    }
}


