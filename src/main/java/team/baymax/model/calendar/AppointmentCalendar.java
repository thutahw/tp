package team.baymax.model.calendar;

import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Calendar;

import javafx.beans.property.SimpleStringProperty;
import team.baymax.model.util.datetime.Date;
import team.baymax.model.util.datetime.DateTimeUtil;
import team.baymax.model.util.datetime.Day;
import team.baymax.model.util.datetime.Month;
import team.baymax.model.util.datetime.Year;


// make AppointmentCalendar a java bean class to support property change listeners
public class AppointmentCalendar {

    protected final SimpleStringProperty dayProperty;
    protected final SimpleStringProperty monthProperty;
    protected final SimpleStringProperty yearProperty;
    protected final SimpleStringProperty dateProperty;

    private Day day;
    private Month month;
    private Year year;

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public AppointmentCalendar() {
        this(new Day(getCurrentDay()), new Month(getCurrentMonth()), new Year(getCurrentYear()));
    }

    /**
     * Constructs an {@code AppointmentCalendar} given the {@code day}, {@code month} and {@code year}.
     *
     */
    public AppointmentCalendar(Day day, Month month, Year year) {
        requireAllNonNull(day, month, year);

        this.day = day;
        this.month = month;
        this.year = year;

        this.dayProperty = new SimpleStringProperty(day.toString());
        this.monthProperty = new SimpleStringProperty(month.toString());
        this.yearProperty = new SimpleStringProperty(year.toString());
        this.dateProperty = new SimpleStringProperty(new Date(day, month, year).toString());
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

    public Day getDay() {
        return day;
    }

    public Month getMonth() {
        return month;
    }

    public Year getYear() {
        return year;
    }

    /**
     * Updates the value of the {@code day} attribute stored in the {@code AppointmentCalendar}.
     */
    public void updateDay() {
        int maxNumOfDays = DateTimeUtil.getNumOfDays(this.month, this.year);
        if (day.getValue() > maxNumOfDays) {
            setDay(new Day(maxNumOfDays));
        } else {
            setDay(day);
        }
    }

    public void setDay(Day day) {
        Day prev = this.day;
        Date newDate = new Date(day, month, year);
        this.day = day;
        updateDateProperty(newDate);
        pcs.firePropertyChange("day", prev, day);
    }

    public void setMonth(Month month) {
        Month prev = this.month;
        Date newDate = new Date(day, month, year);
        this.month = month;
        updateDay();
        pcs.firePropertyChange("month", oldMonth, month);
    }

    public void setYear(Year year) {
        Year prev = this.year;
        Date newDate = new Date(day, month, year);
        this.year = year;
        updateDay();
        pcs.firePropertyChange("year", oldYear, year);
    }

    /**
     * Updates the date property of the {@code AppointmentCalendar}.
     *
     */
    public void updateDateProperty(Date date) {
        dateProperty.set(date.toString());
        dayProperty.set(date.getDay().toString());
        monthProperty.set(date.getMonth().toString());
        yearProperty.set(date.getYear().toString());
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

    public SimpleStringProperty getDateProperty() {
        return dateProperty;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentCalendar // instanceof handles nulls
                && day.equals(((AppointmentCalendar) other).day)
                && month.equals(((AppointmentCalendar) other).month)
                && year.equals(((AppointmentCalendar) other).year));
    }
}
