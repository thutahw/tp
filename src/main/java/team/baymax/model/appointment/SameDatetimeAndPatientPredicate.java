package team.baymax.model.appointment;

import java.util.function.Predicate;

import team.baymax.commons.core.time.DateTime;
import team.baymax.model.patient.Patient;

/**
 * Tests that an {@code Appointment}'s {@code DateTime} and {@code Patient} matches the ones given.
 */
public class SameDatetimeAndPatientPredicate implements Predicate<Appointment> {
    private final DateTime dt;
    private final Patient patient;

    public SameDatetimeAndPatientPredicate(DateTime dt, Patient patient) {
        this.dt = dt;
        this.patient = patient;
    }

    public DateTime getDt() {
        return dt;
    }

    public Patient getPatient() {
        return patient;
    }

    @Override
    public boolean test(Appointment appointment) {
        return (appointment.getDateTime().equals(dt))
                && (appointment.getPatient().equals(patient));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SameDatetimeAndPatientPredicate // instanceof handles nulls
                && dt.equals(((SameDatetimeAndPatientPredicate) other).getDt())
                && patient.equals(((SameDatetimeAndPatientPredicate) other).getPatient())); // state check
    }
}
