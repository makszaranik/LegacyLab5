package org.example.lab5.view;

import org.example.lab5.controller.AudienceController;
import org.example.lab5.controller.StudentController;
import org.example.lab5.model.Audience;
import org.example.lab5.model.Student;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class StudentPanel extends JPanel {

    private final AudienceController audienceController;
    private final StudentController studentController;
    private JComboBox<String> audienceSelectComboBox;
    private DefaultComboBoxModel<String> audienceSelectComboModel;
    private JTextField studentNameField;
    private JTextField studentAgeField;
    private DefaultListModel<Student> studentListModel;
    private JList<Student> studentJList;
    private JTextField removeStudentField;

    public StudentPanel(AudienceController audienceController, StudentController studentController) {
        this.audienceController = audienceController;
        this.studentController = studentController;
        setLayout(new BorderLayout(5, 5));
        initUI();
    }

    private void initUI() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(new TitledBorder("Student Management"));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.add(new JLabel("Select Audience:"));
        audienceSelectComboModel = new DefaultComboBoxModel<>();
        audienceSelectComboBox = new JComboBox<>(audienceSelectComboModel);
        row1.add(audienceSelectComboBox);
        inputPanel.add(row1);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(new JLabel("Student Name:"));
        studentNameField = new JTextField(20);
        row2.add(studentNameField);
        inputPanel.add(row2);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row3.add(new JLabel("Student Age:"));
        studentAgeField = new JTextField(5);
        row3.add(studentAgeField);
        inputPanel.add(row3);

        JPanel row4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addStudentButton = new JButton("Add Student");
        addStudentButton.addActionListener(e -> addStudentToAudience());
        row4.add(addStudentButton);
        JButton updateStudentButton = new JButton("Update Student");
        updateStudentButton.addActionListener(e -> updateStudent());
        row4.add(updateStudentButton);
        inputPanel.add(row4);

        JPanel row5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row5.add(new JLabel("Remove Student by Name:"));
        removeStudentField = new JTextField(15);
        row5.add(removeStudentField);
        JButton removeStudentButton = new JButton("Remove Student");
        removeStudentButton.addActionListener(e -> removeStudentFromAudience());
        row5.add(removeStudentButton);
        inputPanel.add(row5);

        add(inputPanel, BorderLayout.NORTH);

        studentListModel = new DefaultListModel<>();
        studentJList = new JList<>(studentListModel);
        studentJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        studentJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Student selected = studentJList.getSelectedValue();
                    if (selected != null) {
                        studentNameField.setText(selected.getName());
                        studentAgeField.setText(String.valueOf(selected.getAge()));
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(studentJList);
        scrollPane.setBorder(new TitledBorder("Students of Selected Audience"));
        add(scrollPane, BorderLayout.CENTER);
    }


    public void updateAudienceComboBox() {
        audienceSelectComboModel.removeAllElements();
        for (Audience a : audienceController.getAllAudiences()) {
            audienceSelectComboModel.addElement(a.getName());
        }
    }

    private void addStudentToAudience() {
        String studentName = studentNameField.getText().trim();
        String ageStr = studentAgeField.getText().trim();

        if (studentName.isEmpty() || ageStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in both student name and age!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int age;
        try {
            age = Integer.parseInt(ageStr);
            if (age <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Age must be a positive number!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Age must be a numeric value!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String selectedAudienceName = (String) audienceSelectComboBox.getSelectedItem();
        if (selectedAudienceName == null) {
            JOptionPane.showMessageDialog(this,
                    "Please create an audience first!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            studentController.addStudentToAudience(selectedAudienceName, studentName, String.valueOf(age));
            JOptionPane.showMessageDialog(this,
                    "Student " + studentName + " added!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateStudentListForSelectedAudience();
        studentNameField.setText("");
        studentAgeField.setText("");
    }

    private void updateStudent() {
        Student selected = studentJList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a student to update.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String oldName = selected.getName();
        String newName = studentNameField.getText().trim();
        String ageStr = studentAgeField.getText().trim();

        if (newName.isEmpty() || ageStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in both student name and age for update.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int newAge;
        try {
            newAge = Integer.parseInt(ageStr);
            if (newAge <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Age must be a positive number!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Age must be a numeric value!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            studentController.updateStudent(oldName, newName, String.valueOf(newAge));
            JOptionPane.showMessageDialog(this,
                    "Student updated successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateStudentListForSelectedAudience();
        studentNameField.setText("");
        studentAgeField.setText("");
    }

    private void removeStudentFromAudience() {
        String studentName = removeStudentField.getText().trim();
        if (studentName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Enter the name of the student to remove!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String selectedAudienceName = (String) audienceSelectComboBox.getSelectedItem();
        if (selectedAudienceName == null) {
            JOptionPane.showMessageDialog(this,
                    "Please create an audience first!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            studentController.removeStudentFromAudience(selectedAudienceName, studentName);
            JOptionPane.showMessageDialog(this,
                    "Student " + studentName + " removed!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateStudentListForSelectedAudience();
        removeStudentField.setText("");
    }

    private void updateStudentListForSelectedAudience() {
        studentListModel.clear();
        String selectedAudienceName = (String) audienceSelectComboBox.getSelectedItem();
        if (selectedAudienceName != null) {
            List<Student> students = studentController.getStudentsByAudience(selectedAudienceName);
            for (Student s : students) {
                studentListModel.addElement(s);
            }
        }
    }
}
