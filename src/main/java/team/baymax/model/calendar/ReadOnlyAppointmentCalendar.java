package team.baymax.model.calendar;

public class ReadOnlyAppointmentCalendar extends AppointmentCalendar {

    public ReadOnlyAppointmentCalendar(AppointmentCalendar appointmentCalendar) {
        super();
        dayProperty.set(appointmentCalendar.getDayProperty().get());
        monthProperty.set(appointmentCalendar.getMonthProperty().get());
        yearProperty.set(appointmentCalendar.getYearProperty().get());
    }

    @Override
    public void setDayProperty(String dayProperty) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMonthProperty(String monthProperty) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setYearProperty(String yearProperty) {
        throw new UnsupportedOperationException();
    }
}
