package org.example.server;

import org.example.controller.Controller;
import org.example.controller.Player;

import org.example.exception.ExceptionManager;
import org.example.exception.InvalidCardException;
import org.example.exception.PlaceholderNotValid;
import org.example.model.Model;
import org.example.server.rmi.RMIClientCallbackInterface;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * Handles client connections and processes commands from both TCP and RMI clients.
 */
public class ServerClientHandler implements Runnable {
    private Socket socket;  // Socket for TCP communication
    private Model model;  // Game model
    private Controller controller;  // Game controller
    private Map<String, JSONObject> commands;  // Map of commands and their details
    private Map<Socket, String> socketToUsername;  // Map to associate sockets with usernames
    private Server server;  // Main server instance
    private RMIClientCallbackInterface rmiClientCallback;  // RMI client callback interface
    private String username;  // Username of the client
    private ExceptionManager exceptionManager;

    /**
     * Constructor for TCP connections.
     *
     * @param socket           The socket for the TCP connection.
     * @param commands         The map of commands.
     * @param model            The game model.
     * @param controller       The game controller.
     * @param socketToUsername The map associating sockets with usernames.
     * @param server           The main server instance.
     */
    public ServerClientHandler(Socket socket, Map<String, JSONObject> commands, Model model, Controller controller, Map<Socket, String> socketToUsername, Server server) {
        this.socket = socket;
        this.commands = commands;
        this.model = model;
        this.controller = controller;
        this.socketToUsername = socketToUsername;
        this.server = server;
    }

    /**
     * Constructor for RMI connections.
     *
     * @param rmiClientCallback The RMI client callback interface.
     * @param username          The username of the client.
     * @param commands          The map of commands.
     * @param model             The game model.
     * @param controller        The game controller.
     * @param server            The main server instance.
     */
    public ServerClientHandler(RMIClientCallbackInterface rmiClientCallback, String username, Map<String, JSONObject> commands, Model model, Controller controller, Server server) {
        this.rmiClientCallback = rmiClientCallback;
        this.username = username;
        this.commands = commands;
        this.model = model;
        this.controller = controller;
        this.server = server;
    }

    /**
     * Runs the handler, processing either TCP or RMI connections based on the initialization.
     */
    public void run() {
        try {
            if (socket != null) {
                handleTCPConnection();
            } else if (rmiClientCallback != null) {
                handleRMIConnection();
            }
        } catch (Exception e) {
            exceptionManager.handleException(e, username, server);
        }
    }
    /**
     * Handles TCP connections by reading input from the socket and executing commands.
     */
    private void handleTCPConnection() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                executeCommand(inputLine, socketToUsername.get(socket));  // Execute the command from the input
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Placeholder method for handling RMI connections.
     */
    private void handleRMIConnection() {
        // Handle RMI connection commands if necessary
    }


    /**
     * Executes a command based on the input line.
     *
     * @param inputLine The input command line.
     * @param username  The username of the client.
     */

    private void executeCommand(String inputLine, String username) {
        System.out.println("Eseguendo " + inputLine);
        try {
            String[] parts = inputLine.split(":");
            String commandKey = parts[0]; // Command key
            if (!commands.containsKey(commandKey)) {
                System.out.println("Comando non riconosciuto");
                return; // Command not recognized
            }
            System.out.println(server.gameFlow);
            if (server.gameFlow == null || server.getGameFlow().isYourTurn(username, commandKey)) {
                JSONObject commandDetails = commands.get(commandKey); // Command details from the map
                String className = commandDetails.getString("className");
                String methodName = commandDetails.getString("methodName");
                JSONArray jsonParams = commandDetails.getJSONArray("parameters");
                Class<?> cls = Class.forName(className); // Load the class
                Class<?>[] paramTypes = new Class[jsonParams.length()]; // Parameter types
                Object[] paramValues = new Object[jsonParams.length()]; // Parameter values
                String[] params = parts.length > 1 ? parts[1].split(",") : new String[0];
                if (params.length != jsonParams.length() - 1) { // Exclude the 'Model' type parameter
                    System.out.println("Numero di parametri non corrispondente");
                    return;
                }
                int j = 0;
                for (int i = 0; i < jsonParams.length(); i++) {
                    JSONObject param = jsonParams.getJSONObject(i);
                    String type = param.getString("type");
                    if (type.equals("Model") && "server".equals(param.getString("source"))) {
                        paramTypes[i] = Model.class;
                        paramValues[i] = model;
                    } else {
                        paramTypes[i] = type.equals("int") ? int.class : Class.forName(type);
                        paramValues[i] = type.equals("int") ? Integer.parseInt(params[j]) : params[j]; // Increment j only for user-supplied params
                        j++;
                    }
                }
                Player player = controller.getPlayerByUsername(username);
                if (player == null) {
                    System.out.println("Player non trovato: " + username);
                    return;
                }
                Method method = cls.getDeclaredMethod(methodName, paramTypes);
                if (method == null) {
                    System.out.println("Metodo non trovato: " + methodName);
                    return;
                }
                method.invoke(player, paramValues); // Invoke the method on the player
                if (commandKey.equals("setObjStarter")) {
                    server.incrementSetObjStarterCount();
                }
                if(commandKey.equals("draw")){
                    server.showDrawCardArea();
                }
                if (server.getGameFlow() != null) {
                    server.getGameFlow().incrementTurn();
                }
            }
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            System.err.println("InvocationTargetException: " + targetException.getMessage());
            targetException.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error executing command: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
