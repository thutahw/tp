package team.baymax.model.calendar;

import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Calendar;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class AppointmentCalendar {

    private ObjectProperty<Day> day;
    private ObjectProperty<Month> month;
    private ObjectProperty<Year> year;

    public AppointmentCalendar() {
        Calendar calendar = Calendar.getInstance();

        int defaultYear = calendar.get(Calendar.YEAR);
        int defaultMonth = calendar.get(Calendar.MONTH);
        int defaultDay = calendar.get(Calendar.DAY_OF_MONTH);

        this.day = new SimpleObjectProperty<>(new Day(defaultDay));
        this.month = new SimpleObjectProperty<>(new Month(defaultMonth));
        this.year = new SimpleObjectProperty<>(new Year(defaultYear));
    }

    public AppointmentCalendar(Day day, Month month, Year year) {
        requireAllNonNull(day, month, year);

        this.day = new SimpleObjectProperty<>(day);
        this.month = new SimpleObjectProperty<>(month);
        this.year = new SimpleObjectProperty<>(year);
    }

    public ReadOnlyAppointmentCalendar asUnmodifiableAppointmentCalendar() {
        return new ReadOnlyAppointmentCalendar(this);
    }

    public Day getDay() {
        return day.get();
    }

    public Month getMonth() {
        return month.get();
    }

    public Year getYear() {
        return year.get();
    }

    public void setDay(Day day) {
        this.day.set(day);
    }

    public void setMonth(Month month) {
        this.month.set(month);
    }

    public void setYear(Year year) {
        this.year.set(year);
    }

    public Date getDate() {
        return Date.fromCalendar(this);
    }

}
