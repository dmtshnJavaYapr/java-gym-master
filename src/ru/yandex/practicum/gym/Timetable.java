package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay ,ArrayList<TrainingSession>>> timetable = new HashMap<>();


    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        if (timetable.containsKey(dayOfWeek)) {
            TreeMap<TimeOfDay ,ArrayList<TrainingSession>> currentMap = timetable.get(dayOfWeek);
            if (currentMap.containsKey(timeOfDay))
                currentMap.get(timeOfDay).add(trainingSession);

            else
                currentMap.put(timeOfDay, new ArrayList<>(List.of(trainingSession)));
        }

        else
            putInTimetableIfDayOfWeekIsNull(trainingSession, timeOfDay, dayOfWeek);


    }

    private void putInTimetableIfDayOfWeekIsNull(TrainingSession trainingSession,
                                                 TimeOfDay timeOfDay, DayOfWeek dayOfWeek){
        ArrayList<TrainingSession> sessions = new ArrayList<>(List.of(trainingSession));
        TreeMap<TimeOfDay ,ArrayList<TrainingSession>> currentMap = new TreeMap<>();
        // Насчет конструктора currentMap запутался, можно ли передать лист и время дня сразу в конструктор?
        // Пытался сделать все в одной строке, не вышло(
        // да и насколько такая запись могла быть уместной и понятной?
        currentMap.put(timeOfDay, sessions);
        timetable.put(dayOfWeek, currentMap);
    }

    public TreeMap<TimeOfDay ,ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (timetable.containsKey(dayOfWeek)) {
            return timetable.get(dayOfWeek);
        }
        return null;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (timetable.containsKey(dayOfWeek)) {
            TreeMap<TimeOfDay ,ArrayList<TrainingSession>> currentMap = timetable.get(dayOfWeek);
            return currentMap.get(timeOfDay);
        }

        return null;
    }

    public Map<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> getTimetable() {
        return timetable;
    }

    /* Объясню контекст метода, мне очень понравилась задача, и я вошел в азарт
    из спортивного интереса решить метод getCountByCoaches без компараторов
    хотел получить список тренеров, заполнить под каждый индекс тренеров список счетчиков
    далее синхронно через циклы отсортировать оба списка и уже последовательно
    закинуть уже в LinkedHashMap
    Но с этим провозился часа 3, устал, и все что хотел, закончить побыстрее простым способом))
     */

    public TreeMap<Coach, Integer> getCountByCoaches(){
        ArrayList<Coach> coaches = getAllCoaches();
        Set<Coach> uniqueCoaches = new TreeSet<>(coaches);
        Map<Coach, Integer> coachesByCount = new HashMap<>();

        for(Coach currentCoach : uniqueCoaches){
            int coachSum = 0;

            for (Coach coach : coaches){
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

    private ArrayList<Coach> getAllCoaches(){
        ArrayList<Coach> coaches = new ArrayList<>();

        for (DayOfWeek dayOfWeek : timetable.keySet()) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> currentMap = timetable.get(dayOfWeek);

            for (ArrayList<TrainingSession> trainingSessions : currentMap.values()) {
                for (TrainingSession session : trainingSessions) {
                    coaches.add(session.getCoach());
                }
            }
        }

        return coaches;
    }
}

// коммент чисто чтобы сделать pull