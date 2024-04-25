package org.example.server;

import org.example.listener.MoveListener;

public interface ClientConnection {
    void closeConnection();

    void addListener(MoveListener moveListener);

    void asyncSend(Object message);
}
