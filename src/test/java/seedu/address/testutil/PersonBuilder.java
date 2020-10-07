package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.patient.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_REMARK = "Likes to code";

    private Name name;
    private Phone phone;
<<<<<<< HEAD
    private Email email;
=======
    private Gender gender;
//    private Address address;
>>>>>>> 4f043b0927b895a9c6a0fb5ec9ee744d7d02adef
    private Set<Tag> tags;
    private Remark remark;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
<<<<<<< HEAD
        email = new Email(DEFAULT_EMAIL);
=======
        gender = new Gender(DEFAULT_EMAIL);
//        address = new Address(DEFAULT_ADDRESS);
>>>>>>> 4f043b0927b895a9c6a0fb5ec9ee744d7d02adef
        tags = new HashSet<>();
        remark = new Remark(DEFAULT_REMARK);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Patient patientToCopy) {
        name = patientToCopy.getName();
        phone = patientToCopy.getPhone();
<<<<<<< HEAD
        email = patientToCopy.getEmail();
=======
        gender = patientToCopy.getGender();
//        address = patientToCopy.getAddress();
>>>>>>> 4f043b0927b895a9c6a0fb5ec9ee744d7d02adef
        tags = new HashSet<>(patientToCopy.getTags());
        remark = patientToCopy.getRemark();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
<<<<<<< HEAD
=======
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
//    public PersonBuilder withAddress(String address) {
//        this.address = new Address(address);
//        return this;
//    }

    /**
>>>>>>> 4f043b0927b895a9c6a0fb5ec9ee744d7d02adef
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.gender = new Gender(email);
        return this;
    }

    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    public Patient build() {
<<<<<<< HEAD
        return new Patient(name, phone, email, tags, remark);
=======
        return new Patient(name, phone, gender, tags, remark);
>>>>>>> 4f043b0927b895a9c6a0fb5ec9ee744d7d02adef
    }

}
