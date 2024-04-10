package org.example.model;
import org.example.model.deck.enumeration.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import  org.example.model.deck.*;
import org.example.model.PlayArea.*;

/** The Gameflow is a controller of the status of the match, it depends on the Controller and allows the players only to Draw an Play
 *
 */

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
   /*public void addPlayer(Token token, Player player) throws IllegalArgumentException{
        if (Players.containsKey(player)){
            throw new IllegalArgumentException("Player already initialized!\n");
        }
        Players.put(player,token);
    } */
}
