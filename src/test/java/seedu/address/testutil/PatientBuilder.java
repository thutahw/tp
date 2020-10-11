package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PatientBuilder {

    public static final String DEFAULT_NAME = "Daniel Gryffin";
    public static final String DEFAULT_PHONE = "81053636";
    public static final String DEFAULT_GENDER = "F";
    public static final String DEFAULT_REMARK = "Likes to code";

    private Name name;
    private Phone phone;
    private Gender gender;
    private Set<Tag> tags;
    private Remark remark;

    /**
     * Creates a {@code PatientBuilder} with the default details.
     */
    public PatientBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        gender = new Gender(DEFAULT_GENDER);
        tags = new HashSet<>();
        remark = new Remark(DEFAULT_REMARK);
    }

    /**
     * Initializes the PatientBuilder with the data of {@code personToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        name = patientToCopy.getName();
        phone = patientToCopy.getPhone();
        gender = patientToCopy.getGender();
        tags = new HashSet<>(patientToCopy.getTags());
        remark = patientToCopy.getRemark();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PatientBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PatientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PatientBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PatientBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    public Patient build() {
        return new Patient(name, phone, gender, tags, remark);
    }

}
