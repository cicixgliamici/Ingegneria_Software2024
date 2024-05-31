package org.example.server;

import org.example.controller.Controller;
import org.example.controller.Player;
import org.example.exception.ExceptionManager;

import org.example.model.Model;
import org.example.server.rmi.RMIClientCallbackInterface;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.parsers.ParserConfigurationException;
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
    private Socket socket;
    private Model model;
    private Controller controller;
    private Map<String, JSONObject> commands;
    private Map<Socket, String> socketToUsername;
    private Server server;
    private RMIClientCallbackInterface rmiClientCallback;
    private String username;
    private ExceptionManager exceptionManager;

    public ServerClientHandler(Socket socket, Map<String, JSONObject> commands, Model model, Controller controller, Map<Socket, String> socketToUsername, Server server) {
        this.socket = socket;
        this.commands = commands;
        this.model = model;
        this.controller = controller;
        this.socketToUsername = socketToUsername;
        this.server = server;
        this.exceptionManager = new ExceptionManager(); // Inizializza exceptionManager
    }

    public ServerClientHandler(RMIClientCallbackInterface rmiClientCallback, String username, Map<String, JSONObject> commands, Model model, Controller controller, Server server) {
        this.rmiClientCallback = rmiClientCallback;
        this.username = username;
        this.commands = commands;
        this.model = model;
        this.controller = controller;
        this.server = server;
        this.exceptionManager = new ExceptionManager(); // Inizializza exceptionManager
    }

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

    private void handleRMIConnection() {
        // Handle RMI connection commands if necessary
    }
    private void executeCommand(String inputLine, String username) {
        System.out.println("ricevuto " + inputLine);
        try {
            String[] parts = inputLine.split(":");
            String commandKey = parts[0];
            if (!commands.containsKey(commandKey)) {
                System.out.println("Comando non riconosciuto");
                return;
            }
            if (server.gameFlow == null || server.getGameFlow().isYourTurn(username, commandKey) || commandKey.equals("chatS")) {
                System.out.println("eseguendo " + inputLine);
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
                if (method == null) {
                    System.out.println("Metodo non trovato: " + methodName);
                    return;
                }
                method.invoke(player, paramValues);
                if (commandKey.equals("setObjStarter")) {
                    server.incrementSetObjStarterCount();
                }
                if (commandKey.equals("draw")) {
                    server.showDrawCardArea();
                }
                if (server.getGameFlow() != null) {
                    server.getGameFlow().incrementTurn();
                }
            } else if (!server.getGameFlow().isYourTurn(username, commandKey)) {
                server.onModelSpecific(username, "message:4");
            }
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            exceptionManager.handleException((Exception) targetException, username, server);
        } catch (Exception e) {
            exceptionManager.handleException(e, username, server);
        }
    }
}