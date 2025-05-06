package org.example.lab5.view;

import lombok.NoArgsConstructor;
import org.example.lab5.Annotation.Autowired;
import org.example.lab5.Annotation.Component;
import org.example.lab5.Annotation.PostConstruct;
import org.example.lab5.controller.AudienceController;
import org.example.lab5.model.Audience;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

@Component
@NoArgsConstructor
public class AudiencePanel extends JPanel {

    @Autowired
    private AudienceController audienceController;

    private JTextField audienceNameField;
    private JTextField audienceCapacityField;
    private JComboBox<String> audienceTypeComboBox;
    private DefaultListModel<Audience> audienceListModel;
    private JList<Audience> audienceJList;


    @PostConstruct
    private void initUI() {
        setLayout(new BorderLayout(5, 5));

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBorder(new TitledBorder("Manage Audience"));

        inputPanel.add(new JLabel("Audience Name:"));
        audienceNameField = new JTextField();
        inputPanel.add(audienceNameField);

        inputPanel.add(new JLabel("Capacity:"));
        audienceCapacityField = new JTextField();
        inputPanel.add(audienceCapacityField);

        inputPanel.add(new JLabel("Audience Type:"));
        audienceTypeComboBox = new JComboBox<>(
                new String[]{"LECTURE", "CONFERENCE", "LABORATORY"});
        inputPanel.add(audienceTypeComboBox);

        JButton addBtn = new JButton("Add Audience");
        addBtn.addActionListener(e -> addAudience());
        inputPanel.add(addBtn);

        JButton sortBtn = new JButton("Sort by Capacity");
        sortBtn.addActionListener(e -> sortAudiences());
        inputPanel.add(sortBtn);

        JButton updateBtn = new JButton("Update Audience");
        updateBtn.addActionListener(e -> updateAudience());
        inputPanel.add(updateBtn);

        JButton countBtn = new JButton("Number of students");
        countBtn.addActionListener(e -> showStudentCount());
        inputPanel.add(countBtn);

        add(inputPanel, BorderLayout.NORTH);

        audienceListModel = new DefaultListModel<>();
        audienceJList = new JList<>(audienceListModel);
        audienceJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        audienceJList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Audience sel = audienceJList.getSelectedValue();
                    if (sel != null) {
                        audienceNameField.setText(sel.getName());
                        audienceCapacityField.setText(
                                String.valueOf(sel.getCapacity()));
                        audienceTypeComboBox.setSelectedItem(
                                sel.getAudienceType().name());
                    }
                }
            }
        });

        JScrollPane scroll = new JScrollPane(audienceJList);
        scroll.setBorder(new TitledBorder(
                "Audience List (with Student Count)"));
        add(scroll, BorderLayout.CENTER);

        refreshList();
    }

    private void addAudience() {
        try {
            audienceController.addAudience(
                    audienceNameField.getText().trim(),
                    audienceCapacityField.getText().trim(),
                    (String) audienceTypeComboBox.getSelectedItem());
            clearInputs();
            refreshList();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sortAudiences() {
        audienceListModel.clear();
        for (Audience a : audienceController.sortAudiencesByCapacity()) {
            audienceListModel.addElement(a);
        }
    }

    private void updateAudience() {
        Audience sel = audienceJList.getSelectedValue();
        if (sel == null) {
            JOptionPane.showMessageDialog(this,
                    "Select an audience first", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            audienceController.updateAudience(
                    sel.getName(),
                    audienceNameField.getText().trim(),
                    audienceCapacityField.getText().trim(),
                    (String) audienceTypeComboBox.getSelectedItem());
            clearInputs();
            refreshList();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showStudentCount() {
        Audience sel = audienceJList.getSelectedValue();
        if (sel == null) {
            JOptionPane.showMessageDialog(this,
                    "Select an audience first", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        int count = audienceController
                .getAudienceWithMaxCapacity()
                .getStudents().size();
        JOptionPane.showMessageDialog(this,
                "Students: " + count, "Count",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshList() {
        audienceListModel.clear();
        for (Audience a : audienceController.getAllAudiences()) {
            audienceListModel.addElement(a);
        }
    }

    private void clearInputs() {
        audienceNameField.setText("");
        audienceCapacityField.setText("");
    }
}
