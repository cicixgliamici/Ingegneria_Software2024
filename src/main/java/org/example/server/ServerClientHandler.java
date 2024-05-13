package org.example.server;


import org.example.controller.Controller;
import org.example.controller.Player;
import org.example.model.Model;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.*;

/** Class for the interpretation of the commands written by the client
*/

public class ServerClientHandler implements Runnable {
    private Socket socket;
    private Model model;
    private Controller controller;

    private Map<String, JSONObject> commands = new HashMap<>();
    private Map<Socket, String> socketToUsername;

    public ServerClientHandler(Socket socket, Map<String, JSONObject> commands, Model model, Controller controller, Map<Socket, String> socketToUsername) {
        this.socket = socket;
        this.commands = commands;
        this.model = model;
        this.controller = controller;
        this.socketToUsername = socketToUsername;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received command: " + inputLine);  // Aggiungi questa linea per debug
                if (commands.containsKey(inputLine.split(":")[0])) {
                    executeCommand(inputLine, out);
                } else {
                    out.println("Command not recognized.");
                }
            }
            // Close stream and socket
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /** Read a string written from the client which must have this format:  method:param1,param2,param3
    *   If there is a method with the same name in the Commands.JSON, calls it with the parameters.
    *   EX: draw:int
    *       play:int,int,int,int
    */
    private void executeCommand(String inputLine, PrintWriter out) {
        try {
            String[] parts = inputLine.split(":");
            String commandKey = parts[0];
            if (!commands.containsKey(commandKey)) {
                out.println("Command not recognized: " + commandKey);
                return;
            }
            JSONObject commandDetails = commands.get(commandKey);
            String className = commandDetails.getString("className");
            String methodName = commandDetails.getString("methodName");
            JSONArray jsonParams = commandDetails.getJSONArray("parameters");
            Class<?> cls = Class.forName(className);
            Class<?>[] paramTypes = new Class[jsonParams.length()];
            Object[] paramValues = new Object[jsonParams.length()];
            String[] params = parts.length > 1 ? parts[1].split(",") : new String[0];
            if (params.length != jsonParams.length() - 1) {  // Exclude the 'Model' type parameter
                out.println("Parameter count mismatch for command: " + commandKey);
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
                    paramValues[i] = type.equals("int") ? Integer.parseInt(params[j++]) : params[j++];  // Increment j only for user-supplied params
                }
            }
            // Assume you have a method in your controller to get a Player by username.
            Player player = controller.getPlayerByUsername(socketToUsername.get(socket)); // Assuming you have such a method and mapping
            Method method = cls.getDeclaredMethod(methodName, paramTypes);
            Object response = method.invoke(player, paramValues); // Ensure you are invoking on the correct object
            //out.println("Command executed successfully: ");
        } catch (Exception e) {
            out.println("Error executing command: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
