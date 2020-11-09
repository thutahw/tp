package team.baymax.testutil.appointment;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import team.baymax.logic.commands.appointment.EditAppointmentDescriptor;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.Description;
import team.baymax.model.tag.Tag;
import team.baymax.model.util.datetime.DateTime;

/**
 * A utility class to help with building EditAppointmentDescriptor objects.
 */
public class EditAppointmentDescriptorBuilder {

    private EditAppointmentDescriptor descriptor;

    public EditAppointmentDescriptorBuilder() {
        descriptor = new EditAppointmentDescriptor();
    }

    public EditAppointmentDescriptorBuilder(EditAppointmentDescriptor descriptor) {
        this.descriptor = new EditAppointmentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditAppointmentDescriptor} with fields containing {@code appointment}'s details
     */
    public EditAppointmentDescriptorBuilder(Appointment appointment) {
        descriptor = new EditAppointmentDescriptor();
        descriptor.setDateTime(appointment.getDateTime());
        descriptor.setDescription(appointment.getDescription());
        descriptor.setTags(appointment.getTags());

    }


    /**
     * Sets the {@code Nric} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withDateTime(String dateTime) {
        descriptor.setDateTime(DateTime.fromString(dateTime));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditAppointmentDescriptor}
     * that we are building.
     */
    public EditAppointmentDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditAppointmentDescriptor build() {
        return descriptor;
    }
}
