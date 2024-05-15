package org.example.server;

import org.example.server.rmi.RMIServerImpl;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    private int port;
    private Server mainServer;
    private RMIServerImpl rmiServerImpl;

    public RMIServer(int port, Server mainServer) {
        this.port = port;
        this.mainServer = mainServer;
    }

    //From startServer, go to ServerRMIImplementation, then check RMIClient
    public void start() {
        try {
            rmiServerImpl = new RMIServerImpl(mainServer);
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("RMIServer", rmiServerImpl);
            System.out.println("RMI server started on port " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendToAllClients(String message) {
        rmiServerImpl.sendToAllClients(message);
    }
}
