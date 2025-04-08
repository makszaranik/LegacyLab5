package org.example.lab5.repository.audience;

import org.example.lab5.model.Audience;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AudienceRepositoryInMemory implements AudienceRepository {

    private static AudienceRepositoryInMemory instance;
    private final List<Audience> audiences = new ArrayList<>();

    private AudienceRepositoryInMemory() {}

    public static AudienceRepositoryInMemory getInstance() {
        if (instance == null) {
            instance = new AudienceRepositoryInMemory();
        }
        return instance;
    }

    @Override
    public void addAudience(Audience audience) {
        if (findAudienceByName(audience.getName()).isPresent()) {
            throw new IllegalArgumentException("An audience with this name already exists.");
        }
        audiences.add(audience);
    }

    @Override
    public Optional<Audience> findAudienceByName(String name) {
        return audiences.stream()
                .filter(a -> a.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public List<Audience> findAll() {
        return new ArrayList<>(audiences);
    }

    @Override
    public boolean removeAudience(String name) {
        return audiences.removeIf(a -> a.getName().equalsIgnoreCase(name));
    }

    @Override
    public void updateAudience(Audience updatedAudience) {
        Optional<Audience> audienceOpt = findAudienceByName(updatedAudience.getName());
        if (audienceOpt.isPresent()) {
            Audience audience = audienceOpt.get();
            audience.setCapacity(updatedAudience.getCapacity());
            audience.setAudienceType(updatedAudience.getAudienceType());
        }
    }

    @Override
    public int countStudentsInAudience(String name) {
        Optional<Audience> audienceOpt = findAudienceByName(name);
        if (audienceOpt.isPresent()) {
            return audienceOpt.get().getStudents().size();
        }
        throw new IllegalArgumentException("Audience not found: " + name);
    }
}
