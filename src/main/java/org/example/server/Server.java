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

public class Server {
    private boolean chooseMode;
    private int numPlayers;

    public void setParameters(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public Card getCard() {
        //todo metodo che chiede al client una carta dalla mano
        return new Card();
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setChooseMode(boolean chooseMode) {
        this.chooseMode = chooseMode;
    }

    public boolean isChooseMode() {
        return chooseMode;
    }

    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<String, ClientConnection> waitingConnection = new HashMap<>();
    private Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();
    private ArrayList<String> nicknames = new ArrayList<>();


    /**
     * Method deregisterClient deletes a client from the hashMaps and active lists, unregistering his
     * connection with the server
     *
     * @param c of type ClientConnection
     */


    public synchronized void deregisterConnection(ClientConnection c) {
        ClientConnection opponent = playingConnection.get(c);
        ClientConnection opponent2 = playingConnection.get(opponent);

        if (opponent != null) {
            opponent.closeConnection();
        }

        if (opponent2 != null) {
            opponent2.closeConnection();
        }

        playingConnection.remove(c);
        playingConnection.remove(opponent);
        playingConnection.remove(opponent2);
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
            Player player1 = new Player(keys.get(1), 0);
            Player player2 = new Player(keys.get(0), 1);
            View player1View = new RemoteView(player1, keys.get(0), c1);
            View player2View = new RemoteView(player2, keys.get(1), c2);

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
}
