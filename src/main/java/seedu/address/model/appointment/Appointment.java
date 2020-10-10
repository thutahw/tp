package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;

public class Appointment implements Comparator<Appointment> {
    private final Patient patient;
    private final LocalDateTime dateTime;
    private final AppointmentStatus status;
    private final Set<Tag> tags;
    private final Description description;

    /**
     * Every field must be present and not null.
     */
    public Appointment(Patient patient, LocalDateTime dateTime, Set<Tag> tags, Description description,
                       AppointmentStatus status) {
        requireAllNonNull(patient, dateTime, tags, description);
        this.patient = patient;
        this.dateTime = dateTime;
        this.description = description;
        this.tags = tags;
        this.status = status;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Description getDescription() {
        return description;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /** Returns true if both appointments have the same date and time.
     * This defines a weaker notion of equality between two appointments.
     */
    public boolean isSameAppointment(Appointment otherAppointment) {
        if (otherAppointment == this) {
            return true;
        }

        return otherAppointment != null
                && otherAppointment.getPatient().equals(getPatient())
                && otherAppointment.getDateTime().equals(getDateTime());
    }

    @Override
    public int compare(Appointment a1, Appointment a2) {
        return a2.getDateTime().compareTo(a1.getDateTime());
    }

    /**
     * Returns true if both appointments have the same identity and data fields.
     * This defines a stronger notion of equality between two appointments.
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
        return Objects.hash(patient, dateTime, status, tags, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getPatient())
                .append(" Date time: ")
                .append(getDateTime())
                .append(" Description: ")
                .append(getDescription())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
