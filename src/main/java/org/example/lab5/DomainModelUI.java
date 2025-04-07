package org.example.lab5;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DomainModelUI extends JFrame {

    private University university;
    private AudiencePanel audiencePanel;
    private StudentPanel studentPanel;
    private SimulationPanel simulationPanel;

    public DomainModelUI() {
        super("Domain Model UI");
        university = new University();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        audiencePanel = new AudiencePanel(university);
        studentPanel = new StudentPanel(university);
        simulationPanel = new SimulationPanel(university);

        tabbedPane.addTab("Audiences", audiencePanel);
        tabbedPane.addTab("Students", studentPanel);
        tabbedPane.addTab("Simulation", simulationPanel);


        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();

                if (selectedIndex == 1) {
                    studentPanel.updateAudienceComboBox();
                }
            }
        });

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DomainModelUI().setVisible(true));
    }
}
