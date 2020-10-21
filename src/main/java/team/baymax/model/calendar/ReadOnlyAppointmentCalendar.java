package team.baymax.model.calendar;

public class ReadOnlyAppointmentCalendar extends AppointmentCalendar {

    private final Day day;
    private final Month month;
    private final Year year;

    public ReadOnlyAppointmentCalendar(AppointmentCalendar appointmentCalendar) {
        day = appointmentCalendar.getDay();
        month = appointmentCalendar.getMonth();
        year = appointmentCalendar.getYear();
    }

    @Override
    public void setDay(Day day) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMonth(Month month) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setYear(Year year) {
        throw new UnsupportedOperationException();
    }
}
