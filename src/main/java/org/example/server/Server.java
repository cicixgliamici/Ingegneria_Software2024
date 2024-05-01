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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private Model model;
    private Controller controller;
    private Map<String, JSONObject> commands = new HashMap<>();

    public Server(int port) throws IOException, ParseException {
        this.port = port;
        this.model = new Model();
        this.controller = new Controller(model);
        loadCommands();
    }

    public void startServer() {
        ExecutorService executor = Executors.newFixedThreadPool(128);
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    executor.submit(new ServerClientHandler(clientSocket,commands, model, controller));
                }
                /*


                 */
                catch (IOException e) {
                    System.out.println("Error handling client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port " + port + ": " + e.getMessage());
        }
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
