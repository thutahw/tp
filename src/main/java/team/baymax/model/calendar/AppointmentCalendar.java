package team.baymax.model.calendar;

import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Calendar;

import javafx.beans.property.SimpleStringProperty;

public class AppointmentCalendar {

    protected final SimpleStringProperty dayProperty;
    protected final SimpleStringProperty monthProperty;
    protected final SimpleStringProperty yearProperty;


    public AppointmentCalendar() {
        this.dayProperty = new SimpleStringProperty(Integer.toString(getCurrentday()));
        this.monthProperty = new SimpleStringProperty(Integer.toString(getCurrentMonth()));
        this.yearProperty = new SimpleStringProperty(Integer.toString(getCurrentYear()));
    }

    public AppointmentCalendar(Day day, Month month, Year year) {
        requireAllNonNull(day, month, year);

        this.dayProperty = new SimpleStringProperty(day.getText());
        this.monthProperty = new SimpleStringProperty(month.getText());
        this.yearProperty = new SimpleStringProperty(year.getText());
    }

    public static int getCurrentday() {
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
        return new Day(Integer.parseInt(dayProperty.get()));
    }

    public Month getMonth() {
        return new Month(Integer.parseInt(monthProperty.get()));
    }

    public Year getYear() {
        return new Year(Integer.parseInt(yearProperty.get()));
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

    public void setDayProperty(String dayProperty) {
        this.dayProperty.set(dayProperty);
    }

    public void setMonthProperty(String monthProperty) {
        this.monthProperty.set(monthProperty);
    }

    public void setYearProperty(String yearProperty) {
        this.yearProperty.set(yearProperty);
    }

}
