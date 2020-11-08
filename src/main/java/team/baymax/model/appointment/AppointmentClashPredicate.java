package team.baymax.model.appointment;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import team.baymax.model.patient.Patient;
import team.baymax.model.util.datetime.DateTime;

public class AppointmentClashPredicate implements Predicate<Appointment> {

    private final Appointment appointment;
    private final Appointment toExclude;

    /**
     * Constructs a {@code AppointmentClashPredicate} that checks for clashes between appointments.
     * @param appointment
     * @param toExclude
     */
    public AppointmentClashPredicate(Appointment appointment, Appointment toExclude) {
        requireNonNull(appointment);
        this.appointment = appointment;
        this.toExclude = toExclude;
    }

    @Override
    public boolean test(Appointment other) {
        requireNonNull(other);
        Patient patient = appointment.getPatient();
        DateTime startDateTime = appointment.getDateTime();
        DateTime endDateTime = appointment.getEndDateTime();

        return (toExclude == null || !toExclude.isSame(other))
                && patient.equals(other.getPatient())
                && !(endDateTime.isEqual(other.getDateTime())
                    || endDateTime.isBefore(other.getDateTime())
                    || startDateTime.isEqual(other.getEndDateTime())
                    || startDateTime.isAfter(other.getEndDateTime()));
    }
}
