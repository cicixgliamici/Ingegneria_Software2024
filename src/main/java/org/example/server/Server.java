package org.example.server;

import org.example.controller.Controller;
import org.example.controller.Player;
import org.example.model.Model;
import org.example.server.rmi.RMIClientCallbackInterface;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Main server class that handles TCP and RMI client connections,
 * manages game state, and communicates with clients.
 */
public class Server implements ModelChangeListener {
    private int tcpPort;
    private int rmiPort;
    protected Model model;
    protected Controller controller;
    private List<Player> players;
    protected Map<String, JSONObject> commands = new HashMap<>();
    protected Map<String, PrintWriter> clientWriters = new HashMap<>();
    protected Map<Socket, String> socketToUsername = new HashMap<>();
    protected Map<String, RMIClientCallbackInterface> rmiClientCallbacks = new HashMap<>();
    private List<String> availableColors;
    private AtomicInteger setObjStarterCount = new AtomicInteger(0);
    protected ExecutorService executor;
    private TCPServer tcpServer;
    private RMIServer rmiServer;
    private int numConnections = 0;
    public int numMaxConnections = 4;

    // Called from PortSelection main
    public Server(int tcpPort, int rmiPort) throws IOException, ParseException {
        this.tcpPort = tcpPort;
        this.rmiPort = rmiPort;
        this.model = new Model();
        this.model.addModelChangeListener(this);
        this.players = new ArrayList<>();
        loadCommands();
        this.controller = new Controller(model);
        this.availableColors = new ArrayList<>(Arrays.asList("Red", "Blue", "Green", "Yellow"));
    }

    //Called from PortSelection main, go to TCP/RMI start
    public void startServer() {
        executor = Executors.newFixedThreadPool(128);
        tcpServer = new TCPServer(tcpPort, this);
        rmiServer = new RMIServer(rmiPort, this);
        executor.submit(tcpServer::start);
        executor.submit(rmiServer::start);
    }

    //Called from handleConnection, go to checkForGameStarter
    public void handleNewTCPClient(String username, PrintWriter out) throws RemoteException {
        addPlayer(username);
        clientWriters.put(username, out);
        checkForGameStart();
    }

    public void handleNewRMIClient(String username, RMIClientCallbackInterface clientCallback) throws RemoteException {
        addPlayer(username);
        rmiClientCallbacks.put(username, clientCallback);
        checkForGameStart();
    }

    //Called from handleNewTCPClient, go to gameFlow
    private void checkForGameStart() throws RemoteException {
        if (numConnections == numMaxConnections) {
            onModelGeneric("Match started");
            controller.setPlayers(players);
            controller.initializeController();
            try {
                gameFlow(numConnections);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Called from checkForGameStart, go to todo
    public void gameFlow(int numConnections) throws IOException {
        waitForSetObjStarter(numConnections);
    }

    private void waitForSetObjStarter(int numConnections) {
        while (setObjStarterCount.get() < numConnections) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("All clients have set correctly");
        try {
            onModelGeneric("message:3");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void incrementSetObjStarterCount() {
        setObjStarterCount.incrementAndGet();
    }

    public void loadCommands() throws IOException {
        String path = "src/main/resources/Commands.json";
        String text = new String(Files.readAllBytes(Paths.get(path)));
        JSONObject obj = new JSONObject(text);
        JSONObject jsonCommands = obj.getJSONObject("commands");
        for (String key : jsonCommands.keySet()) {
            commands.put(key, jsonCommands.getJSONObject(key));
        }
    }

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

    public List<String> getAvailableColors() {
        return availableColors;
    }
}
