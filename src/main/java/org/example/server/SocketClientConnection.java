
package org.example.server;

import org.example.listener.*;

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
                for (String s : server.getNicknames()){
                    if(s.equals(nickname)){
                        sendMessage("Nickname already use! Try to insert another:");
                        errorName = true;
                    }
                }
                if(nickname.equals("")){
                    sendMessage("Write a valid Name! Please try again:");
                    errorName = true;
                }
            } while (errorName);
            sendMessage(nickname);
            if(server.isFirst()){
                sendMessage("Please wait... Waiting for other player");
                while(server.getWaitingConnection().size() == 1 && server.isFirst()){
                        Thread.sleep(250);
            }
        }
            server.lobby(this, nickname);
            synchronized (server){
                if(server.getWaitingConnection().size() == 1){
                    server.setFirst(true);
                }
                do{
                    sendMessage("Please enter the number of player");
                    read = in.readObject();
                    if(Integer.parseInt((String)read) > 4){
                        sendMessage("Please enter a correct number between 2 and 4");
                    }
                } while (Integer.parseInt((String)read) > 4);
                if(server.getWaitingConnection().size() < server.getNumPlayers() && server.getWaitingConnection().size() != 0)
                    sendMessage("\nWaiting for another player\n");
            }
            while(isActive()){

                read = in.readObject();
                //notify((String)read); da sistemare
            }
    } catch (Exception error){
            System.out.println("Error!" + error.getMessage());
        } finally {
            close();
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
