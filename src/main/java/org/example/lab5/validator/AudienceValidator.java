package org.example.lab5.validator;

import org.example.lab5.Annotation.Component;
import org.example.lab5.model.Audience;


@Component
public class AudienceValidator {

    private static AudienceValidator instance;

    private AudienceValidator() { }

    public static AudienceValidator getInstance() {
        if (instance == null) {
            instance = new AudienceValidator();
        }
        return instance;
    }

    public void validate(String name, String capacityStr, String typeStr) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Audience name cannot be empty.");
        }
        int capacity;
        try {
            capacity = Integer.parseInt(capacityStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Capacity must be a numeric value.");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        try {
            Audience.AudienceType.valueOf(typeStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid audience type.");
        }
    }
}
