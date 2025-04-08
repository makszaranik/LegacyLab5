package org.example.lab5.view;

import org.example.lab5.controller.AudienceController;
import org.example.lab5.model.Audience;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AudiencePanel extends JPanel {

    private final AudienceController audienceController;
    private JTextField audienceNameField;
    private JTextField audienceCapacityField;
    private JComboBox<String> audienceTypeComboBox;

    private DefaultListModel<Audience> audienceListModel;
    private JList<Audience> audienceJList;

    public AudiencePanel(AudienceController audienceController) {
        this.audienceController = audienceController;
        setLayout(new BorderLayout(5, 5));
        initUI();
        updateAudienceList();
    }

    private void initUI() {
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBorder(new TitledBorder("Manage Audience"));

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

        JButton sortByCapacityButton = new JButton("Sort by Capacity");
        sortByCapacityButton.addActionListener(e -> sortAudiences());
        inputPanel.add(sortByCapacityButton);

        JButton updateAudienceButton = new JButton("Update Audience");
        updateAudienceButton.addActionListener(e -> updateAudience());
        inputPanel.add(updateAudienceButton);

        JButton numberOfStudentsInAudienceButton = new JButton("Number of students");
        numberOfStudentsInAudienceButton.addActionListener(e -> getNumberOfStudents());
        inputPanel.add(numberOfStudentsInAudienceButton);

        add(inputPanel, BorderLayout.NORTH);

        audienceListModel = new DefaultListModel<>();
        audienceJList = new JList<>(audienceListModel);
        audienceJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        audienceJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Audience selected = audienceJList.getSelectedValue();
                    if (selected != null) {
                        audienceNameField.setText(selected.getName());
                        audienceCapacityField.setText(String.valueOf(selected.getCapacity()));
                        audienceTypeComboBox.setSelectedItem(selected.getAudienceType().name());
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(audienceJList);
        scrollPane.setBorder(new TitledBorder("Audience List (with Student Count)"));
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

    private void updateAudience() {
        Audience selected = audienceJList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select an audience to update.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String oldName = selected.getName();
        String newName = audienceNameField.getText().trim();
        String capacityStr = audienceCapacityField.getText().trim();
        String type = (String) audienceTypeComboBox.getSelectedItem();
        try {
            audienceController.updateAudience(oldName, newName, capacityStr, type);
            updateAudienceList();
            audienceNameField.setText("");
            audienceCapacityField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getNumberOfStudents() {
        Audience selected = audienceJList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select an audience to view its student count.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int count = selected.getStudents().size();
        JOptionPane.showMessageDialog(this,
                "The audience '" + selected.getName() + "' has " + count + " student(s).",
                "Student Count", JOptionPane.INFORMATION_MESSAGE);
    }

    private void sortAudiences() {
        audienceListModel.clear();
        for (Audience audience : audienceController.sortAudiencesByCapacity()) {
            audienceListModel.addElement(audience);
        }
    }

    private void updateAudienceList() {
        audienceListModel.clear();
        for (Audience audience : audienceController.getAllAudiences()) {
            audienceListModel.addElement(audience);
        }
    }
}
