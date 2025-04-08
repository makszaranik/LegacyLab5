package org.example.lab5.model;

import org.example.lab5.repository.audience.AudienceRepositoryInMemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule {

    private static final Map<DayOfWeek, Map<LessonNumber, Audience>> SCHEDULE = new HashMap<>();

    private final static AudienceRepositoryInMemory audienceRepositoryInMemory
            = AudienceRepositoryInMemory.getInstance();

    private Schedule() {
        for (DayOfWeek day : DayOfWeek.values()) {
            SCHEDULE.put(day, new HashMap<>());
        }
    }

    public static List<Audience> findFreeAudiences(DayOfWeek day, LessonNumber pairNumber) {
        Map<LessonNumber, Audience> scheduleByDay = SCHEDULE.get(day);
        List<Audience> allAudiences = audienceRepositoryInMemory.findAll();

        if (scheduleByDay == null) {
            return allAudiences;
        }

        List<Audience> freeAudiences = new ArrayList<>();
        for(Audience audience : allAudiences) {
            if((scheduleByDay.get(pairNumber) == null)){
                return allAudiences;
            }
            if(!scheduleByDay.get(pairNumber).equals(audience)) {
                freeAudiences.add(audience);
            }
        }
        return freeAudiences;
    }

    public static void reserveAudience(DayOfWeek day, LessonNumber pairNumber, Audience audience) {
        Map<LessonNumber, Audience> scheduleByDay = SCHEDULE
                .computeIfAbsent(day, k -> new HashMap<>());

        if(scheduleByDay.containsKey(pairNumber)) {
            throw new IllegalArgumentException("The pair number " + pairNumber + " is already reserved.");
        }
        scheduleByDay.put(pairNumber, audience);
    }


    public enum DayOfWeek {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }

    public enum LessonNumber {
        FIRST,
        SECOND,
        THIRD,
        FOURTH,
        FIFTH
    }

}

