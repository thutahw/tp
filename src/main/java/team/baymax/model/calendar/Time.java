package team.baymax.model.calendar;

public class Time {

    private final Hour hour;
    private final Minute minute;

    public Time(Hour hour, Minute minute) {
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public String toString() {
        return hour + ":" + minute;
    }
}
