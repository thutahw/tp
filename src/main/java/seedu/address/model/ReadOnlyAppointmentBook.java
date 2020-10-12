package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;

/**
 * Unmodifiable view of an appointment book
 */
public interface ReadOnlyAppointmentBook {

    /**
     * Returns an unmodifiable view of the patients list.
     * This list will not contain any duplicate patients.
     */
    ObservableList<Patient> getPatientList();
    ObservableList<Appointment> getAppointmentList();
}
