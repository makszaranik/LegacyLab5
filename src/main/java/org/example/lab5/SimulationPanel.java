package org.example.lab5;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SimulationPanel extends JPanel {

    private University university;
    private JTextArea simulationTextArea;
    private JButton startSimulationButton;
    private Timer simulationTimer;
    private int simulationStep = 0;

    public SimulationPanel(University university) {
        this.university = university;
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
        if (university.getAudiences().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Create at least one audience to run the simulation!", "Error", JOptionPane.ERROR_MESSAGE);
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
        Audience maxCapacityAudience = university.getAudienceWithMaxCapacity();
        if (maxCapacityAudience != null) {
            simulationTextArea.append("Step " + simulationStep + ": Audience with the highest capacity - "
                    + maxCapacityAudience.getAudienceName() + "\n");
        }
        if (simulationStep >= 10) {
            simulationTimer.stop();
            simulationTextArea.append("Simulation completed.\n");
            startSimulationButton.setEnabled(true);
        }
    }
}
