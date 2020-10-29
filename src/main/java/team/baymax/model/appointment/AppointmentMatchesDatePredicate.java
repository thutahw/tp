package team.baymax.model.appointment;

import java.util.function.Predicate;

import team.baymax.model.util.datetime.Date;

public class AppointmentMatchesDatePredicate implements Predicate<Appointment> {

    private final Date date;

    public AppointmentMatchesDatePredicate(Date date) {
        this.date = date;
    }

    @Override
    public boolean test(Appointment appointment) {
        return date.equals(appointment.getDate());
    }
}
