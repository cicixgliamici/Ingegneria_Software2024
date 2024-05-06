package org.example.server;


import org.example.controller.Controller;
import org.example.model.Model;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/** Class for the interpretation of the commands written by the client
*/

public class ServerClientHandler implements Runnable {
    private Socket socket;
    private Model model;
    private Controller controller;

    private Map<String, JSONObject> commands = new HashMap<>();

    public ServerClientHandler(Socket socket, Map commands, Model model, Controller controller) {
        this.socket = socket;
        this.commands=commands;
        this.model=model;
        this.controller=controller;
    }
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (commands.containsKey(inputLine)) {
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
            String[] params = parts.length > 1 ? parts[1].split(",") : new String[0];
            if (!commands.containsKey(commandKey)) {
                out.println("Command not recognized.");
                return;
            }
            JSONObject commandDetails = commands.get(commandKey);
            String className = commandDetails.getString("className");
            String methodName = commandDetails.getString("methodName");
            JSONArray jsonParams = commandDetails.getJSONArray("parameters");
            Class<?> cls = Class.forName(className);
            Class<?>[] paramTypes = new Class[jsonParams.length()];
            Object[] paramValues = new Object[jsonParams.length()];
            for (int i = 0; i < jsonParams.length(); i++) {
                JSONObject param = jsonParams.getJSONObject(i);
                String type = param.getString("type");
                paramTypes[i] = type.equals("int") ? int.class : Class.forName(type);
                paramValues[i] = type.equals("Model") ? model : Integer.parseInt(params[i]); // Assicurati che l'indice esista
            }
            Method method = cls.getDeclaredMethod(methodName, paramTypes);
            Object response = method.invoke(controller, paramValues);
            out.println("Command executed: " + response.toString());
        } catch (Exception e) {
            out.println("Error executing command: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
