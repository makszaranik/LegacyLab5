package org.example.lab5.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


public class Laboratory extends Audience implements Comparable<Laboratory> {

    private static final int DEFAULT_CAPACITY = 30;
    private List<String> equipment;


    public Laboratory(String name, String equipment) {
        this(name, DEFAULT_CAPACITY, equipment);
    }

    public Laboratory(String name, int capacity, String equipment) {
        super(name, capacity, AudienceType.LABORATORY);
        this.equipment = new ArrayList<>(List.of(equipment));
    }


    public List<String> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<String> equipment) {
        this.equipment = equipment;
    }

    public boolean hasEquipment(String item) {
        return equipment.contains(item);
    }

    public void addEquipment(String item) {
        if (!equipment.contains(item)) {
            equipment.add(item);
            System.out.println(item + " has been added to the laboratory.");
        } else {
            System.out.println(item + " is already present in the laboratory.");
        }
    }

    public void removeEquipment(String item) {
        if (equipment.remove(item)) {
            System.out.println(item + " has been removed from the laboratory.");
        } else {
            System.out.println(item + " was not found in the laboratory.");
        }
    }

    @Override
    public void showAudienceInfo(boolean displayCapacityAndType) {
        super.showAudienceInfo(displayCapacityAndType);
        System.out.println("Equipment: " + equipment);
    }

    @Override
    public int compareTo(Laboratory o) {
        return Integer.compare(this.getCapacity(), o.getCapacity());
    }
}