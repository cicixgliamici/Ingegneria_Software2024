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
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private Model model;
    private Controller controller;
    private Map<String, JSONObject> commands = new HashMap<>();
    private List<String> usernames = new ArrayList<>();

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
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    // Verifica se il numero massimo di connessioni è stato raggiunto
                    if (numConnections >= numMaxConnections) {
                        out.println("Connection failed: maximum number of players reached");
                        clientSocket.close(); // Chiudi la connessione
                        continue; // Vai alla prossima iterazione del ciclo per accettare nuovi client
                    }

                    // Richiedi lo username al client
                    out.println("Enter your username:");
                    String username = in.readLine();

                    // Richiedi il numero massimo di giocatori solo al primo client che si connette
                    if (usernames.isEmpty()) {
                        out.println("Enter the maximum number of players (1-4):");
                        numMaxConnections = Integer.parseInt(in.readLine());
                    }

                    usernames.add(username);
                    out.println("Connection successful");

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
        for (String string : usernames) {
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
