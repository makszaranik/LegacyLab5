package org.example.lab5.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
public class Audience implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private int capacity;
    private AudienceType audienceType;
    private List<Student> students = new ArrayList<>();

    public Audience(String name, int capacity, AudienceType audienceType) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        this.name = name;
        this.capacity = capacity;
        this.audienceType = audienceType;
    }

    public boolean addStudent(Student student) {
        if (students.size() >= capacity) {
            return false;
        }
        return students.add(student);
    }

    public boolean removeStudent(String studentName) {
        return students.removeIf(s -> s.getName().equalsIgnoreCase(studentName));
    }

    public void showAudienceInfo() {
        showAudienceInfo(false);
    }

    public void showAudienceInfo(boolean displayCapacityAndType) {
        if (displayCapacityAndType) {
            System.out.println("Audience with name: " + name
                    + " has capacity: " + capacity
                    + " and type: " + audienceType);
        } else {
            System.out.println("Audience with name: " + name + " exists");
        }
    }

    public enum AudienceType {
        LECTURE, CONFERENCE, LABORATORY
    }
}
