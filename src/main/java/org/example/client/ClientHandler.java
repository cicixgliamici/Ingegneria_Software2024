package org.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import org.example.view.View;
import org.json.JSONArray;
import org.json.JSONObject;

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
            String[] parts = inputLine.split(":");
            String commandKey = parts[0];
            String[] params = parts.length > 1 ? parts[1].split(",") : new String[0];
            if (!commands.containsKey(commandKey)) {
                System.out.println("Command not recognized.");
                return;
            }
            JSONObject commandDetails = commands.get(commandKey);
            String methodName = commandDetails.getString("methodName");
            JSONArray jsonParams = commandDetails.getJSONArray("parameters");
            Class<?>[] paramTypes = new Class[jsonParams.length()];
            Object[] paramValues = new Object[jsonParams.length()];
            for (int i = 0; i < jsonParams.length(); i++) {
                JSONObject param = jsonParams.getJSONObject(i);
                String type = param.getString("type");
                paramTypes[i] = type.equals("int") ? int.class : String.class;
                paramValues[i] = type.equals("int") ? Integer.parseInt(params[i]) : params[i];
            }
            // Reflection to call method on View
            java.lang.reflect.Method method = view.getClass().getMethod(methodName, paramTypes);
            method.invoke(view, paramValues);
        } catch (Exception e) {
            System.out.println("Error executing command: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
