package team.baymax.testutil.patient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import team.baymax.model.modelmanagers.PatientManager;
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
            .withRemark("remark Alice")
            .withTags("tag1").build();

    // Manually added - Patient's details found in {@code CommandTestUtil}
    public static final Patient BENSON = new PatientBuilder()
            .withNric("S1234567B")
            .withName("Benson Meier")
            .withGender("M")
            .withPhone("98765432")
            .withTags("tag2", "tag3")
            .withRemark("remark Benson")
            .build();
    public static final Patient CARL = new PatientBuilder()
            .withNric("S8546464H")
            .withName("Carl Kurz")
            .withPhone("95352563")
            .withGender("F")
            .withRemark("remark Carl")
            .build();
    public static final Patient DANIEL = new PatientBuilder()
            .withNric("S7539514E")
            .withName("Daniel Meier")
            .withPhone("87652533")
            .withGender("M")
            .withTags("tag1")
            .withRemark("remark Daniel")
            .build();
    public static final Patient ELLE = new PatientBuilder()
            .withNric("T0012564N")
            .withName("Elle Meyer")
            .withPhone("9482224")
            .withGender("F")
            .withRemark("remark Elle")
            .build();
    public static final Patient FIONA = new PatientBuilder()
            .withNric("S7744115E")
            .withName("Fiona Kunz")
            .withPhone("9482427")
            .withGender("F")
            .withRemark("remark Fiona")
            .build();
    public static final Patient GEORGE = new PatientBuilder()
            .withNric("S0002546G")
            .withName("George Best")
            .withPhone("9482442")
            .withGender("M")
            .withRemark("remark George")
            .build();

    // Manually added
    public static final Patient HOON = new PatientBuilder()
            .withNric("S0312456A")
            .withName("Hoon Meier")
            .withPhone("8482424")
            .withGender("F")
            .build();

    public static final Patient IDA = new PatientBuilder()
            .withNric("T7894561D")
            .withName("Ida Mueller")
            .withPhone("8482131")
            .withGender("F")
            .build();

    // Manually added - Patient's details found in {@code CommandTestUtil}
    public static final Patient AMY = new PatientBuilder().withNric(PatientUtil.VALID_NRIC_AMY)
            .withName(PatientUtil.VALID_NAME_AMY).withPhone(PatientUtil.VALID_PHONE_AMY)
            .withGender(PatientUtil.VALID_GENDER_AMY).withTags(PatientUtil.VALID_TAG_LTP)
            .withRemark(PatientUtil.VALID_REMARK_AMY).build();

    public static final Patient BOB = new PatientBuilder().withNric(PatientUtil.VALID_NRIC_BOB)
            .withName(PatientUtil.VALID_NAME_BOB).withPhone(PatientUtil.VALID_PHONE_BOB)
            .withGender(PatientUtil.VALID_GENDER_BOB)
            .withTags(PatientUtil.VALID_TAG_DIABETIC, PatientUtil.VALID_TAG_LTP)
            .withRemark(PatientUtil.VALID_REMARK_BOB).build();

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
