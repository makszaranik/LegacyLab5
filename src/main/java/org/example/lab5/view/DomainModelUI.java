package org.example.lab5.view;

import org.example.lab5.controller.AudienceController;
import org.example.lab5.controller.StudentController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DomainModelUI extends JFrame {

    private AudiencePanel audiencePanel;
    private StudentPanel studentPanel;
    private SimulationPanel simulationPanel;

    public DomainModelUI() {
        super("Domain Model UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        AudienceController audienceController = new AudienceController();
        StudentController studentController = new StudentController();

        JTabbedPane tabbedPane = new JTabbedPane();

        audiencePanel = new AudiencePanel(audienceController);
        studentPanel = new StudentPanel(audienceController, studentController);
        simulationPanel = new SimulationPanel(audienceController);

        tabbedPane.addTab("Audiences", audiencePanel);
        tabbedPane.addTab("Students", studentPanel);
        tabbedPane.addTab("Simulation", simulationPanel);

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedIndex() == 1) {
                    studentPanel.updateAudienceComboBox();
                }
            }
        });

        add(tabbedPane);
    }
}
