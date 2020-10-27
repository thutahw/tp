package team.baymax.model.modelmanagers;

import static java.util.Objects.requireNonNull;

import team.baymax.model.calendar.AppointmentCalendar;
import team.baymax.model.calendar.Day;
import team.baymax.model.calendar.Month;
import team.baymax.model.calendar.Year;

public class CalendarManager {
    private final AppointmentCalendar appointmentCalendar;

    {
        appointmentCalendar = new AppointmentCalendar();
    }

    public CalendarManager() {}

    public AppointmentCalendar getAppointmentCalendar() {
        return appointmentCalendar;
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

    public void resetCalendar(CalendarManager newCalendar) {
        AppointmentCalendar appointmentCalendar = newCalendar.getAppointmentCalendar();

        setYear(appointmentCalendar.getYear());
        setMonth(appointmentCalendar.getMonth());
        setDay(appointmentCalendar.getDay());
    }
}
