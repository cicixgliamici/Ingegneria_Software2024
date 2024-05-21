package org.example.server;

import org.example.server.rmi.RMIServerImpl;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Classe RMIServer che gestisce l'inizializzazione e la gestione del server RMI.
 */
public class RMIServer {
    private int port; // Porta su cui il server RMI sarà in ascolto.
    private Server mainServer; // Riferimento al server principale per la logica di business.
    private RMIServerImpl rmiServerImpl; // Implementazione del server RMI.
    private Registry registry; // RMI Registry instance

    /**
     * Costruttore della classe RMIServer.
     * @param port Porta su cui il server RMI ascolterà.
     * @param mainServer Istanza del server principale per delegare la gestione delle richieste.
     */
    public RMIServer(int port, Server mainServer) {
        this.port = port;
        this.mainServer = mainServer;
    }

    /**
     * Avvia il server RMI creando un registro RMI sulla porta specificata e
     * pubblicando l'implementazione del server RMI per consentire ai client RMI di invocare metodi remoti.
     */
    public void start() {
        try {
            // Crea un'istanza dell'implementazione del server RMI.
            rmiServerImpl = new RMIServerImpl(mainServer);

            // Prova a ottenere un registro RMI esistente
            try {
                registry = LocateRegistry.getRegistry(port);
                registry.list(); // Prova a comunicare con il registro
            } catch (Exception e) {
                // Se non esiste, crea un nuovo registro RMI
                registry = LocateRegistry.createRegistry(port);
            }

            // Registra l'oggetto remoto (rmiServerImpl) nel registro RMI con il nome "RMIServer".
            registry.rebind("RMIServer", rmiServerImpl);

            System.out.println("RMI server started on port " + port);
        } catch (Exception e) {
            // Stampa stack trace di eccezioni come problemi di rete, errori di binding, ecc.
            e.printStackTrace();
        }
    }

    /**
     * Arresta il server RMI disassociando e annullando l'esportazione dell'oggetto remoto.
     */
    public void stop() {
        try {
            if (registry != null) {
                registry.unbind("RMIServer");
                UnicastRemoteObject.unexportObject(rmiServerImpl, true);
                System.out.println("RMI server stopped.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
