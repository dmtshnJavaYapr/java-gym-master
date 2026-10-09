package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    public void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        DayOfWeek dayOfWeek = singleTrainingSession.getDayOfWeek();
        Assertions.assertEquals(1, timetable.getTimetable().get(dayOfWeek).size());
        Assertions.assertNull(timetable.getTimetable().get(DayOfWeek.TUESDAY));
        //Проверить, что за понедельник вернулось одно занятие
        //Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Assertions.assertEquals(1, timetable.getTimetable().get(DayOfWeek.MONDAY).size());
        Assertions.assertEquals(2, timetable.getTimetable().get(DayOfWeek.THURSDAY).size());
        Assertions.assertNull(timetable.getTimetable().get(DayOfWeek.TUESDAY));

        // Проверить, что за понедельник вернулось одно занятие
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.getTrainingSessionsForDayAndTime(DayOfWeek.TUESDAY, new TimeOfDay(14, 0));

        Assertions.assertEquals(1, timetable.getTimetable().get(DayOfWeek.MONDAY)
                .get(new TimeOfDay(13, 0)).size());
        Assertions.assertNull(timetable.getTimetable().get(DayOfWeek.TUESDAY));

        /*Здесь очень запарился, не понимал, почему размер второго кидает, что тест не прошел
        прошелся по всей логической цепочки и дошло, что нельзя посмотреть размер листа, которого
        физически не существует))
        */

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        //Проверить, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    void testShouldGetTwoSessionsThatCreatedByOneTimeAndЩnoDay() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Бокс для взросых", Age.ADULT, 90);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession firstTrainingSession = new TrainingSession(group1, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        TrainingSession secondTrainingSession = new TrainingSession(group2, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);

        Assertions.assertEquals(2, timetable.getTimetable().get(DayOfWeek.MONDAY)
                .get(new TimeOfDay(13, 0)).size());
    }

    @Test
    void testSholdGetNoSessionsThatCreatedByMondayAndAt130AndGotForFriday() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Бокс для взросых", Age.ADULT, 90);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession firstTrainingSession = new TrainingSession(group1, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        TrainingSession secondTrainingSession = new TrainingSession(group2, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);

        timetable.getTrainingSessionsForDayAndTime(DayOfWeek.FRIDAY, new TimeOfDay(13, 0));
        Assertions.assertNull(timetable.getTimetable().get(DayOfWeek.FRIDAY));
    }


    @Test
    void shouldGetOneCoachForOneTrainingSessionCreated() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession firstTrainingSession = new TrainingSession(group1, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(firstTrainingSession);

        TreeMap<Coach, Integer> currentMap = timetable.getCountByCoaches();
        Assertions.assertEquals(1, currentMap.get(coach));
    }

    @Test
    void shouldGetFirstCoachWithTwoSessionsThenSecondCoachWithOneSession(){
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Бокс для взросых", Age.ADULT, 90);
        Coach coach1 = new Coach("Пердольерро", "Федор", "Николаевич");
        Coach coach2 = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession firstTrainingSession = new TrainingSession(group1, coach1,
                DayOfWeek.FRIDAY, new TimeOfDay(13, 0));

        TrainingSession secondTrainingSession = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(19, 0));

        TrainingSession thirdTrainingSession = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(20, 30));

        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);
        timetable.addNewTrainingSession(thirdTrainingSession);

        TreeMap<Coach, Integer> currentMap = timetable.getCountByCoaches();
        Integer counter = 2;

        for (Integer count : currentMap.values()){
            Assertions.assertEquals(counter, count);
            counter--;
        }


    }

    @Test
    void shouldGetThreeCoachesAfterUseGetCountByCoaches(){
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Бокс для взросых", Age.ADULT, 90);
        Coach coach1 = new Coach("Пердольерро", "Федор", "Николаевич");
        Coach coach2 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach3 = new Coach("Гончаренко", "Андрей", "Сергеевич");
        TrainingSession firstTrainingSession = new TrainingSession(group1, coach1,
                DayOfWeek.FRIDAY, new TimeOfDay(13, 0));

        TrainingSession secondTrainingSession = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(19, 0));

        TrainingSession thirdTrainingSession = new TrainingSession(group1, coach3,
                DayOfWeek.TUESDAY, new TimeOfDay(20, 30));

        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);
        timetable.addNewTrainingSession(thirdTrainingSession);
        TreeMap<Coach, Integer> currentMap = timetable.getCountByCoaches();

        Assertions.assertEquals(3, currentMap.size());
    }



}

