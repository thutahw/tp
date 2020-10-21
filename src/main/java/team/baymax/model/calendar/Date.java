package team.baymax.model.calendar;

import java.time.format.DateTimeFormatter;

import team.baymax.model.appointment.DateTime;

public class Date {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final Year year;
    private final Month month;
    private final Day day;

    private Date(Day day, Month month, Year year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public static Date fromCalendar(AppointmentCalendar ac) {
        return new Date(ac.getDay(), ac.getMonth(), ac.getYear());
    }

    public Year getYear() {
        return year;
    }

    public Month getMonth() {
        return month;
    }

    public Day getDay() {
        return day;
    }

    public boolean isSameDate(DateTime dateTime) {
        return dateTime != null
                && dateTime.getDay() == day.getValue()
                && dateTime.getMonth() == month.getValue()
                && dateTime.getYear() == year.getValue();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;

        return other != null
                && otherDate.getDay().equals(day)
                && otherDate.getMonth().equals(month)
                && otherDate.getYear().equals(year);
    }
}
