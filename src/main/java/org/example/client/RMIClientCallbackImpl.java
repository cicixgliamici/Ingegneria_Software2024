package org.example.client;

import org.example.server.rmi.RMIClientCallbackInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementazione dell'interfaccia RMIClientCallbackInterface.
 * Questa classe è responsabile della gestione dei messaggi ricevuti dal server RMI.
 * Quando il server invia un messaggio al client, questo viene gestito dal metodo receiveMessage,
 * che delega l'elaborazione del messaggio alla classe Client.
 */
public class RMIClientCallbackImpl extends UnicastRemoteObject implements RMIClientCallbackInterface {
    private final Client client;  // Riferimento alla classe Client per gestire i messaggi ricevuti

    /**
     * Costruttore della classe RMIClientCallbackImpl.
     * @param client Riferimento alla classe Client.
     * @throws RemoteException Se si verifica un errore RMI.
     */
    public RMIClientCallbackImpl(Client client) throws RemoteException {
        this.client = client;
    }

    /**
     * Metodo chiamato dal server per inviare un messaggio al client.
     * @param message Il messaggio inviato dal server.
     * @throws RemoteException Se si verifica un errore RMI.
     */
    @Override
    public void receiveMessage(String message) throws RemoteException {
        client.handleMessage(message);  // Delegare la gestione del messaggio alla classe Client
    }
}
