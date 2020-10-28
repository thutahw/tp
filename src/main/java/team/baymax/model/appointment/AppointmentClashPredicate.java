package team.baymax.model.appointment;

import java.util.function.Predicate;

import team.baymax.model.patient.Patient;
import team.baymax.model.util.datetime.DateTime;

public class AppointmentClashPredicate implements Predicate<Appointment> {

    private final Appointment appointment;

    public AppointmentClashPredicate(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public boolean test(Appointment other) {
        Patient patient = appointment.getPatient();
        DateTime startDateTime = appointment.getDateTime();
        DateTime endDateTime = appointment.getEndDateTime();

        return patient.equals(other.getPatient())
                && !(endDateTime.isEqual(other.getDateTime())
                    || endDateTime.isBefore(other.getDateTime())
                    || startDateTime.isEqual(other.getEndDateTime())
                    || startDateTime.isAfter(other.getEndDateTime()));
    }
}
