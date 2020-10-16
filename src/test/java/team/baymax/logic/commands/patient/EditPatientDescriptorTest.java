package team.baymax.logic.commands.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.DESC_AMY;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.DESC_BOB;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_GENDER_BOB;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_NAME_BOB;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_PHONE_BOB;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_REMARK_BOB;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import team.baymax.testutil.EditPatientDescriptorBuilder;

public class EditPatientDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPatientCommand.EditPatientDescriptor descriptorWithSameValues =
                new EditPatientCommand.EditPatientDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditPatientCommand.EditPatientDescriptor editedAmy = new EditPatientDescriptorBuilder(DESC_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different gender -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withGender(VALID_GENDER_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different remark -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withRemark(VALID_REMARK_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
