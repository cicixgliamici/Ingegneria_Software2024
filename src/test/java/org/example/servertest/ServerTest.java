package org.example.servertest;

import org.example.server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ServerTest {

    private Server server;
    private final int tcpPort = 8000;
    private final int rmiPort = 1099;

    @BeforeEach
    public void setUp() throws Exception {
        server = new Server(tcpPort, rmiPort);
        // Start the server in a separate thread to prevent blocking the test runner
        new Thread(() -> server.startServer()).start();
        // Allow some time for the server to start
        Thread.sleep(1000);  // Adjust the time based on your server startup time
    }

    @Test
    public void testServerListeningOnPorts() {
        // Test TCP connection
        try (Socket socket = new Socket("localhost", tcpPort)) {
            assertTrue(socket.isConnected(), "Server should be listening on the TCP port");
        } catch (IOException e) {
            fail("Should have connected to the server on TCP port " + tcpPort);
        }

        // Test RMI connection
        try (Socket socket = new Socket("localhost", rmiPort)) {
            assertTrue(socket.isConnected(), "Server should be listening on the RMI port");
        } catch (IOException e) {
            fail("Should have connected to the server on RMI port " + rmiPort);
        }
    }
}
