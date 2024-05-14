package org.example.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote {
    void connect(String username, RMIClientCallbackInterface clientCallback) throws RemoteException;
    void chooseColor(String username, String color) throws RemoteException;
}
