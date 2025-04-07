package org.example.lab5;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String name;
    private final int age;
    private static final List<Student> allStudents = new ArrayList<>();

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
        allStudents.add(this);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static class StudentGroup<T extends Student> {
        private final String groupName;
        private final List<T> members;

        public StudentGroup(String groupName, List<T> members) {
            this.groupName = groupName;
            this.members = new ArrayList<>(members);
        }

        public void printStudents() {
            System.out.println("Group: " + groupName);
            members.forEach(s -> System.out.println("- " + s.getName()));
        }
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public static List<Student> getAllStudents() { return new ArrayList<>(allStudents); }
}