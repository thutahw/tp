package team.baymax.logic.commands.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.assertPatientCommandFailure;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.showPatientAtIndex;
import static team.baymax.testutil.patient.TypicalPatientIndexes.INDEX_FIRST_PATIENT;
import static team.baymax.testutil.patient.TypicalPatientIndexes.INDEX_SECOND_PATIENT;
import static team.baymax.testutil.patient.TypicalPatients.getTypicalPatientManager;

import org.junit.jupiter.api.Test;

import team.baymax.commons.core.Messages;
import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.CommandTestUtil;
import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.modelmanagers.AppointmentManager;
import team.baymax.model.modelmanagers.CalendarManager;
import team.baymax.model.patient.Patient;
import team.baymax.model.patient.PatientHasAppointmentPredicate;
import team.baymax.model.userprefs.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ListPatientAppointmentsCommand}.
 */
public class ListPatientAppointmentsCommandTest {

    private Model model = new ModelManager(getTypicalPatientManager(), new AppointmentManager(),
            new UserPrefs(), new CalendarManager());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Patient patientChosen = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        ListPatientAppointmentsCommand listPatientAppointmentsCommand =
                new ListPatientAppointmentsCommand(INDEX_FIRST_PATIENT);

        String expectedMessage = String.format(ListPatientAppointmentsCommand.MESSAGE_SUCCESS, patientChosen);

        ModelManager expectedModel = new ModelManager(model.getPatientManager(),
                new AppointmentManager(), new UserPrefs(), new CalendarManager());
        expectedModel.updateFilteredAppointmentList(new PatientHasAppointmentPredicate(patientChosen));

        CommandTestUtil.assertCommandSuccess(listPatientAppointmentsCommand,
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
        ListPatientAppointmentsCommand firstIndexCommand =
                new ListPatientAppointmentsCommand(INDEX_FIRST_PATIENT);
        ListPatientAppointmentsCommand secondIndexCommand =
                new ListPatientAppointmentsCommand(INDEX_SECOND_PATIENT);

        // same object -> returns True
        assertTrue(firstIndexCommand.equals(firstIndexCommand));

        // same values -> returns True
        ListPatientAppointmentsCommand firstIndexCommandCopy =
                new ListPatientAppointmentsCommand(INDEX_FIRST_PATIENT);
        assertTrue(firstIndexCommand.equals(firstIndexCommandCopy));

        // different types -> returns False
        assertFalse(firstIndexCommand.equals(1));

        // null -> returns False
        assertFalse(firstIndexCommand.equals(null));

        // different patient -> returns False
        assertFalse(firstIndexCommand.equals(secondIndexCommand));
    }
}
