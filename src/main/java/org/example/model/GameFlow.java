package org.example.model;
import org.example.model.deck.enumeration.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import  org.example.model.deck.*;
import org.example.model.PlayArea.*;

public class GameFlow {
    //nel gameflow avrò tutte le informazioni del gioco e tutta la gestione dei punti e le chiamate per il calcolo dei punteggi
    /* dati:
    - i giocatori presenti (a ognuno di essi devo attribuire un username) quindi un hashmap
    - i deck presenti sul tavolo da gioco (il primo elemento avrà il deck dei resource, il secondo i deck delle carte oro)
    - la gameboard per il calcolo dei punteggi (oppure gestiti direttamente dal giocatore nella sua classe)
    - l'oggetto riferimento al controller
    - il vincitore da notificare

     Metodi:
     - inizializzazione della partita (deck, notifico l'inizio della partita al controller, distribuisce le carte, inizializza la gameboard)
     - gestisce la fine del turno
     - verifica che qualcuno abbia raggiunto i punti (NON CALCOLA I PUNTI)
     - notifica tutte le informazioni al controller da reperire al controller del player
     - inizializza la carta obbiettivo comune
     */

    private HashMap<Player, Token> Players;     //* Associate Player to Token
    private List<Deck> placedDeck;              //* Arraylist where there are placed Deck Resource and Gold
    private List<Card> placedCard;              //* List of placed card
    private ScoreBoard scoreBoard;              //* Object scoreboard to memorize points
    private List<String> winnerPlayer;
    //l'oggetto in riferimento al controller

    public GameFlow(ScoreBoard scoreBoard){     //Called from the Controller
        Players = new HashMap<>();
        placedDeck = new ArrayList<>();
        placedCard = new ArrayList<>();
        winnerPlayer = new ArrayList<>();
        this.scoreBoard = scoreBoard;
    }

    public void MapPlayerToken(Player player, Color color){
            scoreBoard.addToken(color);
            Players.put(player, scoreBoard.getToken(color));
    }

    public void StartGame(){
        //method called by the controller to initialize the game
        //chiamata per inizializzare i deck e porli negli arraylist (da chiedere)
    }
   public void addPlayer(Token token, Player player) throws IllegalArgumentException{
        if (Players.containsKey(player)){
            throw new IllegalArgumentException("Player already initialized!\n");
        }
        Players.put(player,token);
    }

    public void checkPoints(int id){

    }

}
