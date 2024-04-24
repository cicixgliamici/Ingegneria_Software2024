package org.example.server;

import org.example.listener.MoveListener;
import sun.jvm.hotspot.utilities.Observer;

public interface ClientConnection {
    void closeConnection();

    void addObserver(MoveListener moveListener);

    void asyncSend(Object message);
}
