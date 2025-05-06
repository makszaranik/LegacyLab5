package org.example.lab5.validator;


import org.example.lab5.Annotation.Component;

@Component
public class StudentValidator {

    private static StudentValidator instance;

    private StudentValidator() {}

    public static StudentValidator getInstance() {
        if (instance == null) {
            instance = new StudentValidator();
        }
        return instance;
    }

    public void validate(String name, String ageStr) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty.");
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Age must be a valid number.");
        }

        if (age <= 0) {
            throw new IllegalArgumentException("Age must be greater than zero.");
        }

        if (!name.matches("[A-Za-z'\\- ]+")) {
            throw new IllegalArgumentException("Name contains invalid characters.");
        }
    }
}
