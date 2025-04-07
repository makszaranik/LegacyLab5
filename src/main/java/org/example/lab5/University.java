package org.example.lab5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import java.util.List;

public class University {
    private List<Audience> audiences;

    public University() {
        audiences = new ArrayList<>();
    }

    public boolean addAudience(Audience audience) {
        for (Audience existingAudience : audiences) {
            if (existingAudience.getName().equals(audience.getName())) {
                return false;
            }
        }
        audiences.add(audience);
        return true;
    }

    public boolean removeAudience(Audience audience) {
        return audiences.remove(audience);
    }

    public List<Audience> getAudiences() {
        return audiences;
    }

    public void sortAudiencesByCapacity() {
        audiences.sort(Comparator.comparingInt(Audience::getCapacity));
    }

    public Audience getAudienceWithMaxCapacity() {
        if (audiences.isEmpty()) {
            return null;
        }

        Audience maxCapacityAudience = audiences.get(0);
        for (Audience audience : audiences) {
            if (audience.getCapacity() > maxCapacityAudience.getCapacity()) {
                maxCapacityAudience = audience;
            }
        }
        return maxCapacityAudience;
    }

    public boolean removeAudienceByName(String name) {
        for (Audience audience : audiences) {
            if (audience.getName().equals(name)) {
                audiences.remove(audience);
                return true;
            }
        }
        return false;
    }
}
