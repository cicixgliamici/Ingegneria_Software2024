package org.example.server;
public interface ModelChangeListener {
    void onModelChange(String username, String specificMessage, String generalMessage);
    void onModelSpecific(String username, String specificMessage);
}

