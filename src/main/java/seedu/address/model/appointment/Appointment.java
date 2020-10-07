package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Remark;
import seedu.address.model.tag.Tag;

public class Appointment implements Comparator<Appointment> {
    private final Patient patient;
    private final LocalDateTime dateTime;
    private final AppointmentStatus status;
    private final Set<Tag> tags;
    private final Remark remark;

    //Default constructor
    public Appointment(Patient patient, LocalDateTime dateTime, Set<Tag> tags, Remark remark) {
        requireNonNull(patient);
        requireNonNull(dateTime);
        requireNonNull(remark);
        this.patient = patient;
        this.dateTime = dateTime;
        this.remark = remark;
        this.tags = tags;
        status = AppointmentStatus.UPCOMING;
    }

    //Constructor with status option, used for backdating appointments
    public Appointment(Patient patient, LocalDateTime dateTime, AppointmentStatus status, Set<Tag> tags,
                       Remark remark) {
        this.patient = patient;
        this.dateTime = dateTime;
        this.status = status;
        this.tags = tags;
        this.remark = remark;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Remark getRemark() {
        return remark;
    }

    public Appointment editPatient(Patient newPatient) {
        return new Appointment(newPatient, getDateTime(), getStatus(), getTags(), getRemark());
    }

    public Appointment editDateTime(LocalDateTime newDateTime) {
        return new Appointment(getPatient(), newDateTime, getStatus(), getTags(), getRemark());
    }

    public Appointment editTag(Set<Tag> newTags) {
        return new Appointment(getPatient(), getDateTime(), getStatus(), newTags, getRemark());
    }

    public Appointment editDescription(Remark newRemark) {
        return new Appointment(getPatient(), getDateTime(), getStatus(), getTags(), newRemark);
    }

    public Appointment editAppointmentStatus(AppointmentStatus newStatus) {
        return new Appointment(getPatient(), getDateTime(), newStatus, getTags(), getRemark());
    }

    /* Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameAppointment(Appointment otherAppt) {
        if (otherAppt == this) {
            return true;
        }

        return otherAppt != null
                && otherAppt.getRemark().equals(getRemark());
    }

    @Override
    public int compare(Appointment appointment, Appointment t1) {
        return t1.getDateTime().compareTo(appointment.getDateTime());
    }

    /* Returns true if both persons have the same identity and data fields.
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
        return Objects.hash(patient, remark);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getPatient())
                .append(getDateTime().toString())
                .append(getStatus())
                .append(getTags())
                .append(" Remarks: ")
                .append(getRemark());
        return builder.toString();
    }
}
