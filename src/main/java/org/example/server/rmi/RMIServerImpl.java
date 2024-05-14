package org.example.server.rmi;

import org.example.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementazione dell'interfaccia del server RMI. Questa classe gestisce la logica di connessione
 * e comunicazione con i client RMI.
 */
public class RMIServerImpl extends UnicastRemoteObject implements RMIServerInterface {
    private Server server;  // Riferimento al server principale
    private Map<String, RMIClientCallbackInterface> clientCallbacks = new ConcurrentHashMap<>();  // Mappa per memorizzare i callback dei client

    /**
     * Costruttore della classe RMIServerImpl.
     * @param server Riferimento al server principale.
     * @throws RemoteException Se si verifica un errore RMI.
     */
    public RMIServerImpl(Server server) throws RemoteException {
        this.server = server;
    }

    /**
     * Metodo chiamato dai client per connettersi al server RMI.
     * @param username Il nome utente del client.
     * @param clientCallback Il callback del client per ricevere messaggi dal server.
     * @throws RemoteException Se si verifica un errore RMI.
     */
    @Override
    public void connect(String username, RMIClientCallbackInterface clientCallback) throws RemoteException {
        server.addPlayer(username);  // Aggiungi il giocatore al server principale
        clientCallbacks.put(username, clientCallback);  // Memorizza il callback del client
        // Eventuale logica aggiuntiva per gestire la connessione del giocatore
    }

    /**
     * Metodo chiamato dai client per scegliere un colore.
     * @param username Il nome utente del client.
     * @param color Il colore scelto dal client.
     * @throws RemoteException Se si verifica un errore RMI.
     */
    @Override
    public void chooseColor(String username, String color) throws RemoteException {
        server.chooseColor(username, color);  // Chiama il metodo del server principale per gestire la scelta del colore
        // Eventuale logica aggiuntiva per gestire la scelta del colore
    }

    /**
     * Metodo per inviare un messaggio a tutti i client connessi.
     * @param message Il messaggio da inviare.
     */
    public void sendToAllClients(String message) {
        for (RMIClientCallbackInterface callback : clientCallbacks.values()) {  // Itera su tutti i callback dei client
            try {
                callback.receiveMessage(message);  // Invia il messaggio al client tramite il suo callback
            } catch (RemoteException e) {
                e.printStackTrace();  // Stampa lo stack trace in caso di eccezione
            }
        }
    }
}
