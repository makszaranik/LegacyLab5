package org.example.lab5.repository.laboratory;

import org.example.lab5.model.Laboratory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LaboratoryRepositoryInMemory implements LaboratoryRepository {

    private final List<Laboratory> laboratories = new ArrayList<>();

    @Override
    public void addLaboratory(Laboratory laboratory) {
        if (findLaboratoryByName(laboratory.getName()).isPresent()) {
            throw new IllegalArgumentException("A laboratory with this name already exists.");
        }
        laboratories.add(laboratory);
    }

    @Override
    public Optional<Laboratory> findLaboratoryByName(String name) {
        return laboratories.stream()
                .filter(l -> l.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public List<Laboratory> findAll() {
        return new ArrayList<>(laboratories);
    }

    @Override
    public boolean removeLaboratory(String name) {
        return laboratories.removeIf(l -> l.getName().equalsIgnoreCase(name));
    }

    @Override
    public boolean updateLaboratory(Laboratory updatedLaboratory) {
        Optional<Laboratory> labOpt = findLaboratoryByName(updatedLaboratory.getName());
        if (labOpt.isPresent()) {
            Laboratory laboratory = labOpt.get();
            laboratory.setCapacity(updatedLaboratory.getCapacity());
            laboratory.setAudienceType(updatedLaboratory.getAudienceType());
            laboratory.setEquipment(updatedLaboratory.getEquipment());
            return true;
        }
        return false;
    }
}
