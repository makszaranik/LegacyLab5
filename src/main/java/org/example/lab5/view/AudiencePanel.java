package org.example.lab5.view;

import org.example.lab5.controller.AudienceController;
import org.example.lab5.model.Audience;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class AudiencePanel extends JPanel {

    private final AudienceController audienceController;
    private JTextField audienceNameField;
    private JTextField audienceCapacityField;
    private JComboBox<String> audienceTypeComboBox;
    private DefaultListModel<String> audienceListModel;
    private JList<String> audienceJList;
    private JButton sortByCapacityButton;

    public AudiencePanel(AudienceController audienceController) {
        this.audienceController = audienceController;
        setLayout(new BorderLayout(5, 5));
        initUI();
        updateAudienceList();
    }

    private void initUI() {
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(new TitledBorder("Create Audience"));

        inputPanel.add(new JLabel("Audience Name:"));
        audienceNameField = new JTextField();
        inputPanel.add(audienceNameField);

        inputPanel.add(new JLabel("Capacity:"));
        audienceCapacityField = new JTextField();
        inputPanel.add(audienceCapacityField);

        inputPanel.add(new JLabel("Audience Type:"));
        audienceTypeComboBox = new JComboBox<>(new String[]{"LECTURE", "CONFERENCE", "LABORATORY"});
        inputPanel.add(audienceTypeComboBox);

        JButton addAudienceButton = new JButton("Add Audience");
        addAudienceButton.addActionListener(e -> addAudience());
        inputPanel.add(addAudienceButton);

        sortByCapacityButton = new JButton("Sort by Capacity");
        sortByCapacityButton.addActionListener(e -> sortAudiences());
        inputPanel.add(sortByCapacityButton);

        add(inputPanel, BorderLayout.NORTH);

        audienceListModel = new DefaultListModel<>();
        audienceJList = new JList<>(audienceListModel);
        JScrollPane scrollPane = new JScrollPane(audienceJList);
        scrollPane.setBorder(new TitledBorder("Audience List"));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addAudience() {
        String name = audienceNameField.getText().trim();
        String capacityStr = audienceCapacityField.getText().trim();
        String type = (String) audienceTypeComboBox.getSelectedItem();
        try {
            audienceController.addAudience(name, capacityStr, type);
            updateAudienceList();
            audienceNameField.setText("");
            audienceCapacityField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sortAudiences() {
        audienceListModel.clear();
        for (Audience audience : audienceController.sortAudiencesByCapacity()) {
            audienceListModel.addElement(audience.toString());
        }
    }

    private void updateAudienceList() {
        audienceListModel.clear();
        for (Audience audience : audienceController.getAllAudiences()) {
            audienceListModel.addElement(audience.toString());
        }
    }
}
