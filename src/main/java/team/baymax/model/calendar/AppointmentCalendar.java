package team.baymax.model.calendar;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

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

    private Date date;

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public AppointmentCalendar() {
        this(DateTimeUtil.getCurrentDate());
    }

    /**
     * Constructs an {@code AppointmentCalendar} given the {@code day}, {@code month} and {@code year}.
     *
     */
    public AppointmentCalendar(Date date) {
        requireAllNonNull(date);

        this.date = date;

        this.dayProperty = new SimpleStringProperty(date.getDay().toString());
        this.monthProperty = new SimpleStringProperty(date.getMonth().toString());
        this.yearProperty = new SimpleStringProperty(date.getYear().toString());
        this.dateProperty = new SimpleStringProperty(date.toString());
    }

    public Date getDate() {
        return date;
    }

    public Day getDay() {
        return date.getDay();
    }

    public Month getMonth() {
        return date.getMonth();
    }

    public Year getYear() {
        return date.getYear();
    }

    public void setDate(Date date) {
        requireNonNull(date);
        this.date = date;
        updateDateProperty(date);
    }

    public void setDay(Day day) {
        requireNonNull(day);
        Day prev = getDay();
        setDate(new Date(day, getMonth(), getYear()));
        pcs.firePropertyChange("day", prev, getDay());
    }

    public void setMonth(Month month) {
        requireNonNull(month);
        Month prev = getMonth();
        setDate(DateTimeUtil.getClosestValidDate(getDay(), month, getYear()));
        pcs.firePropertyChange("month", prev, getMonth());
    }

    public void setYear(Year year) {
        requireNonNull(year);
        Year prev = getYear();
        setDate(DateTimeUtil.getClosestValidDate(getDay(), getMonth(), year));
        pcs.firePropertyChange("year", prev, year);
    }

    /**
     * Updates the date property of the {@code AppointmentCalendar}.
     *
     */
    public void updateDateProperty(Date date) {
        dateProperty.set(getDate().toString());
        dayProperty.set(getDay().toString());
        monthProperty.set(getMonth().toString());
        yearProperty.set(getYear().toString());
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
                && date.equals(((AppointmentCalendar) other).getDate()));
    }
}
