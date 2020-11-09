package team.baymax.logic.commands.appointment;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import team.baymax.commons.util.CollectionUtil;
import team.baymax.model.appointment.Description;
import team.baymax.model.tag.Tag;
import team.baymax.model.util.datetime.DateTime;
import team.baymax.model.util.datetime.Duration;

/**
 * Stores the details to edit the appointment with. Each non-empty field value will replace the
 * corresponding field value of the appointment.
 */
public class EditAppointmentDescriptor {
    private DateTime dateTime;
    private Duration duration;
    private Set<Tag> tags;
    private Description description;

    public EditAppointmentDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
        setDateTime(toCopy.dateTime);
        setDuration(toCopy.duration);
        setTags(toCopy.tags);
        setDescription(toCopy.description);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(dateTime, tags, description, duration);
    }

    /**
     * Returns true if the datetime field is edited.
     */
    public boolean isDateTimeEdited() {
        return CollectionUtil.isNonNull(dateTime);
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Optional<DateTime> getDateTime() {
        return Optional.ofNullable(dateTime);
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Optional<Duration> getDuration() {
        return Optional.ofNullable(duration);
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

        return getDateTime().equals(e.getDateTime())
                && getDuration().equals(e.getDuration())
                && getTags().equals(e.getTags())
                && getDescription().equals(e.getDescription());
    }
}
