package org.example.servertest;

import org.example.server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.Socket;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ServerTest {

    private Server server;
    private int tcpPort = 8000;
    private int rmiPort = 1099;

    @BeforeEach
    public void setUp() throws Exception {
        server = new Server(tcpPort, rmiPort);
        server.startServer(); // Start the server which should bind the ports
    }

    @Test
    public void testServerListeningOnPorts() {
        // Check if the server is listening on the TCP port
        try (Socket socket = new Socket("localhost", tcpPort)) {
            assertTrue(socket.isConnected(), "Server should be listening on the TCP port");
        } catch (IOException e) {
            fail("Should have connected to the server on port " + tcpPort);
        }

        // Check if the server is listening on the RMI port
        try (Socket socket = new Socket("localhost", rmiPort)) {
            assertTrue(socket.isConnected(), "Server should be listening on the RMI port");
        } catch (IOException e) {
            fail("Should have connected to the server on port " + rmiPort);
        }
    }
}
