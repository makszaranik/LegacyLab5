package org.example.lab5.controller;

import org.example.lab5.model.Audience;
import org.example.lab5.model.Student;
import org.example.lab5.repository.audience.AudienceRepository;
import org.example.lab5.repository.audience.AudienceRepositoryInMemory;
import org.example.lab5.repository.student.StudentRepository;
import org.example.lab5.repository.student.StudentRepositoryInMemory;

import java.util.List;
import java.util.Optional;

public class StudentController {

    private AudienceRepository audienceRepository = AudienceRepositoryInMemory.getInstance();
    private StudentRepository studentRepository = StudentRepositoryInMemory.getInstance();

    public StudentController(AudienceRepository audienceRepository, StudentRepository studentRepository) {
        this.audienceRepository = audienceRepository;
        this.studentRepository = studentRepository;
    }

    public StudentController(){}

    public void addStudentToAudience(String audienceName, String studentName, int age) {
        Optional<Audience> optionalAudience = audienceRepository.findAudienceByName(audienceName);
        if (!optionalAudience.isPresent())
            throw new IllegalArgumentException("Audience not found.");
        Audience audience = optionalAudience.get();
        Student student = new Student(studentName, age);
        if (!audience.addStudent(student))
            throw new IllegalArgumentException("Cannot add student – audience is full.");
        studentRepository.addStudent(student);
    }

    public void removeStudentFromAudience(String audienceName, String studentName) {
        Optional<Audience> optionalAudience = audienceRepository.findAudienceByName(audienceName);
        if (!optionalAudience.isPresent())
            throw new IllegalArgumentException("Audience not found.");
        Audience audience = optionalAudience.get();
        if (!audience.removeStudent(studentName))
            throw new IllegalArgumentException("Student not found in audience.");
    }

    public List<Student> getStudentsByAudience(String audienceName) {
        Optional<Audience> optionalAudience = audienceRepository.findAudienceByName(audienceName);
        if (!optionalAudience.isPresent())
            throw new IllegalArgumentException("Audience not found.");
        return optionalAudience.get().getStudents();
    }

    public void updateStudent(String oldName, String newName, int newAge) {
        Optional<Student> studentOpt = studentRepository.findStudentByName(oldName);
        if (!studentOpt.isPresent()) {
            throw new IllegalArgumentException("Student with name '" + oldName + "' not found.");
        }
        Student student = studentOpt.get();
        student.setName(newName);
        student.setAge(newAge);
        // Обновляем данные в репозитории
        if (!studentRepository.updateStudent(student)) {
            throw new IllegalArgumentException("Failed to update student: " + newName);
        }
    }
}
