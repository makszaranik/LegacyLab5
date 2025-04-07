package org.example.lab5;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Audience {
    private static final int DEFAULT_CAPACITY = 50;
    private static Set<Audience> AUDIENCES = new HashSet<>();

    private final String name;
    private int capacity;
    private AudienceType audienceType;
    private List<Student> students = new ArrayList<>();

    public Audience() {
        this("Audience-" + UUID.randomUUID(), DEFAULT_CAPACITY);
    }

    public Audience(String name, int capacity) {
        this(name, capacity, AudienceType.LECTURE);
    }

    public Audience(String name, AudienceType audienceType) {
        this(name, DEFAULT_CAPACITY, audienceType);
    }

    public Audience(String name, int capacity, AudienceType audienceType) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        this.name = name;
        this.capacity = capacity;
        this.audienceType = audienceType;
        AUDIENCES.add(this);
    }

    public void showAudienceInfo(boolean displayCapacityAndType) {
        if (displayCapacityAndType) {
            System.out.println("Audience with name: " + name + " has capacity: " + capacity + " and type: " + audienceType);
        } else {
            System.out.println("Audience with name: " + name + " exists");
        }
    }

    public static int getTotalAudiences() {
        return AUDIENCES.size();
    }

    public static int getTotalCapacity() {
        return AUDIENCES.stream().mapToInt(Audience::getCapacity).sum();
    }

    public String getName() {
        return name;
    }

    public String getAudienceName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public static List<Audience> getAllAudiences() {
        return new ArrayList<>(AUDIENCES);
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        this.capacity = capacity;
    }

    public static void removeAudience(String name) {
        AUDIENCES.removeIf(audience -> audience.getAudienceName().equals(name));
    }

    public boolean addStudent(Student student) {
        if (students.size() < capacity) {
            students.add(student);
            return true;
        }
        return false;
    }

    public boolean removeStudent(String studentName) {
        return students.removeIf(s -> s.getName().equalsIgnoreCase(studentName));
    }

    public List<Student> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "Audience{" +
                "name='" + name + '\'' +
                ", capacity=" + capacity +
                ", audienceType=" + audienceType +
                ", students=" + students +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Audience audience = (Audience) o;
        return capacity == audience.capacity && Objects.equals(name, audience.name)
                && audienceType == audience.audienceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capacity, audienceType);
    }

    public enum AudienceType {
        LECTURE, CONFERENCE, LABORATORY
    }
}
