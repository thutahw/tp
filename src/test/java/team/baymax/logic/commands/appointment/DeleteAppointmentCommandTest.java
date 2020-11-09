package team.baymax.logic.commands.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.assertAppointmentCommandFailure;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.showAppointmentAtIndex;
import static team.baymax.testutil.appointment.TypicalAppointmentIndexes.INDEX_FIRST_APPOINTMENT;
import static team.baymax.testutil.appointment.TypicalAppointmentIndexes.INDEX_SECOND_APPOINTMENT;
import static team.baymax.testutil.appointment.TypicalAppointments.getTypicalAppointmentManager;
import static team.baymax.testutil.datetime.TypicalDateTimes.DATETIME1;
import static team.baymax.testutil.patient.TypicalFirstNames.FIRST_NAME_ALICE;
import static team.baymax.testutil.patient.TypicalFirstNames.FIRST_NAME_GEORGE;
import static team.baymax.testutil.patient.TypicalFirstNames.FIRST_NAME_HANSON;
import static team.baymax.testutil.patient.TypicalPatients.getTypicalPatientManager;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import team.baymax.commons.core.Messages;
import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.CommandTestUtil;
import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.modelmanagers.CalendarManager;
import team.baymax.model.userprefs.UserPrefs;

public class DeleteAppointmentCommandTest {

    private Model model = new ModelManager(getTypicalPatientManager(), getTypicalAppointmentManager(),
            new UserPrefs(), new CalendarManager());
    private Model expectedModel = new ModelManager(model.getPatientManager(), model.getAppointmentManager(),
            new UserPrefs(), new CalendarManager());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Appointment appointmentToDelete = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(
                Optional.of(INDEX_FIRST_APPOINTMENT));

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                appointmentToDelete);

        expectedModel.deleteAppointment(appointmentToDelete);

        CommandTestUtil.assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAppointmentList().size() + 1);
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(
                Optional.of(outOfBoundIndex));

        assertAppointmentCommandFailure(deleteAppointmentCommand, model,
                Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);

        Appointment appointmentToDelete = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(
                Optional.of(INDEX_FIRST_APPOINTMENT));

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                appointmentToDelete);

        expectedModel.deleteAppointment(appointmentToDelete);
        showNoAppointment(expectedModel);

        CommandTestUtil.assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);

        Index outOfBoundIndex = INDEX_SECOND_APPOINTMENT;

        // ensures that outOfBoundIndex is still in bounds of appointment book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAppointmentManager().getReadOnlyList().size());
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(
                Optional.of(outOfBoundIndex));

        assertAppointmentCommandFailure(deleteAppointmentCommand, model,
                Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteAppointmentCommand deleteFirstAppointment = new DeleteAppointmentCommand(
                Optional.of(INDEX_FIRST_APPOINTMENT));
        DeleteAppointmentCommand deleteSecondAppointment = new DeleteAppointmentCommand(
                Optional.of(INDEX_SECOND_APPOINTMENT));

        // same object -> returns True
        assertTrue(deleteFirstAppointment.equals(deleteFirstAppointment));

        // same values -> returns True
        DeleteAppointmentCommand deleteFirstAppointmentCopy = new DeleteAppointmentCommand(
                Optional.of(INDEX_FIRST_APPOINTMENT));
        assertTrue(deleteFirstAppointment.equals(deleteFirstAppointmentCopy));

        // different types -> returns False
        assertFalse(deleteFirstAppointment.equals(1));

        // null -> returns False
        assertFalse(deleteFirstAppointment.equals(null));

        // different details -> returns False
        assertFalse(deleteFirstAppointment.equals(deleteSecondAppointment));
    }

    @Test
    public void execute_validDateTimeAndNameUnfilteredList_success() {
        Appointment appointmentChosen = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(
                Optional.of(appointmentChosen.getDateTime()), Optional.of(appointmentChosen.getPatient().getName()));

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                appointmentChosen);

        expectedModel.deleteAppointment(appointmentChosen);

        CommandTestUtil.assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidDateTimeAndNameUnfilteredList_throwsCommandException() {
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(
                Optional.of(DATETIME1), Optional.of(FIRST_NAME_HANSON));

        assertAppointmentCommandFailure(deleteAppointmentCommand, model,
                Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
    }

    @Test
    public void execute_validDateTimeAndNameFilteredList_success() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);

        Appointment appointmentChosen = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());

        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(
                Optional.of(appointmentChosen.getDateTime()), Optional.of(appointmentChosen.getPatient().getName()));

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                appointmentChosen);

        expectedModel.deleteAppointment(appointmentChosen);
        showNoAppointment(expectedModel);

        CommandTestUtil.assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidDateTimeAndNameFilteredList_throwsCommandException() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);
        Index outOfBoundIndex = INDEX_SECOND_APPOINTMENT;

        // ensures that outOfBoundIndex is still in bounds of appointment book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAppointmentManager().getReadOnlyList().size());
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(
                Optional.of(DATETIME1), Optional.of(FIRST_NAME_GEORGE));

        assertAppointmentCommandFailure(deleteAppointmentCommand, model,
                Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
    }

    @Test
    public void execute_allValidUnfilteredList_success() {
        Appointment appointmentToDelete = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(
                Optional.of(appointmentToDelete.getDateTime()),
                Optional.of(appointmentToDelete.getPatient().getName()));

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                appointmentToDelete);

        expectedModel.deleteAppointment(appointmentToDelete);

        CommandTestUtil.assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidDatetimeUnfilteredList_throwsCommandException() {
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(
                Optional.of(DATETIME1), Optional.of(FIRST_NAME_ALICE));

        assertAppointmentCommandFailure(deleteAppointmentCommand, model,
                Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        Appointment appointmentToDelete = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(
                Optional.of(appointmentToDelete.getDateTime()), Optional.of(FIRST_NAME_HANSON));

        assertAppointmentCommandFailure(deleteAppointmentCommand, model,
                Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoAppointment(Model model) {
        model.updateFilteredAppointmentList(p -> false);

        assertTrue(model.getFilteredAppointmentList().isEmpty());
    }

}
