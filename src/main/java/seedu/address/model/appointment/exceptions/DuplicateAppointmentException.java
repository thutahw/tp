package seedu.address.model.appointment.exceptions;

/**
 * Signals that the operation will result in duplicate Appointment (Appointments are considered duplicates
 * if they have the same date and time).
 */
public class DuplicateAppointmentException extends RuntimeException {
    public DuplicateAppointmentException() {
        super("Operation would result in duplicate appointments");
    }
}
