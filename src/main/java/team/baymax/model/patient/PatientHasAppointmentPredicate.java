package team.baymax.model.patient;

import java.util.function.Predicate;

import team.baymax.model.appointment.Appointment;

/**
 * Tests that a {@code Appointment}'s {@code Patient} matches the patient given.
 */
public class PatientHasAppointmentPredicate implements Predicate<Appointment> {

    private final Patient patient;

    public PatientHasAppointmentPredicate(Patient patient) {
        this.patient = patient;
    }

    @Override
    public boolean test(Appointment appointment) {
        return patient.isSame(appointment.getPatient());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PatientHasAppointmentPredicate // instanceof handles nulls
                && patient.equals(((PatientHasAppointmentPredicate) other).patient)); // state check
    }

}
