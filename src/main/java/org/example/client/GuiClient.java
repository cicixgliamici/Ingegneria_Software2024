package org.example.client;

import javax.swing.*;

public class GuiClient {

    public static void main(String[] args){
        JFrame jculo = new JFrame("Codex Naturalis");
        jculo.setSize(800,600);
        jculo.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JLabel label = new JLabel("Codex Naturalis");
        jculo.add(label);
        jculo.setVisible(true);
    }
}
