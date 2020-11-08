package team.baymax.logic.commands.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.patient.PatientUtil.DESC_AMY;
import static team.baymax.testutil.patient.PatientUtil.DESC_BOB;
import static team.baymax.testutil.patient.PatientUtil.VALID_GENDER_BOB;
import static team.baymax.testutil.patient.PatientUtil.VALID_NAME_BOB;
import static team.baymax.testutil.patient.PatientUtil.VALID_PHONE_BOB;
import static team.baymax.testutil.patient.PatientUtil.VALID_REMARK_BOB;
import static team.baymax.testutil.patient.PatientUtil.VALID_TAG_DIABETIC;

import org.junit.jupiter.api.Test;

import team.baymax.testutil.patient.EditPatientDescriptorBuilder;

public class EditPatientDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns True
        EditPatientCommand.EditPatientDescriptor descriptorWithSameValues =
                new EditPatientCommand.EditPatientDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns True
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns False
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns False
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns False
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns False
        EditPatientCommand.EditPatientDescriptor editedAmy = new EditPatientDescriptorBuilder(DESC_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns False
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different gender -> returns False
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withGender(VALID_GENDER_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different remark -> returns False
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withRemark(VALID_REMARK_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditPatientDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_DIABETIC).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
