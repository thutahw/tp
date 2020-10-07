package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.patient.Patient;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Patient ALICE = new PersonBuilder().withName("Alice Pauline")
            .withGender("female")
            .withPhone("94351253")
            .withTags("friends").build();

    // Manually added

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Patient BENSON = new PersonBuilder().withName("Benson Meier")
            .withGender("male").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Patient CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withGender("male").build();
    public static final Patient DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withGender("male").withTags("friends").build();
    public static final Patient ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withGender("female").build();
    public static final Patient FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withGender("female").build();
    public static final Patient GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withGender("male").build();

    // Manually added
    public static final Patient HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withGender("female").build();
    public static final Patient IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withGender("female").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Patient AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withGender(VALID_GENDER_AMY).withTags(VALID_TAG_FRIEND)
            .withRemark(VALID_REMARK_AMY).build();
    public static final Patient BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withGender(VALID_GENDER_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withRemark(VALID_REMARK_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Patient patient : getTypicalPersons()) {
            ab.addPerson(patient);
        }
        return ab;
    }

    public static List<Patient> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
