package org.example.lab5.repository.audience;

import org.example.lab5.model.Audience;

import java.util.List;
import java.util.Optional;

public interface AudienceRepository {

    void addAudience(Audience audience);
    Optional<Audience> findAudienceByName(String audienceName);
    List<Audience> findAll();
    boolean removeAudience(String name);
    void updateAudience(Audience updatedAudience);
    int countStudentsInAudience(String name);

}
