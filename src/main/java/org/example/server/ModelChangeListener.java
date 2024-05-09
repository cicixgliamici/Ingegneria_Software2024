package org.example.server;
public interface ModelChangeListener {
    /**  Interface for the communication between Server and client
     *   1 - send a specific message to a client and a general one to the others
     *   2 - send only a specific message to a client
     *   3 - send a broadcast
     */
    void onModelChange(String username, String specificMessage, String generalMessage);
    void onModelSpecific(String username, String specificMessage);
    void onModelGeneric(String generalMessage);
}

