package team.baymax.logic.commands.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.logic.commands.CommandTestUtil.assertCommandSuccess;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.assertPatientCommandFailure;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.showPatientAtIndex;
import static team.baymax.testutil.patient.PatientUtil.DESC_AMY;
import static team.baymax.testutil.patient.PatientUtil.DESC_BOB;
import static team.baymax.testutil.patient.PatientUtil.VALID_NAME_BOB;
import static team.baymax.testutil.patient.PatientUtil.VALID_PHONE_BOB;
import static team.baymax.testutil.patient.PatientUtil.VALID_TAG_DIABETIC;
import static team.baymax.testutil.patient.TypicalPatientIndexes.INDEX_FIRST_PATIENT;
import static team.baymax.testutil.patient.TypicalPatientIndexes.INDEX_SECOND_PATIENT;
import static team.baymax.testutil.patient.TypicalPatients.getTypicalPatientManager;

import org.junit.jupiter.api.Test;

import team.baymax.commons.core.Messages;
import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.general.ClearCommand;
import team.baymax.logic.commands.patient.EditPatientCommand.EditPatientDescriptor;
import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.modelmanagers.AppointmentManager;
import team.baymax.model.modelmanagers.CalendarManager;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.patient.Patient;
import team.baymax.model.patient.PatientIdenticalPredicate;
import team.baymax.model.userprefs.UserPrefs;
import team.baymax.testutil.patient.EditPatientDescriptorBuilder;
import team.baymax.testutil.patient.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditPatientCommandTest {

    private Model model = new ModelManager(getTypicalPatientManager(), new AppointmentManager(), new UserPrefs(),
            new CalendarManager());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Patient editedPatient = new PatientBuilder().build();
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(editedPatient).build();
        EditPatientCommand editPatientCommand = new EditPatientCommand(INDEX_FIRST_PATIENT, descriptor);

        String expectedMessage = String.format(EditPatientCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new PatientManager(model.getPatientManager()),
                new AppointmentManager(), new UserPrefs(), new CalendarManager());
        expectedModel.setPatient(model.getFilteredPatientList().get(0), editedPatient);
        expectedModel.updateFilteredPatientList(new PatientIdenticalPredicate(editedPatient));

        assertCommandSuccess(editPatientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPatient = Index.fromOneBased(model.getFilteredPatientList().size());
        Patient lastPatient = model.getFilteredPatientList().get(indexLastPatient.getZeroBased());

        PatientBuilder patientInList = new PatientBuilder(lastPatient);
        Patient editedPatient = patientInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_DIABETIC).build();

        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_DIABETIC).build();
        EditPatientCommand editPatientCommand = new EditPatientCommand(indexLastPatient, descriptor);

        String expectedMessage = String.format(EditPatientCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new PatientManager(model.getPatientManager()),
                new AppointmentManager(), new UserPrefs(), new CalendarManager());
        expectedModel.setPatient(lastPatient, editedPatient);
        expectedModel.updateFilteredPatientList(new PatientIdenticalPredicate(editedPatient));

        assertCommandSuccess(editPatientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditPatientCommand editPatientCommand = new EditPatientCommand(INDEX_FIRST_PATIENT,
                new EditPatientCommand.EditPatientDescriptor());
        Patient editedPatient = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());

        String expectedMessage = String.format(EditPatientCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new PatientManager(model.getPatientManager()),
                new AppointmentManager(), new UserPrefs(), new CalendarManager());
        expectedModel.updateFilteredPatientList(new PatientIdenticalPredicate(editedPatient));

        assertCommandSuccess(editPatientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);

        Patient patientInFilteredList = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        Patient editedPatient = new PatientBuilder(patientInFilteredList).withName(VALID_NAME_BOB).build();
        EditPatientCommand editPatientCommand = new EditPatientCommand(INDEX_FIRST_PATIENT,
                new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditPatientCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new PatientManager(model.getPatientManager()),
                new AppointmentManager(), new UserPrefs(), new CalendarManager());
        expectedModel.setPatient(model.getFilteredPatientList().get(0), editedPatient);
        expectedModel.updateFilteredPatientList(new PatientIdenticalPredicate(editedPatient));

        assertCommandSuccess(editPatientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePatientUnfilteredList_failure() {
        Patient firstPatient = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(firstPatient).build();
        EditPatientCommand editPatientCommand = new EditPatientCommand(INDEX_SECOND_PATIENT, descriptor);

        assertPatientCommandFailure(editPatientCommand, model, EditPatientCommand.MESSAGE_DUPLICATE_PATIENT);
    }

    @Test
    public void execute_duplicatePatientFilteredList_failure() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);

        // edit patient in filtered list into a duplicate in appointment book
        Patient patientInList = model.getPatientManager().getReadOnlyList().get(INDEX_SECOND_PATIENT.getZeroBased());
        EditPatientCommand editPatientCommand = new EditPatientCommand(INDEX_FIRST_PATIENT,
                new EditPatientDescriptorBuilder(patientInList).build());

        assertPatientCommandFailure(editPatientCommand, model, EditPatientCommand.MESSAGE_DUPLICATE_PATIENT);
    }

    @Test
    public void execute_invalidPatientIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditPatientCommand editPatientCommand = new EditPatientCommand(outOfBoundIndex, descriptor);

        assertPatientCommandFailure(editPatientCommand, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of appointment book
     */
    @Test
    public void execute_invalidPatientIndexFilteredList_failure() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);
        Index outOfBoundIndex = INDEX_SECOND_PATIENT;
        // ensures that outOfBoundIndex is still in bounds of appointment book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPatientManager().getReadOnlyList().size());

        EditPatientCommand editPatientCommand = new EditPatientCommand(outOfBoundIndex,
                new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertPatientCommandFailure(editPatientCommand, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditPatientCommand standardCommand = new EditPatientCommand(INDEX_FIRST_PATIENT, DESC_AMY);

        // same values -> returns True
        EditPatientDescriptor copyDescriptor = new EditPatientCommand.EditPatientDescriptor(DESC_AMY);
        EditPatientCommand commandWithSameValues = new EditPatientCommand(INDEX_FIRST_PATIENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns True
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns False
        assertFalse(standardCommand.equals(null));

        // different types -> returns False
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns False
        assertFalse(standardCommand.equals(new EditPatientCommand(INDEX_SECOND_PATIENT, DESC_AMY)));

        // different descriptor -> returns False
        assertFalse(standardCommand.equals(new EditPatientCommand(INDEX_FIRST_PATIENT, DESC_BOB)));
    }

}
