package team.baymax.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.Assert.assertThrows;
import static team.baymax.testutil.patient.TypicalPatients.ALICE;
import static team.baymax.testutil.patient.TypicalPatients.BOB;

import org.junit.jupiter.api.Test;

import team.baymax.logic.commands.patient.PatientCommandTestUtil;
import team.baymax.testutil.patient.PatientBuilder;

public class PatientTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Patient patient = new PatientBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> patient.getTags().remove(0));
    }

    @Test
    public void isSamePatient() {
        // same object -> returns true
        assertTrue(ALICE.isSame(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSame(null));

        // different phone and gender -> returns true
        Patient editedAlice = new PatientBuilder(ALICE).withPhone(PatientCommandTestUtil.VALID_PHONE_BOB)
                .withGender(PatientCommandTestUtil.VALID_GENDER_BOB).build();
        assertTrue(ALICE.isSame(editedAlice));

        // different name -> returns true
        editedAlice = new PatientBuilder(ALICE).withName(PatientCommandTestUtil.VALID_NAME_BOB).build();
        assertTrue(ALICE.isSame(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PatientBuilder(ALICE).withGender(PatientCommandTestUtil.VALID_GENDER_BOB)
                .withTags(PatientCommandTestUtil.VALID_TAG_DIABETIC).build();
        assertTrue(ALICE.isSame(editedAlice));

        // same name, same gender, different attributes -> returns true
        editedAlice = new PatientBuilder(ALICE).withPhone(PatientCommandTestUtil.VALID_PHONE_BOB)
                .withTags(PatientCommandTestUtil.VALID_TAG_DIABETIC).build();
        assertTrue(ALICE.isSame(editedAlice));

        // same name, same phone, same gender, different attributes -> returns true
        editedAlice = new PatientBuilder(ALICE).withTags(PatientCommandTestUtil.VALID_TAG_DIABETIC).build();
        assertTrue(ALICE.isSame(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Patient aliceCopy = new PatientBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Patient editedAlice = new PatientBuilder(ALICE).withName(PatientCommandTestUtil.VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PatientBuilder(ALICE).withPhone(PatientCommandTestUtil.VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different gender -> returns false
        editedAlice = new PatientBuilder(ALICE).withGender(PatientCommandTestUtil.VALID_GENDER_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PatientBuilder(ALICE).withTags(PatientCommandTestUtil.VALID_TAG_DIABETIC).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
