package team.baymax.model.calendar;

import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Calendar;

import javafx.beans.property.SimpleStringProperty;
import team.baymax.model.calendar.utils.CalendarUtil;


// make AppointmentCalednar a java bean class to support property change listeners
public class AppointmentCalendar {
    protected final SimpleStringProperty dayProperty;
    protected final SimpleStringProperty monthProperty;
    protected final SimpleStringProperty yearProperty;

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

    public void updateDay() {
        int maxNumOfDays = CalendarUtil.getNumOfDays(this.month, this.year);
        if (this.day.getValue() > maxNumOfDays) {
            this.setDay(new Day(maxNumOfDays));
        }
    }

    public void setDay(Day day) {
        Day oldDay = this.day;
        this.day = day;
        dayProperty.set(day.toString());
        pcs.firePropertyChange("day", oldDay, day);
    }

    public void setMonth(Month month) {
        Month oldMonth = this.month;
        this.month = month;
        monthProperty.set(month.toString());
        pcs.firePropertyChange("month", oldMonth, month);
        updateDay();
    }

    public void setYear(Year year) {
        Year oldYear = this.year;
        this.year = year;
        yearProperty.set(year.toString());
        pcs.firePropertyChange("year", oldYear, year);
        updateDay();
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

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
}
