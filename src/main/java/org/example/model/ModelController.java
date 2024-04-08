package org.example.model;

//* import com.sun.org.apache.xpath.internal.operations.Mod;
import org.example.model.PlayArea.Node;
import org.example.model.PlayArea.Player;
import org.example.model.PlayArea.ScoreBoard;
import org.example.model.PlayArea.Token;
import org.example.model.deck.Card;
import org.example.model.deck.Deck;
import org.example.model.deck.enumeration.Color;
import org.example.model.deck.enumeration.Side;
import org.example.model.deck.enumeration.Type;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//* Class in which the Model is properly setted to start the match

public class ModelController {
    //*  Attributes for the decks
    private static Deck ResourcesDeck;
    private static Deck GoldDeck;
    private static Deck ObjectDeck;
    private static Deck StarterDeck;
    private List<Deck> placedDeck;              //* Arraylist where there are placed Deck Resource and Gold
    private List<Card> placedCard;              //* List of placed card
    //* Attributes for the Gameflow controll
    private HashMap<Player, Token> Players;     //* Associate Player to Toke
    private ScoreBoard scoreBoard;              //* Object scoreboard to memorize points
    private List<String> winnerPlayer;
    private List<Player> PlayersList;
    private GameFlow gameFlow;

   public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {
        ModelController modelController= new ModelController();
        modelController.CreateDeck();
        // Place Deck on Game Area
        // Put 2 cards on ground
       //modelController.InizitializePlayers();
       Player p1= new Player(StarterDeck.drawCard());

       //per prova il player uno pesca una carta
       p1.DrawCard(ResourcesDeck);

       //il player uno sceglie la carta da giocare e la gioca
       p1.ModifyGameArea();

       //metodo di controllo



    }

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
        PlayersList.add(P);
    }
    public void InizitializePlayers(){
        for (Player P: PlayersList){
            P.addCard(ResourcesDeck.drawCard());
            P.addCard(ResourcesDeck.drawCard());
            P.addCard(GoldDeck.drawCard());
            // Add Token Color
        }
    }
    public void GameFlow(ScoreBoard scoreBoard){     //Called from the Controller
        Players = new HashMap<>();
        placedDeck = new ArrayList<>();
        placedCard = new ArrayList<>();
        winnerPlayer = new ArrayList<>();
        this.scoreBoard = scoreBoard;
    }

    public void MapPlayerToken(Player player, Color color){  //Associate a player to a specific color
        scoreBoard.addToken(color);
        Players.put(player, scoreBoard.getToken(color));
    }

    public static Deck getResourcesDeck() {
        return ResourcesDeck;
    }
}
