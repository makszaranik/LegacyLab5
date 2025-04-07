package org.example.lab5;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class AudiencePanel extends JPanel {

    private final University university;
    private JTextField audienceNameField;
    private JTextField audienceCapacityField;
    private JComboBox<String> audienceTypeComboBox;
    private DefaultListModel<String> audienceListModel;
    private JList<String> audienceJList;
    private JButton sortByCapacityButton;

    public AudiencePanel(University university) {
        this.university = university;
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


        if (name.isEmpty() || capacityStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all audience fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int capacity;
        try {
            capacity = Integer.parseInt(capacityStr);
            if (capacity <= 0) {
                JOptionPane.showMessageDialog(this, "Capacity must be a positive number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Capacity must be a numeric value!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Audience.AudienceType audienceType;
        try {
            audienceType = Audience.AudienceType.valueOf(type);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Unknown audience type!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Audience audience = new Audience(name, capacity, audienceType);
        if (university.addAudience(audience)) {
            audienceListModel.addElement(audience.toString());
            audienceNameField.setText("");
            audienceCapacityField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Audience already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sortAudiences() {
        university.sortAudiencesByCapacity();
        updateAudienceList();
    }

    private void updateAudienceList() {
        audienceListModel.clear();
        for (Audience audience : university.getAudiences()) {
            audienceListModel.addElement(audience.toString());
        }
    }
}
