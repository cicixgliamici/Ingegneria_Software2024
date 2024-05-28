
package org.example.server;

import org.example.controller.Controller;
import org.example.controller.GameFlow;
import org.example.controller.Player;
import org.example.enumeration.Color;
import org.example.model.Model;
import org.example.server.rmi.RMIClientCallbackInterface;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Classe principale del server che gestisce le connessioni client TCP e RMI,
 * amministra lo stato del gioco, e comunica con i clienti.
 */
public class Server implements ModelChangeListener {
    private int tcpPort; // Porta TCP per le connessioni dei client.
    private int rmiPort; // Porta RMI per le connessioni dei client.
    protected Model model; // Modello del gioco che mantiene lo stato.
    protected Controller controller; // Controller che gestisce la logica di gioco.
    private List<Player> players; // Lista dei giocatori, sia RMI che TCP.
    protected Map<String, JSONObject> commands = new HashMap<>(); // Comandi supportati dal server.
    protected Map<String, PrintWriter> clientWriters = new HashMap<>(); // Mappa per gestire gli output verso i client TCP.
    protected Map<Socket, String> socketToUsername = new HashMap<>(); // Mappa socket a username per identificazione.
    protected Map<String, RMIClientCallbackInterface> rmiClientCallbacks = new HashMap<>(); // Callbacks per i client RMI.
    private List<String> availableColors; // Colori disponibili per la selezione da parte dei giocatori.
    private AtomicInteger setObjStarterCount = new AtomicInteger(0); // Contatore per tracciare quanti giocatori hanno scelto la carta iniziale.
    protected ExecutorService executor; // Executor per gestire thread multipli.
    private TCPServer tcpServer; // Server TCP.
    private RMIServer rmiServer; // Server RMI.
    private int numConnections = 0; // Contatore delle connessioni attive.
    public int numMaxConnections = 4; // Numero massimo di connessioni.
    public GameFlow gameFlow; // Flusso di gioco che gestisce le fasi del gioco.

    /**
     * Costruttore della classe Server.
     * @param tcpPort Porta TCP.
     * @param rmiPort Porta RMI.
     */
    public Server(int tcpPort, int rmiPort) throws IOException, ParseException {
        this.tcpPort = tcpPort;
        this.rmiPort = rmiPort;
        this.model = new Model();
        this.model.addModelChangeListener(this);
        this.players = new ArrayList<>();
        loadCommands(); // Carica i comandi dal file JSON.
        this.controller = new Controller(model);
        this.availableColors = new ArrayList<>(Arrays.asList("Red", "Blue", "Green", "Yellow"));
    }

    /**
     * Avvia il server, sia TCP che RMI.
     */
    public void startServer() {
        executor = Executors.newFixedThreadPool(128); // Crea un pool di thread.
        tcpServer = new TCPServer(tcpPort, this); // Inizializza il server TCP.
        rmiServer = new RMIServer(rmiPort, this); // Inizializza il server RMI.
        executor.submit(tcpServer::start); // Avvia il server TCP.
        executor.submit(rmiServer::start); // Avvia il server RMI.
    }

    public void stopServer() {
        // Stop TCP server
        if (tcpServer != null) {
            tcpServer.stop();
        }
        // Stop RMI server
        if (rmiServer != null) {
            rmiServer.stop();
        }
        // Shutdown executor service
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }
        System.out.println("Server stopped.");
    }


    /**
     * Gestisce l'arrivo di un nuovo client TCP.
     */
    public void handleNewTCPClient(String username, PrintWriter out) throws RemoteException {
        checkForGameStart(); // Controlla se il gioco può iniziare.
    }

    /**
     * Gestisce l'arrivo di un nuovo client RMI.
     */
    public void handleNewRMIClient(String username, RMIClientCallbackInterface clientCallback) throws RemoteException {
        addPlayer(username); // Aggiunge il giocatore alla lista.
        rmiClientCallbacks.put(username, clientCallback); // Registra il callback RMI per il client.
        checkForGameStart(); // Controlla se il gioco può iniziare.
    }

    /**
     * Verifica se il gioco può iniziare, basato sul numero di connessioni.
     */
    public void checkForGameStart() throws RemoteException {
        if (numConnections == numMaxConnections) {
            onModelGeneric("message:10");// Notifica i clienti che il match è iniziato.
            controller.setPlayers(players); // Imposta i giocatori nel controller.
            controller.initializeController(); // Inizializza il controller.
            waitForSetObjStarter(numConnections); // Aspetta che tutti i giocatori scelgano la carta iniziale.
            onModelGeneric("visibleArea:");
            gameFlow = new GameFlow(players, model, this); // Crea il flusso di gioco.
            gameFlow.setMaxTurn(new AtomicInteger(numConnections*2)); // Imposta il numero massimo di turni.
        }
        else {
            onModelGeneric("message:9");
            System.out.println("message:9");
        }
    }

    /**
     * Aspetta che tutti i giocatori abbiano scelto la carta iniziale.
     */
    public void waitForSetObjStarter(int numConnections) {
        while (setObjStarterCount.get() < numConnections) {
            try {
                Thread.sleep(100); // Pause briefly to reduce CPU load while waiting.
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Properly handle thread interruption.
            }
        }
        System.out.println("All clients have set correctly");
        try {
            // Generate the order message with usernames in the order they connected.
            String orderMessage = generatePlayerOrderMessage();
            onModelGeneric(orderMessage); // Send the order of players.
            onModelGeneric("message:3"); // Notify clients that all have set their starter cards.
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private String generatePlayerOrderMessage() {
        String[] playerOrder = new String[4]; // Array to hold up to four usernames or "null".
        // Fill the array with usernames or "null" based on the number of connected players.
        for (int i = 0; i < playerOrder.length; i++) {
            if (i < players.size()) {
                playerOrder[i] = players.get(i).getUsername();
            } else {
                playerOrder[i] = "null"; // Use "null" as a placeholder if there are fewer than four players.
            }
        }

        // Join the player names (or "nulls") with commas to create the final message.
        return "order:" + String.join(",", playerOrder);
    }

    public String generateColor() {
        String[] colors = new String[4]; // Array to hold up to four usernames or "null".
        // Fill the array with usernames or "null" based on the number of connected players.
        for (int i = 0; i < colors.length; i++) {
            if (i < availableColors.size()) {
                colors[i] = availableColors.get(i);
            } else {
                colors[i] = "null"; // Use "null" as a placeholder if there are fewer than four players.
            }
        }

        // Join the player names (or "nulls") with commas to create the final message.
        return String.join(",", colors);
    }

    public void notifyPlayerPoints() {
        StringBuilder message = new StringBuilder("points:");
        for (int i = 0; i < 4; i++) {
            if (i < players.size()) {
                Player player = players.get(i);
                int points = model.getScoreBoard().getPlayerPoint(player);
                message.append(player.getUsername()).append(",").append(points);
            } else {
                message.append("null,0");
            }
            if (i < 3) {
                message.append(",");
            }
        }
        try {
            onModelGeneric(message.toString());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Incrementa il contatore delle scelte delle carte iniziali.
     */
    public void incrementSetObjStarterCount() {
        setObjStarterCount.incrementAndGet();
    }

    /**
     * Carica i comandi disponibili dal file JSON.
     */
    public void loadCommands() throws IOException {
        String path = "src/main/resources/Commands.json";
        String text = new String(Files.readAllBytes(Paths.get(path)));
        JSONObject obj = new JSONObject(text);
        JSONObject jsonCommands = obj.getJSONObject("commands");
        for (String key : jsonCommands.keySet()) {
            commands.put(key, jsonCommands.getJSONObject(key));
        }
    }

    /**
     * Gestisce i cambiamenti del modello e notifica i clienti.
     */
    public void onModelChange(String username, String specificMessage, String generalMessage) throws RemoteException {
        synchronized (this) {
            for (Map.Entry<String, PrintWriter> entry : clientWriters.entrySet()) {
                PrintWriter writer = entry.getValue();
                if (entry.getKey().equals(username)) {
                    writer.println(specificMessage);
                } else {
                    writer.println(generalMessage);
                }
                writer.flush();
            }
            for (Map.Entry<String, RMIClientCallbackInterface> entry : rmiClientCallbacks.entrySet()) {
                try {
                    if (entry.getKey().equals(username)) {
                        entry.getValue().receiveMessage(specificMessage);
                    } else {
                        entry.getValue().receiveMessage(generalMessage);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onModelSpecific(String username, String specificMessage) {
        synchronized (this) {
            for (Map.Entry<String, PrintWriter> entry : clientWriters.entrySet()) {
                PrintWriter writer = entry.getValue();
                if (entry.getKey().equals(username)) {
                    writer.println(specificMessage);
                    writer.flush();
                }
            }
            for (Map.Entry<String, RMIClientCallbackInterface> entry : rmiClientCallbacks.entrySet()) {
                try {
                    if (entry.getKey().equals(username)) {
                        entry.getValue().receiveMessage(specificMessage);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onModelGeneric(String generalMessage) throws RemoteException {
        synchronized (this) {
            for (PrintWriter writer : clientWriters.values()) {
                writer.println(generalMessage);
                writer.flush();
            }
            for (RMIClientCallbackInterface callback : rmiClientCallbacks.values()) {
                try {
                    callback.receiveMessage(generalMessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addPlayer(String username) {
        synchronized (this) {
            players.add(new Player(username));
            numConnections++;
        }
    }

    public void chooseColor(String username, String color) {
        synchronized (this) {
            if (availableColors.contains(color)) {
                availableColors.remove(color);
                System.out.println(username + " has chosen the color " + color);
            } else {
                System.out.println("Color " + color + " not available.");
            }
        }
    }

    public Controller getController() {
        return controller;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<String> getColor() {return availableColors;}

    public List<String> getAvailableColors() {
        return availableColors;
    }

    public GameFlow getGameFlow() {
        return gameFlow;
    }
    public int getTcpPort() {
        return tcpPort;
    }

    public int getRmiPort() {
        return rmiPort;
    }

    public Model getModel() {
        return model;
    }
    public void setNumMaxConnections(int numConnections){
        System.out.println("Con max:" + numConnections);
        this.numMaxConnections=numConnections;
    }


}
