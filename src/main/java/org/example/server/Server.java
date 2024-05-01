package org.example.server;

import org.example.controller.Controller;
import org.example.model.Model;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private Model model;
    private Controller controller;
    private Map<String, JSONObject> commands = new HashMap<>();
    private Map<String, String> players = new HashMap<>();

    public Server(int port) throws IOException, ParseException {
        this.port = port;
        this.model = new Model();
        this.controller = new Controller(model);
        loadCommands();
    }

    public void startServer() {
        int numConnections = 0;
        int numMaxConnections = 1; // Imposta il numero massimo di giocatori come default a 1
        ExecutorService executor = Executors.newFixedThreadPool(128);
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);
            while (numConnections < numMaxConnections) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    String clientIP = clientSocket.getInetAddress().getHostAddress(); // Ottiene l'IP del client
                    System.out.println("Connessione accettata da: " + clientIP);

                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    // Richiedi lo username al client
                    out.println("Enter your username:");
                    String username = in.readLine();

                    // Richiedi il numero massimo di giocatori solo al primo client che si connette
                    if (players.isEmpty()) {
                        out.println("Enter the maximum number of players (1-4):");
                        numMaxConnections = Integer.parseInt(in.readLine());
                    }

                    // Aggiungi lo username alla mappa dei giocatori
                    players.put(clientIP, username);

                    executor.submit(new ServerClientHandler(clientSocket, commands, model, controller));
                    numConnections++;
                } catch (IOException e) {
                    System.out.println("Error handling client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port " + port + ": " + e.getMessage());
        }
        System.out.println("Server stopped with " + numConnections + " connections");
        for (String string: players.values()){
            System.out.println(string);
        }
        executor.shutdown();

    }

    public void loadCommands() throws IOException {
        String path = "src/main/resources/Commands.json"; // Ensure this path is correct
        String text = new String(Files.readAllBytes(Paths.get(path)));
        JSONObject obj = new JSONObject(text);
        JSONObject jsonCommands = obj.getJSONObject("commands");
        for (String key : jsonCommands.keySet()) {
            commands.put(key, jsonCommands.getJSONObject(key));
        }
    }

}
