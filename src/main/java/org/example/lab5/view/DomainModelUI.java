package org.example.lab5.view;

import org.example.lab5.controller.AudienceController;
import org.example.lab5.controller.StudentController;

import javax.swing.*;

public class DomainModelUI extends JFrame {

    public DomainModelUI() {
        super("Domain Model UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        AudienceController audienceController = new AudienceController();
        StudentController  studentController  = new StudentController();

        AudiencePanel      audiencePanel = new AudiencePanel(audienceController);
        StudentPanel       studentPanel  = new StudentPanel(audienceController, studentController);
        SimulationPanel    simulation    = new SimulationPanel(audienceController);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Audiences",   audiencePanel);
        tabs.addTab("Students",    studentPanel);
        tabs.addTab("Visualization", simulation);

        tabs.addChangeListener(e -> {
            if (tabs.getSelectedComponent() == studentPanel) {
                studentPanel.updateAudienceComboBox();
            }
        });

        add(tabs);
    }
}
