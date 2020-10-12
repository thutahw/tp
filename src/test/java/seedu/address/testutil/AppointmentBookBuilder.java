package seedu.address.testutil;

import seedu.address.model.AppointmentBook;
import seedu.address.model.patient.Patient;

/**
 * A utility class to help with building AppointmentBook objects.
 * Example usage: <br>
 *     {@code AppointmentBook ab = new AppointmentBookBuilder().withPatient("John", "Doe").build();}
 */
public class AppointmentBookBuilder {

    private AppointmentBook appointmentBook;

    public AppointmentBookBuilder() {
        appointmentBook = new AppointmentBook();
    }

    public AppointmentBookBuilder(AppointmentBook appointmentBook) {
        this.appointmentBook = appointmentBook;
    }

    /**
     * Adds a new {@code Patient} to the {@code AppointmentBook} that we are building.
     */
    public AppointmentBookBuilder withPatient(Patient patient) {
        appointmentBook.addPatient(patient);
        return this;
    }

    public AppointmentBook build() {
        return appointmentBook;
    }
}
