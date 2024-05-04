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

            // Chiudo gli stream e il socket
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private void executeCommand(String commandKey, PrintWriter out) {
        try {
            JSONObject commandDetails = commands.get(commandKey);
            String className = commandDetails.getString("className");
            String methodName = commandDetails.getString("methodName");
            Class<?> cls = Class.forName(className);
            Method method;
            Object response;
            // Check if the command details include parameters
            if (commandDetails.optJSONArray("parameters") != null && !commandDetails.getJSONArray("parameters").isEmpty()) {
                JSONArray params = commandDetails.getJSONArray("parameters");
                Class<?>[] paramTypes = new Class[params.length()];
                Object[] paramValues = new Object[params.length()];
                for (int i = 0; i < params.length(); i++) {
                    JSONObject param = params.getJSONObject(i);
                    String type = param.getString("type");
                    paramTypes[i] = type.equals("int") ? int.class : Class.forName(type);
                    paramValues[i] = type.equals("Model") ? model : Integer.parseInt(param.getString("value"));
                }
                method = cls.getDeclaredMethod(methodName, paramTypes);
                response = method.invoke(controller, paramValues);
            } else {
                method = cls.getDeclaredMethod(methodName);
                response = method.invoke(model); // Assuming the method is on 'this' instance
            }
            out.println("Command executed: " + response);
        } catch (Exception e) {
            out.println("Error executing command: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
