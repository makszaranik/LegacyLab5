package org.example.lab5.model;

import lombok.Data;

import java.util.List;

@Data
public class Student {

    private String name;
    private int age;


    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static class StudentGroup<T extends Student> {
        private final String groupName;
        private final List<T> members;
        private Audience assignedAudience;

        public StudentGroup(String groupName, List<T> members) {
            this.groupName = groupName;
            this.members = members;
        }

        public void printStudents() {
            class LocalStudentPrinter {
                void print() {
                    System.out.println("Students in group " + groupName + ":");
                    for (T member : members) {
                        System.out.println(member.getName() + " (age: " + member.getAge() + ")");
                    }
                }
            }
            LocalStudentPrinter printer = new LocalStudentPrinter();
            printer.print();
        }
        public List<T> getStudents() {
            return members;
        }
    }
}
