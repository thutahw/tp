package team.baymax.testutil.patient;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import team.baymax.logic.commands.patient.EditPatientCommand;
import team.baymax.model.patient.Gender;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.Nric;
import team.baymax.model.patient.Patient;
import team.baymax.model.patient.Phone;
import team.baymax.model.patient.Remark;
import team.baymax.model.tag.Tag;

/**
 * A utility class to help with building EditPatientDescriptor objects.
 */
public class EditPatientDescriptorBuilder {

    private EditPatientCommand.EditPatientDescriptor descriptor;

    public EditPatientDescriptorBuilder() {
        descriptor = new EditPatientCommand.EditPatientDescriptor();
    }

    public EditPatientDescriptorBuilder(EditPatientCommand.EditPatientDescriptor descriptor) {
        this.descriptor = new EditPatientCommand.EditPatientDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPatientDescriptor} with fields containing {@code patient}'s details
     */
    public EditPatientDescriptorBuilder(Patient patient) {
        descriptor = new EditPatientCommand.EditPatientDescriptor();
        descriptor.setNric(patient.getNric());
        descriptor.setName(patient.getName());
        descriptor.setPhone(patient.getPhone());
        descriptor.setGender(patient.getGender());
        descriptor.setTags(patient.getTags());
        descriptor.setRemark(patient.getRemark());
    }

    /**
     * Sets the {@code Nric} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withNric(String nric) {
        descriptor.setNric(new Nric(nric));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withGender(String gender) {
        descriptor.setGender(new Gender(gender));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPatientDescriptor}
     * that we are building.
     */
    public EditPatientDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditPatientDescriptor} that we are building.
     */
    public EditPatientDescriptorBuilder withRemark(String remark) {
        descriptor.setRemark(new Remark(remark));
        return this;
    }

    public EditPatientCommand.EditPatientDescriptor build() {
        return descriptor;
    }
}
