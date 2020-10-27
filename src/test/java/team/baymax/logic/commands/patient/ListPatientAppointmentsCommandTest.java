package team.baymax.logic.commands.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.assertPatientCommandFailure;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.showPatientAtIndex;
import static team.baymax.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static team.baymax.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static team.baymax.testutil.TypicalPatients.getTypicalPatientManager;

import org.junit.jupiter.api.Test;

import team.baymax.commons.core.Messages;
import team.baymax.commons.core.index.Index;
import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.listmanagers.AppointmentManager;
import team.baymax.model.patient.Patient;
import team.baymax.model.patient.PatientHasAppointmentPredicate;
import team.baymax.model.userprefs.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code ListPatientAppointmentsCommand}.
 */
public class ListPatientAppointmentsCommandTest {

    private Model model = new ModelManager(getTypicalPatientManager(), new AppointmentManager(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Patient patientChosen = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        ListPatientAppointmentsCommand listPatientAppointmentsCommand =
                new ListPatientAppointmentsCommand(INDEX_FIRST_PATIENT);

        String expectedMessage = String.format(ListPatientAppointmentsCommand.MESSAGE_SUCCESS, patientChosen);

        ModelManager expectedModel = new ModelManager(model.getPatientManager(),
                new AppointmentManager(), new UserPrefs());
        expectedModel.updateFilteredAppointmentList(new PatientHasAppointmentPredicate(patientChosen));

        PatientCommandTestUtil.assertPatientCommandSuccess(listPatientAppointmentsCommand,
                model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        ListPatientAppointmentsCommand listPatientAppointmentsCommand =
                new ListPatientAppointmentsCommand(outOfBoundIndex);

        assertPatientCommandFailure(listPatientAppointmentsCommand, model,
                Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);

        Index outOfBoundIndex = INDEX_SECOND_PATIENT;
        // ensures that outOfBoundIndex is still in bounds of appointment book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPatientManager().getReadOnlyList().size());

        ListPatientAppointmentsCommand listPatientAppointmentsCommand =
                new ListPatientAppointmentsCommand(outOfBoundIndex);

        assertPatientCommandFailure(listPatientAppointmentsCommand, model,
                Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ListPatientAppointmentsCommand listFirstCommand =
                new ListPatientAppointmentsCommand(INDEX_FIRST_PATIENT);
        ListPatientAppointmentsCommand listSecondCommand =
                new ListPatientAppointmentsCommand(INDEX_SECOND_PATIENT);

        // same object -> returns True
        assertTrue(listFirstCommand.equals(listFirstCommand));

        // same values -> returns True
        ListPatientAppointmentsCommand listFirstCommandCopy =
                new ListPatientAppointmentsCommand(INDEX_FIRST_PATIENT);
        assertTrue(listFirstCommand.equals(listFirstCommandCopy));

        // different types -> returns False
        assertFalse(listFirstCommand.equals(1));

        // null -> returns False
        assertFalse(listFirstCommand.equals(null));

        // different patient -> returns False
        assertFalse(listFirstCommand.equals(listSecondCommand));
    }
}
