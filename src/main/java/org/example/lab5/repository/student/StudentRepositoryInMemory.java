package org.example.lab5.repository.student;

import org.example.lab5.model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepositoryInMemory implements StudentRepository {

    private static StudentRepositoryInMemory instance;

    private final List<Student> students = new ArrayList<>();

    private StudentRepositoryInMemory() {}

    public static StudentRepositoryInMemory getInstance() {
        if (instance == null) {
            instance = new StudentRepositoryInMemory();
        }
        return instance;
    }

    @Override
    public void addStudent(Student student) {
        if (findStudentByName(student.getName()).isPresent()) {
            throw new IllegalArgumentException("A student with this name already exists.");
        }
        students.add(student);
    }

    @Override
    public Optional<Student> findStudentByName(String name) {
        return students.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    @Override
    public boolean removeStudent(String name) {
        return students.removeIf(s -> s.getName().equalsIgnoreCase(name));
    }

    @Override
    public boolean updateStudent(Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getName().equalsIgnoreCase(updatedStudent.getName())) {
                students.set(i, updatedStudent);
                return true;
            }
        }
        return false;
    }
}
