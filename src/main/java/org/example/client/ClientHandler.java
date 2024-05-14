package org.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import org.example.view.View;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ClientHandler implements Runnable {
    private Socket socket;
    private View view;
    private Map<String, JSONObject> commands = new HashMap<>();

    public ClientHandler(Socket socket, Map<String, JSONObject> commands, View view) {
        this.socket = socket;
        this.commands = commands;
        this.view = view;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                executeCommand(inputLine);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void executeCommand(String inputLine) {
        try {
            JSONParser parser = new JSONParser();
            String[] parts = inputLine.split(":");
            String commandKey = parts[0];
            if (parts.length < 2 || !commands.containsKey(commandKey)) {
                System.out.println("Command not recognized or parameters missing.");
                return;
            }
            JSONObject commandDetails = commands.get(commandKey);
            String methodName = (String) commandDetails.get("methodName");
            JSONArray jsonParams = (JSONArray) commandDetails.get("parameters");
            Class<?>[] paramTypes = new Class[jsonParams.size()];
            Object[] paramValues = new Object[jsonParams.size()];
            String[] params = parts[1].split(",");
            if (params.length != jsonParams.size()) {
                throw new IllegalArgumentException("Parameter count mismatch for command " + commandKey);
            }
            for (int i = 0; i < jsonParams.size(); i++) {
                JSONObject param = (JSONObject) jsonParams.get(i);
                String type = (String) param.get("type");
                paramTypes[i] = type.equals("int") ? int.class : String.class;
                paramValues[i] = type.equals("int") ? Integer.parseInt(params[i]) : params[i];
            }
            java.lang.reflect.Method method = view.getClass().getMethod(methodName, paramTypes);
            method.invoke(view, paramValues);
        } catch (Exception e) {
            System.out.println("Error executing command: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
