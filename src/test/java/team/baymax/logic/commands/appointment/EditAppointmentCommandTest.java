package team.baymax.logic.commands.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.logic.commands.CommandTestUtil.assertCommandSuccess;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.DESC_APPT1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.DESC_APPT2;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DATETIME_1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESCRIPTION_1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.assertAppointmentCommandFailure;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.showAppointmentAtIndex;
import static team.baymax.testutil.appointment.TypicalAppointmentIndexes.INDEX_FIRST_APPOINTMENT;
import static team.baymax.testutil.appointment.TypicalAppointmentIndexes.INDEX_SECOND_APPOINTMENT;
import static team.baymax.testutil.appointment.TypicalAppointments.getTypicalAppointmentManager;
import static team.baymax.testutil.patient.PatientUtil.VALID_TAG_DIABETIC;

import org.junit.jupiter.api.Test;

import team.baymax.commons.core.Messages;
import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.CommandTestUtil;
import team.baymax.logic.commands.general.ClearCommand;
import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentIdenticalPredicate;
import team.baymax.model.modelmanagers.AppointmentManager;
import team.baymax.model.modelmanagers.CalendarManager;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.userprefs.UserPrefs;
import team.baymax.testutil.appointment.AppointmentBuilder;
import team.baymax.testutil.appointment.EditAppointmentDescriptorBuilder;

public class EditAppointmentCommandTest {

    private Model model = new ModelManager(new PatientManager(), getTypicalAppointmentManager(),
            new UserPrefs(), new CalendarManager());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Appointment appointmentToEdit = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT.getZeroBased());
        Appointment editedAppointment = new AppointmentBuilder().withPatient(appointmentToEdit.getPatient())
                                        .withIsMissed(appointmentToEdit.checkIfMissed()).build();

        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(editedAppointment).build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, descriptor);

        String expectedMessage = String.format(EditAppointmentCommand.MESSAGE_EDIT_APPOINTMENT_SUCCESS,
                editedAppointment);

        Model expectedModel = new ModelManager(new PatientManager(),
                new AppointmentManager(model.getAppointmentManager()), new UserPrefs(), new CalendarManager());

        expectedModel.setAppointment(model.getFilteredAppointmentList().get(0), editedAppointment);
        expectedModel.updateFilteredAppointmentList(new AppointmentIdenticalPredicate(editedAppointment));

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastAppointment = Index.fromOneBased(model.getFilteredAppointmentList().size());
        Appointment lastAppointment = model.getFilteredAppointmentList().get(indexLastAppointment.getZeroBased());

        AppointmentBuilder appointmentInList = new AppointmentBuilder(lastAppointment);
        Appointment editedAppointment = appointmentInList.withDescription(VALID_DESCRIPTION_1)
                .withDateTime(VALID_DATETIME_1).withTags(VALID_TAG_DIABETIC).build();

        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_1)
                .withDateTime(VALID_DATETIME_1).withTags(VALID_TAG_DIABETIC).build();

        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(indexLastAppointment, descriptor);

        String expectedMessage = String.format(EditAppointmentCommand.MESSAGE_EDIT_APPOINTMENT_SUCCESS,
                editedAppointment);

        Model expectedModel = new ModelManager(new PatientManager(), model.getAppointmentManager(),
                new UserPrefs(), new CalendarManager());

        expectedModel.setAppointment(lastAppointment, editedAppointment);
        expectedModel.updateFilteredAppointmentList(new AppointmentIdenticalPredicate(editedAppointment));

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT,
                new EditAppointmentDescriptor());
        Appointment editedAppointment = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT.getZeroBased());

        String expectedMessage = String.format(EditAppointmentCommand.MESSAGE_EDIT_APPOINTMENT_SUCCESS,
                editedAppointment);

        Model expectedModel = new ModelManager(new PatientManager(), model.getAppointmentManager(),
                new UserPrefs(), new CalendarManager());

        expectedModel.updateFilteredAppointmentList(new AppointmentIdenticalPredicate(editedAppointment));

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        Appointment appointmentInFilteredList = model.getFilteredAppointmentList()
                .get(INDEX_FIRST_APPOINTMENT.getZeroBased());

        Appointment editedAppointment = new AppointmentBuilder(appointmentInFilteredList)
                .withDescription(VALID_DESCRIPTION_1).build();

        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_1).build();

        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT,
                descriptor);

        String expectedMessage = String.format(EditAppointmentCommand.MESSAGE_EDIT_APPOINTMENT_SUCCESS,
                editedAppointment);

        Model expectedModel = new ModelManager(new PatientManager(), model.getAppointmentManager(),
                new UserPrefs(), new CalendarManager());

        expectedModel.setAppointment(model.getFilteredAppointmentList().get(0), editedAppointment);
        expectedModel.updateFilteredAppointmentList(new AppointmentIdenticalPredicate(editedAppointment));

        CommandTestUtil.assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidAppointmentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAppointmentList().size() + 1);
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder().build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(outOfBoundIndex, descriptor);

        assertAppointmentCommandFailure(editAppointmentCommand, model,
                Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidAppointmentIndexFilteredList_failure() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);
        Index outOfBoundIndex = INDEX_SECOND_APPOINTMENT;

        // ensures that outOfBoundIndex is still in bounds of appointment book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAppointmentManager().getReadOnlyList().size());

        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(outOfBoundIndex,
                new EditAppointmentDescriptorBuilder().withDescription(VALID_DESCRIPTION_1).build());

        assertAppointmentCommandFailure(editAppointmentCommand, model,
                Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditAppointmentCommand standardCommand = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, DESC_APPT1);

        // same values -> returns true
        EditAppointmentDescriptor copyDescriptor = new EditAppointmentDescriptor(DESC_APPT1);
        EditAppointmentCommand commandWithSameValues = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT,
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns True
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns False
        assertFalse(standardCommand.equals(null));

        // different types -> returns False
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns False
        assertFalse(standardCommand.equals(new EditAppointmentCommand(INDEX_SECOND_APPOINTMENT, DESC_APPT1)));

        // different descriptor -> returns False
        assertFalse(standardCommand.equals(new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, DESC_APPT2)));
    }

}
