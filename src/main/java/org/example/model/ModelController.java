package org.example.model;

//* import com.sun.org.apache.xpath.internal.operations.Mod;
import org.example.model.PlayArea.*;
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
    private DrawingCardArea drawingCardArea;
    private HashMap<Player, Token> Players;     //* Associate Player to Toke
    private ScoreBoard scoreBoard;              //* Object scoreboard to memorize points
    private List<String> winnerPlayer; //scriverà il nome del vincitore
    private List<Player> PlayersList = new ArrayList<>();
    private GameFlow gameFlow;

    public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {
        //todo non c'è il costruttore del model controller
        ModelController modelController = new ModelController();
        //modelController.CreateDeck();
        // Place Deck on Game Area
        // Put 2 cards on ground
        //Player p1 = new Player(StarterDeck.drawCard());
        //p1.InitializeGameArea();

        //modelController.getPlayersList().add(p1);  //nel momento in cui viene creato player questo ha una carta iniziale disposta nella sua game area e una lista di carte hand
        //una volta che si collegano tutti i player il model procede con l'inizializzazione assegnando a tutti i player connessi le carte della mano

        //modelController.InizitializePlayers();

        //inizia il flusso di gioco
        //il player uno sceglie la carta da giocare e la gioca
        //p1.ModifyGameArea();

        //per prova il player uno pesca una carta
        //p1.drawCard(ResourcesDeck);
        //metodo di controllo


    }

    public ModelController() throws IOException, ParseException {
        drawingCardArea = new DrawingCardArea();
        scoreBoard = new ScoreBoard();
        Players = new HashMap<>();
        winnerPlayer = new ArrayList<>();
        gameFlow = new GameFlow(); // TODO valutare l'aggiunta di stati per il gameflow
    }

    /* Create the four decks
    public void CreateDeck() throws IOException, ParseException {
        /** Creo i deck che con le carte che verranno distribuite ai player
         *  scelgo la carta obbiettivo comune
         *  scelgo la carta oggetto segreta
        //ResourcesDeck = new Deck(Type.RESOURCES); già inizializzata dalla classe DrawingCardArea.java
        //ResourcesDeck.printAllCards();
        //ResourcesDeck.printCard(0);
        //ResourcesDeck.shuffle();
        //ResourcesDeck.printCard(0);
        //Card card = ResourcesDeck.drawCard();
        //card.print();
        //GoldDeck = new Deck(Type.GOLD); già implementato da DrawingCardArea.java
        //GoldDeck.shuffle();
        ObjectDeck = new Deck(Type.OBJECT);
        ObjectDeck.shuffle();
        StarterDeck = new Deck(Type.STARTER);
        StarterDeck.shuffle();
        //Card cartaInizialePlayer1 = StarterDeck.drawCard();
    }
*/
    public void ConnectPlayer(Player P, Color color){
        PlayersList.add(P);
        scoreBoard.addToken(color);
        Players.put(P, scoreBoard.getToken(color));
        P.addCard(drawingCardArea.drawCardFromDeck(Type.RESOURCES));
        P.addCard(drawingCardArea.drawCardFromDeck(Type.RESOURCES));
        P.addCard(drawingCardArea.drawCardFromDeck(Type.GOLD));
        P.setInitialCard(drawingCardArea.drawCardFromDeck(Type.STARTER));
        // TODO mettere la chiamata la metodo che assegna il l'obbiettivo segreto al giocatore
    }

    // Add the player from the
    public void AddPlayer(Player P) {
        //il player P non viene creato qua ma deve essere creato quando si connette, nel momento della creazione sceglie gia come piazzare la sua carta inizale
        //funziona? se io devo scegliere come posizionare la carta iniziale ma non so ancora che carte ho in mano come faccio?
        //chi crea il player? lo creiamo in locale o lo crea il server?
        PlayersList.add(P);
    }

    public void MapPlayerToken(Player player, Color color) {  //Associate a player to a specific color
        scoreBoard.addToken(color);
        Players.put(player, scoreBoard.getToken(color));
    }

    public HashMap<Player, Token> getPlayers() {
        return Players;
    }

    public List<Player> getPlayersList() {
        return PlayersList;
    }

}