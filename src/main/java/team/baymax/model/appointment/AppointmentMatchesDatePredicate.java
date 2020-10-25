package team.baymax.model.appointment;

import java.util.function.Predicate;

import team.baymax.model.calendar.Date;

public class AppointmentMatchesDatePredicate implements Predicate<Appointment> {

    private final Date date;

    public AppointmentMatchesDatePredicate(Date date) {
        this.date = date;
    }

    @Override
    public boolean test(Appointment appointment) {
        return date.isSameDate(appointment.getDateTime());
    }
}
