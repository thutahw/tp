package team.baymax.model.calendar;

public class Date {

    private final Year year;
    private final Month month;
    private final Day day;

    public Date(Day day, Month month, Year year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public static Date fromCalendar(AppointmentCalendar calendar) {
        return new Date(calendar.getDay(), calendar.getMonth(), calendar.getYear());
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

    @Override
    public String toString() {
        return day.getValue() + "-" + month.getValue() + "-" + year.getValue();
    }
}
