package org.example.server;

import org.example.controller.Controller;
import org.example.controller.Player;
import org.example.exception.ExceptionManager;
import org.example.model.Model;
import org.example.server.rmi.RMIClientCallbackInterface;
import org.example.view.gui.listener.EvListener;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Handles client connections and processes commands from both TCP and RMI clients.
 */
public class ServerClientHandler implements Runnable {
    private Socket socket;
    private Model model;
    private Controller controller;
    private Map<String, JSONObject> commands;
    private Map<Socket, String> socketToUsername;
    private Server server;
    private RMIClientCallbackInterface rmiClientCallback;
    private String username;
    private ExceptionManager exceptionManager;
    private List<EvListener> listeners = new ArrayList<>();

    /**
     * Constructor for handling TCP connections.
     *
     * @param socket The client socket.
     * @param commands The map of commands.
     * @param model The game model.
     * @param controller The game controller.
     * @param socketToUsername Map of sockets to usernames.
     * @param server The main server instance.
     */
    public ServerClientHandler(Socket socket, Map<String, JSONObject> commands, Model model, Controller controller, Map<Socket, String> socketToUsername, Server server) {
        this.socket = socket;
        this.commands = commands;
        this.model = model;
        this.controller = controller;
        this.socketToUsername = socketToUsername;
        this.server = server;
        this.exceptionManager = new ExceptionManager(); // Initialize exceptionManager
    }

    /**
     * Constructor for handling RMI connections.
     *
     * @param rmiClientCallback The RMI client callback interface.
     * @param username The client's username.
     * @param commands The map of commands.
     * @param model The game model.
     * @param controller The game controller.
     * @param server The main server instance.
     */
    public ServerClientHandler(RMIClientCallbackInterface rmiClientCallback, String username, Map<String, JSONObject> commands, Model model, Controller controller, Server server) {
        this.rmiClientCallback = rmiClientCallback;
        this.username = username;
        this.commands = commands;
        this.model = model;
        this.controller = controller;
        this.server = server;
        this.exceptionManager = new ExceptionManager(); // Initialize exceptionManager
    }

    /**
     * The main run method that handles either TCP or RMI connections based on the provided parameters.
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
     * Handles TCP client connections.
     */
    private void handleTCPConnection() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                executeCommand(inputLine, socketToUsername.get(socket));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Handles RMI client connections.
     */
    private void handleRMIConnection() {
        // Handle RMI connection commands if necessary
    }

    /**
     * Executes a command received from a client.
     *
     * @param inputLine The input command line.
     * @param username The username of the client.
     */
    private void executeCommand(String inputLine, String username) {
        System.out.println("ricevuto SH: " + inputLine);
        try {
            String[] parts = inputLine.split(":");
            String commandKey = parts[0];
            if (!commands.containsKey(commandKey)) {
                System.out.println("Comando non riconosciuto");
                return;
            }
            System.out.println("server gameflow in SH: " + server.gameFlow + "server commandkey in SH: " + commandKey);
            System.out.println("turn gameflow in SH: " + server.gameFlow.getTurn());
            if (server.gameFlow == null || server.getGameFlow().isYourTurn(username, commandKey) || commandKey.equals("chatS")) {
                System.out.println("eseguendo SH: " + inputLine);
                JSONObject commandDetails = commands.get(commandKey);
                String className = commandDetails.getString("className");
                String methodName = commandDetails.getString("methodName");
                JSONArray jsonParams = commandDetails.getJSONArray("parameters");
                Class<?> cls = Class.forName(className);
                Class<?>[] paramTypes = new Class[jsonParams.length()];
                Object[] paramValues = new Object[jsonParams.length()];
                String[] params = parts.length > 1 ? parts[1].split(",") : new String[0];
                if (params.length != jsonParams.length() - 1) {
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
                    } else if (type.equals("int")) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.parseInt(params[j]);
                        j++;
                    } else if (type.equals("string")) {
                        paramTypes[i] = String.class;
                        paramValues[i] = params[j];
                        j++;
                    } else {
                        paramTypes[i] = Class.forName(type);
                        paramValues[i] = params[j];
                        j++;
                    }
                }
                Player player = controller.getPlayerByUsername(username);
                if (player == null) {
                    System.out.println("Player non trovato: " + username);
                    return;
                }
                Method method = cls.getDeclaredMethod(methodName, paramTypes);
                method.invoke(player, paramValues);
                if (commandKey.equals("setObjStarter")) {
                    server.incrementSetObjStarterCount();
                }
                if (commandKey.equals("draw")) {
                    server.showDrawCardArea();
                }
                if (server.getGameFlow() != null && !commandKey.equals("setObjStarter") && !commandKey.equals("chatS")) {
                    server.getGameFlow().incrementTurn();
                    if (checkIfEnd() && !server.getGameFlow().isLastRoundAnnounced()) {
                        server.getGameFlow().startLastRound();
                        server.getGameFlow().setLastRoundAnnounced(true);
                        server.onModelGeneric("lastRound");
                    }
                }
            } else if (!server.getGameFlow().isYourTurn(username, commandKey)) {
                if (server.getGameFlow().getEndGame() == 1) {
                    server.onModelSpecific(username, "message:12");
                } else {
                    System.out.println("Non è il suo turno in SH");
                    server.onModelSpecific(username, "message:4");
                }
            }
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            exceptionManager.handleException((Exception) targetException, username, server);
        } catch (Exception e) {
            exceptionManager.handleException(e, username, server);
        }
    }

    /**
     * Checks if the game has reached an end condition.
     *
     * @return true if any player's point counter is 5 or more; false otherwise.
     * @throws RemoteException If there is an issue with remote method invocation.
     */
    public boolean checkIfEnd() throws RemoteException {
        for (Player player : server.getPlayers()) {
            if (model.getPlayerCardArea(player).getCounter().getPointCounter() >= 5) {
                return true;
            }
        }
        return false;
    }
}
