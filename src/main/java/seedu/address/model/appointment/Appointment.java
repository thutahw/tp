package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {

    private final String description;
    private final String patient;

    /**
     * Every field must be present and not null.
     */
    public Appointment(String description, String patient) {
        this.description = description;
        this.patient = patient;
    }

    public String getDescription() {
        return description;
    }

    public String getPatient() {
        return patient;
    }
    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameAppointment(Appointment otherAppt) {
        if (otherAppt == this) {
            return true;
        }

        return otherAppt != null
                && otherAppt.getDescription().equals(getDescription());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherPerson = (Appointment) other;
        return otherPerson.getPatient().equals(getPatient());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(patient, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getPatient())
                .append(" Description: ")
                .append(getDescription());
        return builder.toString();
    }

}
