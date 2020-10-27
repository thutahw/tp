package team.baymax.logic.commands.appointment;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import team.baymax.model.calendar.DateTime;
import team.baymax.commons.util.CollectionUtil;
import team.baymax.model.appointment.AppointmentStatus;
import team.baymax.model.appointment.Description;
import team.baymax.model.patient.Patient;
import team.baymax.model.tag.Tag;

/**
 * Stores the details to edit the appointment with. Each non-empty field value will replace the
 * corresponding field value of the appointment.
 */
public class EditAppointmentDescriptor {
    private Patient patient;
    private DateTime dateTime;
    private AppointmentStatus status;
    private Set<Tag> tags;
    private Description description;

    public EditAppointmentDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
        setPatient(toCopy.patient);
        setDateTime(toCopy.dateTime);
        setStatus(toCopy.status);
        setTags(toCopy.tags);
        setDescription(toCopy.description);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(patient, dateTime, status, tags, description);
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Optional<Patient> getPatient() {
        return Optional.ofNullable(patient);
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Optional<DateTime> getDateTime() {
        return Optional.ofNullable(dateTime);
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public Optional<AppointmentStatus> getStatus() {
        return Optional.ofNullable(status);
    }

    /**
     * Sets {@code tags} to this object's {@code tags}.
     * A defensive copy of {@code tags} is used internally.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : null;
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    public Optional<Description> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAppointmentDescriptor)) {
            return false;
        }

        // state check
        EditAppointmentDescriptor e = (EditAppointmentDescriptor) other;

        return getPatient().equals(e.getPatient())
                && getDateTime().equals(e.getDateTime())
                && getStatus().equals(e.getStatus())
                && getTags().equals(e.getTags())
                && getDescription().equals(e.getDescription());
    }
}
