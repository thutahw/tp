package team.baymax.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.baymax.logic.commands.patient.PatientCommandTestUtil;
import team.baymax.model.listmanagers.PatientManager;
import team.baymax.model.patient.Patient;

/**
 * A utility class containing a list of {@code Patient} objects to be used in tests.
 */
public class TypicalPatients {

    public static final Patient ALICE = new PatientBuilder()
            .withNric("T1234567A")
            .withName("Alice Pauline")
            .withGender("F")
            .withPhone("94351253")
            .withTags("friends").build();

    // Manually added

    // Manually added - Patient's details found in {@code CommandTestUtil}
    public static final Patient BENSON = new PatientBuilder()
            .withNric("S1234567B")
            .withName("Benson Meier")
            .withGender("M").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Patient CARL = new PatientBuilder()
            .withNric("S8546464H")
            .withName("Carl Kurz").withPhone("95352563")
            .withGender("F").build();
    public static final Patient DANIEL = new PatientBuilder()
            .withNric("S7539514E")
            .withName("Daniel Meier").withPhone("87652533")
            .withGender("M").withTags("friends").build();
    public static final Patient ELLE = new PatientBuilder()
            .withNric("T0012564N")
            .withName("Elle Meyer").withPhone("9482224")
            .withGender("F").build();
    public static final Patient FIONA = new PatientBuilder()
            .withNric("S7744115E")
            .withName("Fiona Kunz").withPhone("9482427")
            .withGender("F").build();
    public static final Patient GEORGE = new PatientBuilder()
            .withNric("S0002546G")
            .withName("George Best").withPhone("9482442")
            .withGender("M").build();

    // Manually added
    public static final Patient HOON = new PatientBuilder()
            .withNric("S0312456A")
            .withName("Hoon Meier").withPhone("8482424")
            .withGender("F").build();
    public static final Patient IDA = new PatientBuilder()
            .withNric("T7894561D")
            .withName("Ida Mueller").withPhone("8482131")
            .withGender("F").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Patient AMY = new PatientBuilder().withNric(PatientCommandTestUtil.VALID_NRIC_AMY)
            .withName(PatientCommandTestUtil.VALID_NAME_AMY).withPhone(PatientCommandTestUtil.VALID_PHONE_AMY)
            .withGender(PatientCommandTestUtil.VALID_GENDER_AMY).withTags(PatientCommandTestUtil.VALID_TAG_FRIEND)
            .withRemark(PatientCommandTestUtil.VALID_REMARK_AMY).build();
    public static final Patient BOB = new PatientBuilder().withNric(PatientCommandTestUtil.VALID_NRIC_BOB)
            .withName(PatientCommandTestUtil.VALID_NAME_BOB).withPhone(PatientCommandTestUtil.VALID_PHONE_BOB)
            .withGender(PatientCommandTestUtil.VALID_GENDER_BOB)
            .withTags(PatientCommandTestUtil.VALID_TAG_HUSBAND, PatientCommandTestUtil.VALID_TAG_FRIEND)
            .withRemark(PatientCommandTestUtil.VALID_REMARK_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPatients() {} // prevents instantiation

    /**
     * Returns a {@code PatientManager} with all the typical patients.
     */
    public static PatientManager getTypicalPatientManager() {
        PatientManager ab = new PatientManager();
        for (Patient patient : getTypicalPatients()) {
            ab.addPatient(patient);
        }
        return ab;
    }

    public static List<Patient> getTypicalPatients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
