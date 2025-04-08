package org.example.lab5;

import org.example.lab5.view.DomainModelUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DomainModelUI().setVisible(true));
    }
}