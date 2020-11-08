package team.baymax.model.appointment;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import team.baymax.model.patient.Patient;

/**
 * Tests that an {@code Appointment}'s {@code Patient} matches the one given.
 */
public class BelongsToPatientPredicate implements Predicate<Appointment> {
    private final Patient patient;

    /**
     * Constructor taking in a Patient object to match Appointments with
     * @param patient Patient object
     */
    public BelongsToPatientPredicate(Patient patient) {
        requireNonNull(patient);
        this.patient = patient;
    }

    @Override
    public boolean test(Appointment appointment) {
        return appointment.getPatient().equals(patient);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BelongsToPatientPredicate // instanceof handles nulls
                && patient.equals(((BelongsToPatientPredicate) other).patient)); // state check
    }
}
