package org.example.lab5;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class StudentPanel extends JPanel {

    private University university;
    private JComboBox<String> audienceSelectComboBox;
    private DefaultComboBoxModel<String> audienceSelectComboModel;
    private JTextField studentNameField;
    private JTextField studentAgeField;
    private JButton addStudentButton;
    private DefaultListModel<String> studentListModel;
    private JList<String> studentJList;
    private JTextField removeStudentField;
    private JButton removeStudentButton;

    public StudentPanel(University university) {
        this.university = university;
        setLayout(new BorderLayout(5, 5));
        initUI();
    }

    private void initUI() {
        // Input panel (vertical layout)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(new TitledBorder("Student Management"));

        // Row 1: Select audience
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.add(new JLabel("Select Audience:"));
        audienceSelectComboModel = new DefaultComboBoxModel<>();
        audienceSelectComboBox = new JComboBox<>(audienceSelectComboModel);
        row1.add(audienceSelectComboBox);
        inputPanel.add(row1);

        // Row 2: Student name
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(new JLabel("Student Name:"));
        studentNameField = new JTextField(20);
        row2.add(studentNameField);
        inputPanel.add(row2);

        // Row 3: Student age
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row3.add(new JLabel("Student Age:"));
        studentAgeField = new JTextField(5);
        row3.add(studentAgeField);
        inputPanel.add(row3);

        // Row 4: Add student button
        JPanel row4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addStudentButton = new JButton("Add Student");
        addStudentButton.addActionListener(e -> addStudentToAudience());
        row4.add(addStudentButton);
        inputPanel.add(row4);

        // Row 5: Remove student by name
        JPanel row5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row5.add(new JLabel("Remove Student by Name:"));
        removeStudentField = new JTextField(15);
        row5.add(removeStudentField);
        removeStudentButton = new JButton("Remove Student");
        removeStudentButton.addActionListener(e -> removeStudentFromAudience());
        row5.add(removeStudentButton);
        inputPanel.add(row5);

        add(inputPanel, BorderLayout.NORTH);

        // Student list of selected audience
        studentListModel = new DefaultListModel<>();
        studentJList = new JList<>(studentListModel);
        JScrollPane scrollPane = new JScrollPane(studentJList);
        scrollPane.setBorder(new TitledBorder("Students of Selected Audience"));
        add(scrollPane, BorderLayout.CENTER);
    }

    // Update the audience names in the combo box
    public void updateAudienceComboBox() {
        audienceSelectComboModel.removeAllElements();
        for (Audience a : university.getAudiences()) {
            audienceSelectComboModel.addElement(a.getAudienceName());
        }
    }

    private void addStudentToAudience() {
        String studentName = studentNameField.getText().trim();
        String ageStr = studentAgeField.getText().trim();

        if (studentName.isEmpty() || ageStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both student name and age!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int age;
        try {
            age = Integer.parseInt(ageStr);
            if (age <= 0) {
                JOptionPane.showMessageDialog(this, "Age must be a positive number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Age must be a numeric value!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String selectedAudienceName = (String) audienceSelectComboBox.getSelectedItem();
        if (selectedAudienceName == null) {
            JOptionPane.showMessageDialog(this, "Please create an audience first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Audience targetAudience = null;
        for (Audience a : university.getAudiences()) {
            if (a.getAudienceName().equals(selectedAudienceName)) {
                targetAudience = a;
                break;
            }
        }
        if (targetAudience == null) {
            JOptionPane.showMessageDialog(this, "Selected audience not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Student student = new Student(studentName, age);
        if (targetAudience.addStudent(student)) {
            JOptionPane.showMessageDialog(this, "Student " + studentName + " added!");
        } else {
            JOptionPane.showMessageDialog(this, "Cannot add student – audience is full!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateStudentListForSelectedAudience();
        studentNameField.setText("");
        studentAgeField.setText("");
    }

    private void removeStudentFromAudience() {
        String studentName = removeStudentField.getText().trim();
        if (studentName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter the name of the student to remove!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String selectedAudienceName = (String) audienceSelectComboBox.getSelectedItem();
        if (selectedAudienceName == null) {
            JOptionPane.showMessageDialog(this, "Please create an audience first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Audience targetAudience = null;
        for (Audience a : university.getAudiences()) {
            if (a.getAudienceName().equals(selectedAudienceName)) {
                targetAudience = a;
                break;
            }
        }
        if (targetAudience == null) {
            JOptionPane.showMessageDialog(this, "Selected audience not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (targetAudience.removeStudent(studentName)) {
            JOptionPane.showMessageDialog(this, "Student " + studentName + " removed from audience " + selectedAudienceName);
        } else {
            JOptionPane.showMessageDialog(this, "Student with name " + studentName + " not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateStudentListForSelectedAudience();
        removeStudentField.setText("");
    }

    private void updateStudentListForSelectedAudience() {
        studentListModel.clear();
        String selectedAudienceName = (String) audienceSelectComboBox.getSelectedItem();
        if (selectedAudienceName != null) {
            for (Audience a : university.getAudiences()) {
                if (a.getAudienceName().equals(selectedAudienceName)) {
                    List<Student> students = a.getStudents();
                    for (Student s : students) {
                        studentListModel.addElement(s.toString());
                    }
                    break;
                }
            }
        }
    }
}
