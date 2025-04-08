package org.example.lab5.view;

import org.example.lab5.controller.AudienceController;
import org.example.lab5.model.Audience;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SimulationPanel extends JPanel {

    private AudienceController audienceController;
    private JTextArea simulationTextArea;
    private JButton startSimulationButton;
    private Timer simulationTimer;
    private int simulationStep = 0;

    public SimulationPanel(AudienceController audienceController) {
        this.audienceController = audienceController;
        setLayout(new BorderLayout(5, 5));
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        simulationTextArea = new JTextArea(10, 40);
        simulationTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(simulationTextArea);
        scrollPane.setBorder(new TitledBorder("Simulation"));
        mainPanel.add(scrollPane);

        startSimulationButton = new JButton("Start Simulation");
        startSimulationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startSimulationButton.addActionListener(e -> startSimulation());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(startSimulationButton);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void startSimulation() {
        if (audienceController.getAllAudiences().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Create at least one audience to run the simulation!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        startSimulationButton.setEnabled(false);
        simulationStep = 0;
        simulationTextArea.append("Simulation started...\n");

        simulationTimer = new Timer(2000, e -> updateSimulation());
        simulationTimer.start();
    }

    private void updateSimulation() {
        simulationStep++;
        Audience maxCapacityAudience = audienceController.getAudienceWithMaxCapacity();
        if (maxCapacityAudience != null) {
            simulationTextArea.append("Step " + simulationStep + ": Audience with the highest capacity - "
                    + maxCapacityAudience.getName() + "\n");
        }
        if (simulationStep >= 10) {
            simulationTimer.stop();
            simulationTextArea.append("Simulation completed.\n");
            startSimulationButton.setEnabled(true);
        }
    }
}
