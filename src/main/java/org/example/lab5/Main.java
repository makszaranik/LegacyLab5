package org.example.lab5;

import org.example.lab5.Annotation.ApplicationContext;
import org.example.lab5.view.DomainModelUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {

        ApplicationContext context = new ApplicationContext("org.example.lab5");

        SwingUtilities.invokeLater(() -> {
            try {
                context.getBean(DomainModelUI.class).setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
