package org.example.lab5.repository.laboratory;

import org.example.lab5.model.Laboratory;
import java.util.List;
import java.util.Optional;

public interface LaboratoryRepository {
    void addLaboratory(Laboratory laboratory);
    Optional<Laboratory> findLaboratoryByName(String name);
    List<Laboratory> findAll();
    boolean removeLaboratory(String name);
    boolean updateLaboratory(Laboratory updatedLaboratory);
}
