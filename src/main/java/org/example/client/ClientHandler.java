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

/**
 * The ClientHandler class is responsible for handling communication between the client and server.
 * It reads commands from the server, parses them, and executes corresponding methods in the view.
 *
 * Implementing the Runnable interface allows instances of this class to be executed by a thread.
 * The run method defines the code that constitutes the new thread's execution.
 */
public class ClientHandler implements Runnable {
    private Socket socket;  // The socket for communicating with the server
    private View view;  // The view interface for interacting with the user
    private Map<String, JSONObject> commands = new HashMap<>();  // A map to store available commands and their details

    /**
     * Constructor for the ClientHandler class.
     *
     * @param socket The socket for communicating with the server.
     * @param commands A map containing the available commands and their details.
     * @param view The view interface for interacting with the user.
     */
    public ClientHandler(Socket socket, Map<String, JSONObject> commands, View view) {
        this.socket = socket;
        this.commands = commands;
        this.view = view;
    }

    /**
     * The run method is invoked when the thread starts.
     * It continuously reads input from the server and executes the corresponding commands.
     */
    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String inputLine;
            // Continuously read input from the server
            while ((inputLine = in.readLine()) != null) {
                // Execute the command received from the server
                executeCommand(inputLine);
            }
        } catch (IOException e) {
            // Handle any I/O errors that occur during communication
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Executes the command received from the server.
     *
     * @param inputLine The command input line received from the server.
     */
    private void executeCommand(String inputLine) {
        try {
            // Parse the input command
            JSONParser parser = new JSONParser();
            String[] parts = inputLine.split(":");
            String commandKey = parts[0];
            // Check if the command is recognized and has the necessary parameters
            if (parts.length < 2 || !commands.containsKey(commandKey)) {
                System.out.println("Command not recognized or parameters missing.");
                return;
            }
            // Retrieve the command details from the commands map
            JSONObject commandDetails = commands.get(commandKey);
            String methodName = (String) commandDetails.get("methodName");
            JSONArray jsonParams = (JSONArray) commandDetails.get("parameters");
            // Prepare the parameter types and values for method invocation
            Class<?>[] paramTypes = new Class[jsonParams.size()];
            Object[] paramValues = new Object[jsonParams.size()];
            String[] params = parts[1].split(",");
            // Ensure the parameter count matches the expected count
            if (params.length != jsonParams.size()) {
                throw new IllegalArgumentException("Parameter count mismatch for command " + commandKey);
            }
            // Parse and convert the parameters to the appropriate types
            for (int i = 0; i < jsonParams.size(); i++) {
                JSONObject param = (JSONObject) jsonParams.get(i);
                String type = (String) param.get("type");
                paramTypes[i] = type.equals("int") ? int.class : String.class;
                paramValues[i] = type.equals("int") ? Integer.parseInt(params[i]) : params[i];
            }
            // Use reflection to find and invoke the corresponding method in the view
            java.lang.reflect.Method method = view.getClass().getMethod(methodName, paramTypes);
            method.invoke(view, paramValues);
        } catch (Exception e) {
            // Handle any errors that occur during command execution
            System.out.println("Error executing command: " + e.getMessage());
            e.printStackTrace();
        }
    }
}