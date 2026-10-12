package ru.yandex.practicum.gym;

public class TrainingSession implements Comparable<TrainingSession> {

    private Group group;
    private Coach coach;
    private DayOfWeek dayOfWeek;
    private TimeOfDay timeOfDay;

    public TrainingSession(Group group, Coach coach, DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        this.group = group;
        this.coach = coach;
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
    }

    @Override
    public int compareTo(TrainingSession t){
        return timeOfDay.compareTo(t.getTimeOfDay());
    }

    public Group getGroup() {
        return group;
    }

    public Coach getCoach() {
        return coach;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    @Override
    public String toString() {
        return "TrainingSession{" +
                "coach=" + coach +
                ", group=" + group +
                ", dayOfWeek=" + dayOfWeek +
                ", timeOfDay=" + timeOfDay +
                '}';
    }

}
