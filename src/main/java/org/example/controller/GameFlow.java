package org.example.controller;
import org.example.model.Model;
import org.example.enumeration.*;

import java.util.HashMap;
import java.util.List;

import  org.example.model.deck.*;
import org.example.model.PlayArea.*;

/** The Gameflow is a controller of the status of the match, it depends on the Controller and allows the players only to Draw and Play
 * then calculates at the end of each turn the points of the player.
 */

public class GameFlow {
    List<Player> players;
    Model model;

    public GameFlow(List<Player> players, Model model) {
        this.players=players;
        this.model= model;
        Rounds();
        Player winnner = EndGame();
        System.out.println("the winner is : " + winnner.username);
    }

    //todo primo round:
    public void Rounds () {
        System.out.println("First round: \n");
        //todo tutti i giocatori devono solamente piazzare una carta
        boolean IsEnd = false;
        while (!IsEnd) {
            for (Player p : players) {
                System.out.println("Player: " + p + " Play phase");
                p.Play(model);
                System.out.println("Player: " + p + " Draw phase");
                p.Draw(model);
                //todo muovere la pedina del player sulla scoreboard se ha fatto punti
            }
            IsEnd = model.Checkpoints();
        }
    }

    public Player EndGame(){
        //todo calcolo punteggi finali degli obbiettivi nascosti e pubblici
        for (Player p : players) {
            model.getPlayerArea(p).privateObject(model);
            model.getPlayerArea(p).publicObjects(model);
        }
        //todo decreto vincitore
        return new Player("Ha vinto lo sport, Good Game Well Played");
    }































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



    /*
    // TODO va sposato in Player sennò bisogna passargli troppi parametri
    //il game flow deve soltanto gestire i flussi di gioco, non il gioco intero. Deve dividere le fasi di ciasun player
    //quindi x es: player1 : pesca, player 1 gioca, player 2 pesca etc. le varie giocate le gestisce il singolo player.
    public void Draw(Player player, DrawingCardArea drawingCardArea, ChooseAreaToPick chooseAreaToPick, Card chooseCard, Type type){
        switch(chooseAreaToPick) {
            case VISIBLEAREA:
                drawingCardArea.RemoveCardFromVC(chooseCard);
                player.addCard(chooseCard);
            case DECKSAREA:
                player.addCard(drawingCardArea.drawCardFromDeck(type));
        }
    }

    public void Play(Card choosenCard, HashMap<Player, Token> Players, ScoreBoard scoreBoard, Player player) throws IllegalArgumentException{
        if (Players.containsKey(player)) {
            // TODO implementare la chiamata della funzione su dove mettere la carta
            scoreBoard.addColorPoints(Players.get(player).getColor(), choosenCard); //prendo in ingresso la carta scelta e aggiorno lo scoreboard dei punti
            Players.get(player).addPoints(choosenCard); //aggiorno i punti relativo al token
        }
        else {
            throw new IllegalArgumentException("Player doesn't exist!\n");
        }
    }
    */

}
