package org.example.lab5.controller;

import org.example.lab5.Annotation.Autowired;
import org.example.lab5.Annotation.Component;
import org.example.lab5.model.Audience;
import org.example.lab5.repository.audience.AudienceRepository;
import org.example.lab5.repository.audience.AudienceRepositoryInMemory;
import org.example.lab5.validator.AudienceValidator;

import java.util.Comparator;
import java.util.List;

@Component
public class AudienceController {

    @Autowired
    private AudienceRepositoryInMemory audienceRepository;

    @Autowired
    private AudienceValidator audienceValidator;

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

    public void updateAudience(String oldName, String newName, String capacityStr, String typeStr) {
        audienceValidator.validate(newName, capacityStr, typeStr);
        int capacity = Integer.parseInt(capacityStr);
        Audience.AudienceType type = Audience.AudienceType.valueOf(typeStr);

        Audience audience = audienceRepository.findAudienceByName(oldName)
                .orElseThrow(() -> new IllegalArgumentException("Audience with name '" + oldName + "' not found."));

        audience.setName(newName);
        audience.setCapacity(capacity);
        audience.setAudienceType(type);

        audienceRepository.updateAudience(audience);
    }

}
