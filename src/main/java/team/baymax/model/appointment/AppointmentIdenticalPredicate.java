package team.baymax.model.appointment;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

/**
 * Tests that a {@code Appointment} is the same as another {@code Appointment}.
 */
public class AppointmentIdenticalPredicate implements Predicate<Appointment> {

    private final Appointment appointment;

    /**
     * Constructs the Predicate for testing.
     * @param appointment The appointment to be tested against.
     */
    public AppointmentIdenticalPredicate(Appointment appointment) {
        requireNonNull(appointment);
        this.appointment = appointment;
    }

    @Override
    public boolean test(Appointment appointment) {
        requireNonNull(appointment);
        return appointment.equals(this.appointment);
    }
}
