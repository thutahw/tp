package team.baymax.model.appointment;

import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;

import team.baymax.model.patient.Patient;
import team.baymax.model.util.datetime.DateTime;

/**
 * Tests that an {@code Appointment}'s {@code DateTime} and {@code Patient} matches the ones given.
 */
public class SameDatetimeAndPatientPredicate implements Predicate<Appointment> {
    private final DateTime dateTime;
    private final Patient patient;

    /**
     * Constructor taking in a dateTime and Patient object to match Appointments with
     * @param dateTime  DateTime object in the format DD-MM-YYYY HH:MM
     * @param patient   Patient object
     */
    public SameDatetimeAndPatientPredicate(DateTime dateTime, Patient patient) {
        requireAllNonNull(dateTime, patient);
        this.dateTime = dateTime;
        this.patient = patient;
    }

    @Override
    public boolean test(Appointment appointment) {
        return (appointment.getDateTime().equals(dateTime))
                && (appointment.getPatient().equals(patient));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SameDatetimeAndPatientPredicate // instanceof handles nulls
                && dateTime.equals(((SameDatetimeAndPatientPredicate) other).dateTime)
                && patient.equals(((SameDatetimeAndPatientPredicate) other).patient)); // state check
    }
}
