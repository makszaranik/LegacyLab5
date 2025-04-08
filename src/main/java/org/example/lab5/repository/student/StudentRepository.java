package org.example.lab5.repository.student;

import org.example.lab5.model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentRepository {

    void addStudent(Student student);
    Optional<Student> findStudentByName(String name);
    List<Student> findAll();
    boolean removeStudent(String name);
    boolean updateStudent(Student updatedStudent);

}
