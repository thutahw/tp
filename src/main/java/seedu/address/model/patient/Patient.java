package seedu.address.model.patient;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentList;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.tag.Tag;

/**
 * Represents a Patient in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable. Appointments may change.
 */
public class Patient {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Gender gender;

    // Data fields
//    private final Address address;
    private final AppointmentList appointments;
    private final Set<Tag> tags = new HashSet<>();
    private final Remark remark;

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Phone phone, Gender gender, AppointmentList appointments, Set<Tag> tags, Remark remark) {
        requireAllNonNull(name, phone, gender, tags, remark);
        this.name = name;
        this.phone = phone;
        this.gender = gender;
//        this.address = address;
        this.appointments = appointments;
        this.tags.addAll(tags);
        this.remark = remark;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Gender getGender() {
        return gender;
    }

//    public Address getAddress() {
//        return address;
//    }

    public AppointmentList getAppointments() {
        return appointments;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Patient otherPatient) {
        if (otherPatient == this) {
            return true;
        }

        return otherPatient != null
                && otherPatient.getName().equals(getName())
                && (otherPatient.getPhone().equals(getPhone()) || otherPatient.getGender().equals(getGender()));
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

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        return otherPatient.getName().equals(getName())
                && otherPatient.getPhone().equals(getPhone())
                && otherPatient.getGender().equals(getGender())
//                && otherPatient.getAddress().equals(getAddress())
                && otherPatient.getAppointments().equals(getAppointments())
                && otherPatient.getTags().equals(getTags())
                && otherPatient.getRemark().equals(getRemark());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, gender, appointments, tags, remark);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Gender: ")
                .append(getGender())
//                .append(" Address: ")
//                .append(getAddress())
                .append(" Appointments: ")
                .append(getAppointments())
                .append(" Remark: ")
                .append(getRemark())
                .append(" Tags: ")
                .append(getTags());
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
