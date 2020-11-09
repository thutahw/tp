package team.baymax.model.appointment;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import team.baymax.model.util.datetime.Date;

/**
 * Tests that an {@code Appointment}'s {@code Date} matches the date given.
 */
public class AppointmentMatchesDatePredicate implements Predicate<Appointment> {

    private final Date date;

    /**
     * Constructs the Predicate for testing.
     * @param date Date to be tested against for matching.
     */
    public AppointmentMatchesDatePredicate(Date date) {
        requireNonNull(date);
        this.date = date;
    }

    @Override
    public boolean test(Appointment appointment) {
        requireNonNull(appointment);
        return date.equals(appointment.getDate());
    }
}
