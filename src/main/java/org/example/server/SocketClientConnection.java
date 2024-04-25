
package org.example.server;

import org.example.listener.*;
import sun.jvm.hotspot.utilities.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientConnection implements ClientConnection, Runnable, MoveSubject{

    private Socket socket;
    private ObjectOutputStream out;
    private Server server;

    private boolean active = true;


    private boolean isActive(){
        return active;
    }

    public synchronized void sendMessage(Object message){
        try{
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(Exception error){
            System.err.println(error.getMessage());
        }
    }
    public SocketClientConnection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }

    public synchronized void closeConnection(){
        sendMessage("Connection closed!");
        try{
            socket.close();
        }   catch (IOException error){
            System.err.println("Error closing the socket!");
        }
        active = false;
    }

    private void close(){
        closeConnection();
        System.out.println("Closing the connection...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }


    @Override
    public void run() {
        String nickname;
        int numPlayers;
        try{
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            //sendMessage(gameMessage.eriantys); da indagare

            sendMessage("Welcome to Codex Naturalis! Please insert your nickname:");
            Object read;
            boolean errorName;
            do{
                errorName = false;
                read = in.readObject();
                nickname = (String) read;
            }
        }
    }
    @Override
    public void asyncSend(final Object message){
        new Thread(() -> sendMessage(message)).start();
    }

    @Override
    public void registerListener(MoveListener listener){}
    @Override
    public void unregisterListener(MoveListener listener){}
    @Override
    public void notifyListeners(String move){}
    @Override
    public void addObserver(MoveListener moveListener){}
}
