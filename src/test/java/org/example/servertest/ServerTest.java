package org.example.servertest;

import org.example.server.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for the Server.
 * This test focuses on verifying the correct instantiation and initial setup of the server.
 */
public class ServerTest {

    private Server server; // Instance of the server to be tested
    private final int tcpPort = 8000; // TCP port used for server connections
    private final int rmiPort = 1099; // RMI port used for server connections

    /**
     * Setup method executed before each test method.
     * It prepares the testing environment by initializing necessary dependencies and creating the server instance.
     */
    @BeforeEach
    public void setUp() throws Exception {
        try {
            // Attempt to clean up before setting up
            Registry registry = LocateRegistry.getRegistry(rmiPort);
            registry.unbind("RMIServer");
        } catch (NotBoundException | RemoteException e) {
            System.out.println("No need to unbind or cannot connect to registry: " + e.getMessage());
        }
        server = new Server(tcpPort, rmiPort);
        new Thread(() -> server.startServer()).start();
        Thread.sleep(1000);  // Allow time for the server to start
    }

    /**
     * Tear down method executed after each test method.
     * It stops the server and cleans up the testing environment.
     */
    @AfterEach
    public void tearDown() throws Exception {
        server.stopServer();
        Thread.sleep(1000);  // Allow time for the server to stop
    }

    /**
     * Test to verify the server is correctly instantiated.
     * Ensures that all major components are initialized and not null.
     */
    @Test
    public void testServerConstructor() {
        assertNotNull(server, "Server should be instantiated");
        assertNotNull(server.getController(), "Controller should not be null");
        assertNotNull(server.getPlayers(), "Players list should not be null");
        assertNotNull(server.getAvailableColors(), "Available colors list should not be null");
        assertNotNull(server.getModel(), "Model should not be null");
    }

    /**
     * Test to verify the server is listening on the specified TCP and RMI ports.
     */
    @Test
    public void testServerListeningOnPorts() {
        // Test TCP connection
        try (Socket socket = new Socket("localhost", tcpPort)) {
            assertTrue(socket.isConnected(), "Server should be listening on the TCP port");
        } catch (IOException e) {
            fail("Should have connected to the server on TCP port " + tcpPort);
        }

        // Test RMI connection with retry logic
        boolean rmiConnected = false;
        for (int i = 0; i < 5; i++) {
            try {
                Registry registry = LocateRegistry.getRegistry(rmiPort);
                if (registry.lookup("RMIServer") != null) {
                    rmiConnected = true;
                    break;
                }
            } catch (NotBoundException | RemoteException e) {
                try {
                    Thread.sleep(1000); // Wait before retrying
                } catch (InterruptedException ignored) {
                }
            }
        }

        assertTrue(rmiConnected, "Server should be listening on the RMI port");
    }
}
