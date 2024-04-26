package org.example.server;

import org.example.controller.Controller;
import org.example.controller.Player;
import org.example.model.Model;
import org.example.model.deck.Card;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/* Il server resta in ascolto del primo giocatore, quando esso entra crea model e controller
    dopodiché controlla che il numero di giocatori in attesa sia uguale a numPlayers, dopodiché
    cicla sui giocatori e esegue tutte le chiamate.
 */

public class Server {
    private int numPlayers;
    private boolean first = false;

    public void setParameters(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public Card getCard() {
        //todo metodo che chiede al client una carta dalla mano
        return new Card();
    }
    private ServerSocket serverSocket; //socket del server
    private ExecutorService executor = Executors.newFixedThreadPool(128); //creazione dei thread
    private Map<String, ClientConnection> waitingConnection = new HashMap<>();
    private List<ClientConnection> playerConnected = new ArrayList<>();
    private ArrayList<String> nicknames = new ArrayList<>();

    /**
     * Method to initialize server
     * @param port
     * @throws IOException
     */
    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    /*
    public synchronized void deregisterConnection(ClientConnection c) {
        ClientConnection player = c;
        if(player != null){
            player.closeConnection();
        }
        playerConnected.remove(c);
        Iterator<String> iterator = waitingConnection.keySet().iterator();
        while (iterator.hasNext()) {
            if (waitingConnection.get(iterator.next()) == c) {
                iterator.remove();
            }
        }
    }

    public synchronized void lobby(ClientConnection c, String name) throws IOException, ParseException {
        List<String> keys = new ArrayList<>(waitingConnection.keySet());
        for (int i = 0; i < keys.size(); i++) {
            ClientConnection connection = waitingConnection.get(keys.get(i));
            connection.asyncSend("Connected User: " + keys.get(i));
        }
        waitingConnection.put(name, c);
        nicknames.add(name);
        keys = new ArrayList<>(waitingConnection.keySet());

        if (waitingConnection.size() == 2 && numPlayers == 2) {
            Model model = new Model();
            Controller controller = new Controller(model);
            controller.setParameters(numPlayers);

            ClientConnection c1 = waitingConnection.get(keys.get(1));
            ClientConnection c2 = waitingConnection.get(keys.get(0));
            //todo implemntazione della view da far partire

            controller.addPlayer(player1);
            controller.addPlayer(player2);

            model.addObserver(player1View);
            model.addObserver(player2View);
            player1View.addObserver(controller);
            player2View.addObserver(controller);
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c1);
            waitingConnection.remove(keys.get(1));
            waitingConnection.remove(keys.get(0));
            c1.asyncSend(model);
            c2.asyncSend(model);

            if (model.getCurrentPlayer() == player1.getId()) {
                c1.asyncSend(gameMessage.moveMessage);
                c2.asyncSend(gameMessage.waitMessage);
            } else {
                c2.asyncSend(gameMessage.moveMessage);
                c1.asyncSend(gameMessage.waitMessage);
            }


        } else if (waitingConnection.size() == 3 && numPlayers == 3) {
            Model model = new Model();
            Controller controller = new Controller(model);
            controller.setParameters(numPlayers);


            ClientConnection c1 = waitingConnection.get(keys.get(2));
            ClientConnection c2 = waitingConnection.get(keys.get(1));
            ClientConnection c3 = waitingConnection.get(keys.get(0));
            Player player1 = new Player(keys.get(2), 0);
            Player player2 = new Player(keys.get(1), 1);
            Player player3 = new Player(keys.get(0), 2);
            //View player1View = new RemoteView(player1, keys.get(1) + " and " + keys.get(0), c1);
            //View player2View = new RemoteView(player2, keys.get(2) + " and " + keys.get(0), c2);
            //View player3View = new RemoteView(player3, keys.get(2) + " and " + keys.get(1), c3);

            controller.addPlayer(player1);
            controller.addPlayer(player2);
            controller.addPlayer(player3);

            model.addObserver(player1View);
            model.addObserver(player2View);
            model.addObserver(player3View);
            player1View.addObserver(controller);
            player2View.addObserver(controller);
            player3View.addObserver(controller);
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c3);
            playingConnection.put(c3, c1);
            waitingConnection.remove(keys.get(2));
            waitingConnection.remove(keys.get(1));
            waitingConnection.remove(keys.get(0));
            c1.asyncSend(model);
            c2.asyncSend(model);
            c3.asyncSend(model);

            if (model.getCurrentPlayer() == player1.getId()) {
                c1.asyncSend(gameMessage.moveMessage);
                c2.asyncSend(gameMessage.waitMessage);
                c3.asyncSend(gameMessage.waitMessage);
            } else if (model.getCurrentPlayer() == player2.getId()) {
                c1.asyncSend(gameMessage.waitMessage);
                c2.asyncSend(gameMessage.moveMessage);
                c3.asyncSend(gameMessage.waitMessage);
            } else {
                c1.asyncSend(gameMessage.waitMessage);
                c2.asyncSend(gameMessage.waitMessage);
                c3.asyncSend(gameMessage.moveMessage);
            }
        } else if (waitingConnection.size() == 4 && numPlayers == 4) {
            Model model = new Model();
            Controller controller = new Controller(model);
            controller.setParameters(numPlayers);

            ClientConnection c1 = waitingConnection.get(keys.get(3));
            ClientConnection c2 = waitingConnection.get(keys.get(2));
            ClientConnection c3 = waitingConnection.get(keys.get(1));
            ClientConnection c4 = waitingConnection.get(keys.get(0));
            Player player1 = new Player(keys.get(2), 0);
            Player player2 = new Player(keys.get(1), 1);
            Player player3 = new Player(keys.get(0), 2);
            Player player4 = new Player(keys.get(0), 3);
            //View player1View = new RemoteView(player1, keys.get(1) + " and " + keys.get(0), c1);
            //View player2View = new RemoteView(player2, keys.get(2) + " and " + keys.get(0), c2);
            //View player3View = new RemoteView(player3, keys.get(2) + " and " + keys.get(1), c3);
            //View player4View = new RemoteView(player4, keys.get(1) + " and " + keys.get(0), c4);

            controller.addPlayer(player1);
            controller.addPlayer(player2);
            controller.addPlayer(player3);
            controller.addPlayer(player4);

            model.addObserver(player1View);
            model.addObserver(player2View);
            model.addObserver(player3View);
            model.addObserver(player4View);
            player1View.addObserver(controller);
            player2View.addObserver(controller);
            player3View.addObserver(controller);
            player4View.addObserver(controller);
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c3);
            playingConnection.put(c3, c1);
            playingConnection.put(c4, c1);
            playingConnection.put(c4, c2);
            playingConnection.put(c4, c3);
            waitingConnection.remove(keys.get(3));
            waitingConnection.remove(keys.get(2));
            waitingConnection.remove(keys.get(1));
            waitingConnection.remove(keys.get(0));
            c1.asyncSend(model);
            c2.asyncSend(model);
            c3.asyncSend(model);
            c4.asyncSend(model);

            if (model.getCurrentPlayer() == player1.getId()) {
                c1.asyncSend(gameMessage.moveMessage);
                c2.asyncSend(gameMessage.waitMessage);
                c3.asyncSend(gameMessage.waitMessage);
                c4.asyncSend(gameMessage.waitMessage);
            } else if (model.getCurrentPlayer() == player2.getId()) {
                c1.asyncSend(gameMessage.waitMessage);
                c2.asyncSend(gameMessage.moveMessage);
                c3.asyncSend(gameMessage.waitMessage);
                c4.asyncSend(gameMessage.waitMessage);
            } else if (model.getCurrentPlayer() == player3.getId()) {
                c1.asyncSend(gameMessage.waitMessage);
                c2.asyncSend(gameMessage.waitMessage);
                c3.asyncSend(gameMessage.moveMessage);
                c4.asyncSend(gameMessage.waitMessage);
            } else {
                c1.asyncSend(gameMessage.waitMessage);
                c2.asyncSend(gameMessage.waitMessage);
                c3.asyncSend(gameMessage.waitMessage);
                c4.asyncSend(gameMessage.moveMessage);
            }
        }
    }
    public int getNumPlayers() {
        return numPlayers;
    }
    public boolean isFirst(){
        return First;
    }

    public ArrayList<String> getNicknames() {
        return nicknames;
    }

    public Map<String, ClientConnection> getWaitingConnection() {
        return waitingConnection;
    }

    public void setFirst(boolean first) {
        First = first;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

     */
}
