package org.example.server;

import org.example.controller.Controller;
import org.example.model.Model;
import org.example.model.ModelChangeListener;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;


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

/** Server class that start the server and wait for the clients
*   Subscribed to the listeners list of model (and actually the only one
*   and tell the Clients when something changes
*/

public class Server implements ModelChangeListener {
    private int port; 
    private Model model;
    private Controller controller;
    private Map<String, JSONObject> commands = new HashMap<>();        // Map for the commands written by the Client and the commands in the JSON
    private Map<String, PrintWriter> clientWriters = new HashMap<>();  // Map that associates an username (unique, from client) to a PrintWriter object

    public Server(int port) throws IOException, ParseException {
        this.port = port;
        this.model = new Model();
        this.controller = new Controller(model);
        this.model.addModelChangeListener(this);  //Subscribes the Server to the model listeners list
        loadCommands();                           //Load the commands from resources->Commands.json

    }

    public void startServer() {
        //todo se un client si sconnette e si riconnette con lo stesso username bisogna ricollegarlo allo stesso PrintWriter
        int numConnections = 0;
        int numMaxConnections = 1; // Default number of players
        ExecutorService executor = Executors.newFixedThreadPool(128);
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);  //we choose the port from PortSelection
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //Reads text from a character-input stream
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);  //Prints formatted representations of objects to a text-output stream
                    if (numConnections >= numMaxConnections) {
                        out.println("Connection failed: maximum number of players reached");
                        clientSocket.close(); // Close connection
                        continue; // Re-start the while to add other clients
                    }
                    // Username from the client, must be unique for every Match
                    out.println("Enter your username:");
                    String username = in.readLine();
                    // Only the first client set the number of players
                    if (clientWriters.isEmpty()) {
                        out.println("Enter the maximum number of players (1-4):");
                        numMaxConnections = Integer.parseInt(in.readLine());
                    }
                    clientWriters.put(username, out);
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
        for (String string : clientWriters.keySet()) {
            System.out.println(string);
        }
        executor.shutdown();
    }

    /** Load command from a JSON, where we can choose what parameters do we
    *   need from a client and what we use from the server
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

    /** For every client added, we send them a message when
    * the listener tell us something in the model is changed
    */
    @Override
    public void onModelChange(String updateMessage) {
        for (PrintWriter writer : clientWriters.values()) {
            writer.println(updateMessage);
            writer.flush();
        }
    }
}
