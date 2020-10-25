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
        appointmentCalendar.setDayProperty(day.getText());
    }

    public void setMonth(Month month) {
        requireNonNull(month);
        appointmentCalendar.setMonthProperty(month.getText());
    }

    public void setYear(Year year) {
        requireNonNull(year);
        appointmentCalendar.setYearProperty(year.getText());
    }

    public void resetCalendar(CalendarManager newCalendar) {
        AppointmentCalendar appointmentCalendar = newCalendar.getAppointmentCalendar();

        setYear(appointmentCalendar.getYear());
        setMonth(appointmentCalendar.getMonth());
        setDay(appointmentCalendar.getDay());
    }
}
