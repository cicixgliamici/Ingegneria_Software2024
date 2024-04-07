package org.example.model;

//* import com.sun.org.apache.xpath.internal.operations.Mod;
import org.example.model.PlayArea.Player;
import org.example.model.deck.Card;
import org.example.model.deck.Deck;
import org.example.model.deck.enumeration.Side;
import org.example.model.deck.enumeration.Type;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

//* Class in which the Model is properly setted to start the match

public class ModelController {
    private static Deck ResourcesDeck;
    private static Deck GoldDeck;
    private static Deck ObjectDeck;
    private static Deck StarterDeck;

    private List<Player> Players;

    private GameFlow gameFlow;

   /* public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {
        ModelController modelController= new ModelController();
        modelController.CreateDeck();
        // Place Deck on Game Area
        // Put 2 cards on ground
       //modelController.InizitializePlayers();
       Player p1= new Player(StarterDeck.drawCard());
    } */

    // Create the four decks
    public void CreateDeck () throws IOException, ParseException {
        ResourcesDeck = new Deck(Type.RESOURCES);
        //ResourcesDeck.printAllCards();
        //ResourcesDeck.printCard(0);
        ResourcesDeck.shuffle();
        //ResourcesDeck.printCard(0);
        //Card card = ResourcesDeck.drawCard();
        //card.print();
        GoldDeck = new Deck(Type.GOLD);
        GoldDeck.shuffle();
        ObjectDeck = new Deck(Type.OBJECT);
        ObjectDeck.shuffle();
        StarterDeck = new Deck(Type.STARTER);
        StarterDeck.shuffle();
        Card cartaInizialePlayer1 = StarterDeck.drawCard();
    }

    // Add the player from the
    public void AddPlayer(Player P){
        //il player P non viene creato qua ma deve essere creato quando si connette, nel momento della creazione sceglie gia come piazzare la sua carta inizale
        //funziona? se io devo scegliere come posizionare la carta iniziale ma non so ancora che carte ho in mano come faccio?
        //chi crea il player? lo creiamo in locale o lo crea il server?
        Players.add(P);
    }
    public void InizitializePlayers(){
        for (Player P: Players){
            P.addCard(ResourcesDeck.drawCard());
            P.addCard(ResourcesDeck.drawCard());
            P.addCard(GoldDeck.drawCard());
            // Add Token Color
        }
    }

}
