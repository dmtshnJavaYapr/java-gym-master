package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        if (timetable.containsKey(dayOfWeek)) {
            Map<TimeOfDay, List<TrainingSession>> currentMap = timetable.get(dayOfWeek);
            if (currentMap.containsKey(timeOfDay))
                currentMap.get(timeOfDay).add(trainingSession);

            else
                currentMap.put(timeOfDay, new ArrayList<>(List.of(trainingSession)));
        } else
            putInTimetableIfDayOfWeekIsNull(trainingSession, timeOfDay, dayOfWeek);
    }

    private void putInTimetableIfDayOfWeekIsNull(TrainingSession trainingSession,
                                                 TimeOfDay timeOfDay, DayOfWeek dayOfWeek) {
        List<TrainingSession> sessions = new ArrayList<>(List.of(trainingSession));
        Map<TimeOfDay, List<TrainingSession>> currentMap = new TreeMap<>();
        currentMap.put(timeOfDay, sessions);
        timetable.put(dayOfWeek, currentMap);
    }

    public Map<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (timetable.containsKey(dayOfWeek)) {
            return timetable.get(dayOfWeek);
        }
        return Map.of();
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (timetable.containsKey(dayOfWeek)) {
            Map<TimeOfDay, List<TrainingSession>> currentMap = timetable.get(dayOfWeek);
            return currentMap.get(timeOfDay);
        }

        return List.of();
    }

    public Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> getTimetable() {
        return timetable;
    }

    public TreeMap<Coach, Integer> getCountByCoaches() {
        ArrayList<Coach> coaches = getAllCoaches();
        Set<Coach> uniqueCoaches = new TreeSet<>(coaches);
        Map<Coach, Integer> coachesByCount = new HashMap<>();

        for (Coach currentCoach : uniqueCoaches) {
            int coachSum = 0;

            for (Coach coach : coaches) {
                if (currentCoach.equals(coach))
                    coachSum++;
            }
            coachesByCount.put(currentCoach, coachSum);
        }

        Comparator<Coach> comparator = (coach1, coach2) -> {
            int countComparison = coachesByCount.get(coach2)
                    .compareTo(coachesByCount.get(coach1));

            if (countComparison != 0) {
                return countComparison;
            }

            return coach2.compareTo(coach1);
        };

        TreeMap<Coach, Integer> sortedMap = new TreeMap<>(comparator);
        sortedMap.putAll(coachesByCount);

        return sortedMap;
    }

    private ArrayList<Coach> getAllCoaches() {
        ArrayList<Coach> coaches = new ArrayList<>();

        for (DayOfWeek dayOfWeek : timetable.keySet()) {
            Map<TimeOfDay, List<TrainingSession>> currentMap = timetable.get(dayOfWeek);

            for (List<TrainingSession> trainingSessions : currentMap.values()) {
                for (TrainingSession session : trainingSessions) {
                    coaches.add(session.getCoach());
                }
            }
        }

        return coaches;
    }
}