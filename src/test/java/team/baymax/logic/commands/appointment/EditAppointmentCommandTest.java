package team.baymax.logic.commands.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.DESC_APPT1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.DESC_APPT2;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DATETIME_1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESCRIPTION_1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.assertAppointmentCommandFailure;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_TAG_DIABETIC;
import static team.baymax.testutil.TypicalAppointments.getTypicalAppointmentManager;
import static team.baymax.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static team.baymax.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static team.baymax.testutil.TypicalIndexes.INDEX_SECOND_APPOINTMENT;
import static team.baymax.testutil.TypicalPatients.getTypicalPatientManager;

import org.junit.jupiter.api.Test;

import team.baymax.commons.core.Messages;
import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.ClearCommand;
import team.baymax.logic.commands.CommandTestUtil;
import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.listmanagers.PatientManager;
import team.baymax.model.userprefs.UserPrefs;
import team.baymax.testutil.AppointmentBuilder;
import team.baymax.testutil.EditAppointmentDescriptorBuilder;

public class EditAppointmentCommandTest {

    private Model model = new ModelManager(getTypicalPatientManager(), getTypicalAppointmentManager(), new UserPrefs());

    // TODO (failed)
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
//        model.addAppointment(APPT6);
        Appointment editedAppointment = new AppointmentBuilder().build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(editedAppointment).build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, descriptor);

        String expectedMessage = String.format(EditAppointmentCommand.MESSAGE_EDIT_APPOINTMENT_SUCCESS, editedAppointment);

        Model expectedModel = new ModelManager(new PatientManager(model.getPatientManager()),
                model.getAppointmentManager(), new UserPrefs());
        expectedModel.setAppointment(model.getFilteredAppointmentList().get(0), editedAppointment);

        CommandTestUtil.assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastAppointment = Index.fromOneBased(model.getFilteredAppointmentList().size());
        Appointment lastAppointment = model.getFilteredAppointmentList().get(indexLastAppointment.getZeroBased());

        AppointmentBuilder appointmentInList = new AppointmentBuilder(lastAppointment);
        Appointment editedAppointment = appointmentInList.withDescription(VALID_DESCRIPTION_1).withTime(VALID_DATETIME_1)
                .withTags(VALID_TAG_DIABETIC).build();

        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_1)
                .withDateTime(VALID_DATETIME_1).withTags(VALID_TAG_DIABETIC).build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(indexLastAppointment, descriptor);

        String expectedMessage = String.format(EditAppointmentCommand.MESSAGE_EDIT_APPOINTMENT_SUCCESS, editedAppointment);

        Model expectedModel = new ModelManager(new PatientManager(model.getPatientManager()),
                model.getAppointmentManager(), new UserPrefs());
        expectedModel.setAppointment(lastAppointment, editedAppointment);

        CommandTestUtil.assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        // TODO
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT,
                new EditAppointmentDescriptor());
        Appointment editedAppointment = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT.getZeroBased());

        String expectedMessage = String.format(EditAppointmentCommand.MESSAGE_EDIT_APPOINTMENT_SUCCESS, editedAppointment);

        Model expectedModel = new ModelManager(new PatientManager(model.getPatientManager()),
                model.getAppointmentManager(), new UserPrefs());

        CommandTestUtil.assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        // TODO (needs showAppointmentAtIndex() method)
    }

    @Test
    public void execute_duplicateAppointmentUnfilteredList_failure() {
        Appointment firstAppointment = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT.getZeroBased());
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(firstAppointment).build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(INDEX_SECOND_APPOINTMENT, descriptor);

        assertAppointmentCommandFailure(editAppointmentCommand, model, EditAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT);
    }

    @Test
    public void execute_duplicateAppointmentFilteredList_failure() {
        // TODO (needs showAppointmentAtIndex() method)
    }

    @Test
    public void execute_invalidAppointmentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAppointmentList().size() + 1);
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder().build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(outOfBoundIndex, descriptor);

        assertAppointmentCommandFailure(editAppointmentCommand, model, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidAppointmentIndexFilteredList_failure() {
        // TODO (needs showAppointmentAtIndex() method)
//        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);
//        Index outOfBoundIndex = INDEX_SECOND_APPOINTMENT;
//        // ensures that outOfBoundIndex is still in bounds of address book list
//        assertTrue(outOfBoundIndex.getZeroBased() < model.getAppointmentManager().getReadOnlyList().size());
//
//        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(outOfBoundIndex,
//                new EditAppointmentDescriptorBuilder().build());
//
//        assertAppointmentCommandFailure(editAppointmentCommand, model, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditAppointmentCommand standardCommand = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, DESC_APPT1);

        // same values -> returns true
        EditAppointmentDescriptor copyDescriptor = new EditAppointmentDescriptor(DESC_APPT1);
        EditAppointmentCommand commandWithSameValues = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditAppointmentCommand(INDEX_SECOND_APPOINTMENT, DESC_APPT1)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditAppointmentCommand(INDEX_FIRST_PATIENT, DESC_APPT2)));
    }

}
