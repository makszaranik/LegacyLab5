package org.example.lab5.controller;

import org.example.lab5.model.Audience;
import org.example.lab5.repository.audience.AudienceRepository;
import org.example.lab5.repository.audience.AudienceRepositoryInMemory;
import org.example.lab5.validator.AudienceValidator;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AudienceController {
    private AudienceRepository audienceRepository = AudienceRepositoryInMemory.getInstance();
    private AudienceValidator audienceValidator = AudienceValidator.getInstance();

    public AudienceController(AudienceRepository audienceRepository, AudienceValidator audienceValidator) {
        this.audienceRepository = audienceRepository;
        this.audienceValidator = audienceValidator;
    }

    public AudienceController() {}



    public void addAudience(String name, String capacityStr, String typeStr) {
        audienceValidator.validate(name, capacityStr, typeStr);
        int capacity = Integer.parseInt(capacityStr);
        Audience.AudienceType type = Audience.AudienceType.valueOf(typeStr);
        Audience audience = new Audience(name, capacity, type);
        audienceRepository.addAudience(audience);
    }

    public List<Audience> getAllAudiences() {
        return audienceRepository.findAll();
    }

    public List<Audience> sortAudiencesByCapacity() {
        List<Audience> audiences = audienceRepository.findAll();
        audiences.sort((a1, a2) -> Integer.compare(a1.getCapacity(), a2.getCapacity()));
        return audiences;
    }

    public Audience getAudienceWithMaxCapacity() {
        return audienceRepository.findAll()
                .stream()
                .max(Comparator.comparing(Audience::getCapacity))
                .orElse(null);
    }
}
