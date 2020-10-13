package seedu.address.testutil;

import static seedu.address.logic.commands.patient.PatientCommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.patient.PatientCommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.patient.PatientCommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.patient.PatientCommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.patient.PatientCommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.patient.PatientCommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.patient.PatientCommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.patient.PatientCommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.patient.PatientCommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.patient.PatientCommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AppointmentBook;
import seedu.address.model.patient.Patient;

/**
 * A utility class containing a list of {@code Patient} objects to be used in tests.
 */
public class TypicalPatients {

    public static final Patient ALICE = new PatientBuilder().withName("Alice Pauline")
            .withGender("F")
            .withPhone("94351253")
            .withTags("friends").build();

    // Manually added

    // Manually added - Patient's details found in {@code CommandTestUtil}
    public static final Patient BENSON = new PatientBuilder().withName("Benson Meier")
            .withGender("M").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Patient CARL = new PatientBuilder().withName("Carl Kurz").withPhone("95352563")
            .withGender("F").build();
    public static final Patient DANIEL = new PatientBuilder().withName("Daniel Meier").withPhone("87652533")
            .withGender("M").withTags("friends").build();
    public static final Patient ELLE = new PatientBuilder().withName("Elle Meyer").withPhone("9482224")
            .withGender("F").build();
    public static final Patient FIONA = new PatientBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withGender("F").build();
    public static final Patient GEORGE = new PatientBuilder().withName("George Best").withPhone("9482442")
            .withGender("M").build();

    // Manually added
    public static final Patient HOON = new PatientBuilder().withName("Hoon Meier").withPhone("8482424")
            .withGender("F").build();
    public static final Patient IDA = new PatientBuilder().withName("Ida Mueller").withPhone("8482131")
            .withGender("F").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Patient AMY = new PatientBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withGender(VALID_GENDER_AMY).withTags(VALID_TAG_FRIEND)
            .withRemark(VALID_REMARK_AMY).build();
    public static final Patient BOB = new PatientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withGender(VALID_GENDER_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withRemark(VALID_REMARK_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPatients() {} // prevents instantiation

    /**
     * Returns an {@code AppointmentBook} with all the typical patients.
     */
    public static AppointmentBook getTypicalAppointmentBook() {
        AppointmentBook ab = new AppointmentBook();
        for (Patient patient : getTypicalPatients()) {
            ab.addPatient(patient);
        }
        return ab;
    }

    public static List<Patient> getTypicalPatients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
