package org.example.lab5.view;

import org.example.lab5.Annotation.Autowired;
import org.example.lab5.Annotation.Component;
import org.example.lab5.Annotation.PostConstruct;

import javax.swing.*;

@Component
public class DomainModelUI extends JFrame {

    @Autowired private AudiencePanel   audiencePanel;
    @Autowired private StudentPanel    studentPanel;
    @Autowired private SimulationPanel simulationPanel;

    public DomainModelUI() {
        super("Domain Model UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
    }

    @PostConstruct
    private void initUI() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Audiences",      audiencePanel);
        tabs.addTab("Students",       studentPanel);
        tabs.addTab("Visualization",  simulationPanel);

        tabs.addChangeListener(e -> {
            if (tabs.getSelectedComponent() == studentPanel) {
                studentPanel.updateAudienceComboBox();
            }
        });

        add(tabs);
    }
}

