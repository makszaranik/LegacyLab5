package org.example.lab5.repository.student;

import org.example.lab5.Annotation.Component;
import org.example.lab5.Annotation.Persistent;
import org.example.lab5.model.Student;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Persistent
public class StudentRepositoryInMemory implements StudentRepository, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final List<Student> students = new ArrayList<>();

    public StudentRepositoryInMemory() {}

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
