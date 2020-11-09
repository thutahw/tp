package team.baymax.model.modelmanagers;

import static java.util.Objects.requireNonNull;

import team.baymax.model.calendar.AppointmentCalendar;
import team.baymax.model.util.datetime.Date;
import team.baymax.model.util.datetime.Day;
import team.baymax.model.util.datetime.Month;
import team.baymax.model.util.datetime.Year;

public class CalendarManager {
    private final AppointmentCalendar appointmentCalendar;

    {
        appointmentCalendar = new AppointmentCalendar();
    }

    public CalendarManager() {}

    /**
     * Creates a CalendarManager using the day, month and year in {@code toBeCopied}
     */
    public CalendarManager(CalendarManager toBeCopied) {
        this();
        resetCalendar(toBeCopied);
    }

    public AppointmentCalendar getAppointmentCalendar() {
        return appointmentCalendar;
    }

    public void setDate(Date date) {
        requireNonNull(date);
        appointmentCalendar.setDate(date);
    }

    public void setDay(Day day) {
        requireNonNull(day);
        appointmentCalendar.setDay(day);
    }

    public void setMonth(Month month) {
        requireNonNull(month);
        appointmentCalendar.setMonth(month);
    }

    public void setYear(Year year) {
        requireNonNull(year);
        appointmentCalendar.setYear(year);
    }

    /**
     * Resets the calendar to the default current year, month and day.
     *
     */
    public void resetCalendar(CalendarManager newCalendar) {
        requireNonNull(newCalendar);

        AppointmentCalendar appointmentCalendar = newCalendar.getAppointmentCalendar();

        setYear(appointmentCalendar.getYear());
        setMonth(appointmentCalendar.getMonth());
        setDay(appointmentCalendar.getDay());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalendarManager // instanceof handles nulls
                && appointmentCalendar.equals(((CalendarManager) other).appointmentCalendar));
    }
}
