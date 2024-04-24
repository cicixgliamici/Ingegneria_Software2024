package org.example.listener;

public interface MoveSubject {
    public void registerListener(MoveListener listener);
    public void unregisterListener(MoveListener listener);
    public void notifyListeners(String move);
}
