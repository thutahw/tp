package team.baymax.testutil.patient;

import java.util.HashSet;
import java.util.Set;

import team.baymax.model.patient.Gender;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.Nric;
import team.baymax.model.patient.Patient;
import team.baymax.model.patient.Phone;
import team.baymax.model.patient.Remark;
import team.baymax.model.tag.Tag;
import team.baymax.model.util.SampleDataUtil;

/**
 * A utility class to help with building Patient objects.
 */
public class PatientBuilder {

    public static final String DEFAULT_NRIC = "T1203540A";
    public static final String DEFAULT_NAME = "Daniel Gryffin";
    public static final String DEFAULT_PHONE = "81053637";
    public static final String DEFAULT_GENDER = "M";
    public static final String DEFAULT_REMARK = "No allergies.";

    private Nric nric;
    private Name name;
    private Phone phone;
    private Gender gender;
    private Set<Tag> tags;
    private Remark remark;

    /**
     * Creates a {@code PatientBuilder} with the default details.
     */
    public PatientBuilder() {
        nric = new Nric(DEFAULT_NRIC);
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        gender = new Gender(DEFAULT_GENDER);
        tags = new HashSet<>();
        remark = new Remark(DEFAULT_REMARK);
    }

    /**
     * Initializes the PatientBuilder with the data of {@code patientToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        nric = patientToCopy.getNric();
        name = patientToCopy.getName();
        phone = patientToCopy.getPhone();
        gender = patientToCopy.getGender();
        tags = new HashSet<>(patientToCopy.getTags());
        remark = patientToCopy.getRemark();
    }

    /**
     * Sets the {@code Nric} of the {@Code Patient} that we are building.
     */
    public PatientBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Patient} that we are building.
     */
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Patient} that we are building.
     */
    public PatientBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Patient} that we are building.
     */
    public PatientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Patient} that we are building.
     */
    public PatientBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Patient} that we are building.
     */
    public PatientBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    public Patient build() {
        return new Patient(nric, name, phone, gender, tags, remark);
    }

}
