package team.baymax.model.calendar;

public class Time {

    private final Hour hour;
    private final Minute minute;

    /**
     * Constructs a {@code Time} object given the {@code hour} and {@code minute}.
     *
     */
    public Time(Hour hour, Minute minute) {
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public String toString() {
        return hour + ":" + minute;
    }
}
