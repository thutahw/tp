package team.baymax.model.calendar;

import team.baymax.model.util.datetime.Day;
import team.baymax.model.util.datetime.Month;
import team.baymax.model.util.datetime.Year;

public class ReadOnlyAppointmentCalendar extends AppointmentCalendar {

    /**
     * Constructs an unmodifiable {@code ReadOnlyAppointmentCalendar} from an {@appointmentCalendar}.
     *
     */
    public ReadOnlyAppointmentCalendar(AppointmentCalendar appointmentCalendar) {
        super();
        dayProperty.set(appointmentCalendar.getDayProperty().get());
        monthProperty.set(appointmentCalendar.getMonthProperty().get());
        yearProperty.set(appointmentCalendar.getYearProperty().get());
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
