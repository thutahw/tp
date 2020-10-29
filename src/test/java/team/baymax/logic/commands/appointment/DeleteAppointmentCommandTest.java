package team.baymax.logic.commands.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.assertAppointmentCommandFailure;
import static team.baymax.testutil.TypicalAppointments.getTypicalAppointmentManager;
import static team.baymax.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static team.baymax.testutil.TypicalIndexes.INDEX_SECOND_APPOINTMENT;
import static team.baymax.testutil.TypicalIndexes.INDEX_THIRD_APPOINTMENT;
import static team.baymax.testutil.TypicalPatients.getTypicalPatientManager;

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

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Appointment appointmentToDelete = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                appointmentToDelete);

        ModelManager expectedModel = new ModelManager(model.getPatientManager(),
                model.getAppointmentManager(), new UserPrefs(), new CalendarManager());
        expectedModel.deleteAppointment(appointmentToDelete);

        CommandTestUtil.assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Appointment appointmentToDelete = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAppointmentList().size() + 1);
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(outOfBoundIndex);

        assertAppointmentCommandFailure(deleteAppointmentCommand, model,
                Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {

        Appointment appointmentToDelete = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                appointmentToDelete);

        Model expectedModel = new ModelManager(model.getPatientManager(), model.getAppointmentManager(),
                new UserPrefs(), new CalendarManager());
        expectedModel.deleteAppointment(appointmentToDelete);
        showNoAppointment(expectedModel);

        CommandTestUtil.assertCommandSuccess(deleteAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {

        Index outOfBoundIndex = INDEX_THIRD_APPOINTMENT;
        // ensures that outOfBoundIndex is still in bounds of appointment book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAppointmentManager().getReadOnlyList().size());
        Appointment appointmentToDelete = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(outOfBoundIndex);

        assertAppointmentCommandFailure(deleteAppointmentCommand, model,
                Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Appointment appointmentToDelete = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT
                .getZeroBased());

        DeleteAppointmentCommand deleteFirstCommand = new DeleteAppointmentCommand(INDEX_FIRST_APPOINTMENT);
        DeleteAppointmentCommand deleteSecondCommand = new DeleteAppointmentCommand(INDEX_SECOND_APPOINTMENT);


        // same object -> returns True
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns True
        DeleteAppointmentCommand deleteFirstCommandCopy = new DeleteAppointmentCommand(INDEX_FIRST_APPOINTMENT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns False
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns False
        assertFalse(deleteFirstCommand.equals(null));

        // different details -> returns False
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoAppointment(Model model) {
        model.updateFilteredAppointmentList(p -> false);

        assertTrue(model.getFilteredAppointmentList().isEmpty());
    }

}
