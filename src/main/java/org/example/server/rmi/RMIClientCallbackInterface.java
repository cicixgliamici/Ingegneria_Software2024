package org.example.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientCallbackInterface extends Remote {
    void receiveMessage(String message) throws RemoteException;
}