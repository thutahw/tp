package team.baymax.model.calendar;

import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Calendar;

import javafx.beans.property.SimpleStringProperty;

public class AppointmentCalendar {
    protected final SimpleStringProperty dayProperty;
    protected final SimpleStringProperty monthProperty;
    protected final SimpleStringProperty yearProperty;

    private Day day;
    private Month month;
    private Year year;



    public AppointmentCalendar() {
        this(new Day(getCurrentDay()), new Month(getCurrentMonth()), new Year(getCurrentYear()));
    }

    public AppointmentCalendar(Day day, Month month, Year year) {
        requireAllNonNull(day, month, year);

        this.day = day;
        this.month = month;
        this.year = year;

        this.dayProperty = new SimpleStringProperty(day.toString());
        this.monthProperty = new SimpleStringProperty(month.toString());
        this.yearProperty = new SimpleStringProperty(year.toString());
    }

    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public ReadOnlyAppointmentCalendar asUnmodifiableAppointmentCalendar() {
        return new ReadOnlyAppointmentCalendar(this);
    }

    public Day getDay() {
        return day;
    }

    public Month getMonth() {
        return month;
    }

    public Year getYear() {
        return year;
    }

    public void setDay(Day day) {
        this.day = day;
        dayProperty.set(day.toString());
    }

    public void setMonth(Month month) {
        this.month = month;
        monthProperty.set(month.toString());
    }

    public void setYear(Year year) {
        this.year = year;
        yearProperty.set(year.toString());
    }

    public SimpleStringProperty getDayProperty() {
        return dayProperty;
    }

    public SimpleStringProperty getMonthProperty() {
        return monthProperty;
    }

    public SimpleStringProperty getYearProperty() {
        return yearProperty;
    }
}
