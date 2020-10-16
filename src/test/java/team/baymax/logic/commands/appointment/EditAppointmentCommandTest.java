package team.baymax.logic.commands.appointment;

import org.junit.jupiter.api.Test;

import team.baymax.model.Model;
import team.baymax.model.ModelManager;

public class EditAppointmentCommandTest {

    private Model model = new ModelManager();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        //TODO (with AppointmentBuilder)
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        //TODO (with AppointmentBuilder)
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
//        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_PATIENT,
//                new EditAppointmentDescriptor());
//        Appointment editedAppointment = model.getFilteredAppointmentList().get(INDEX_FIRST_PATIENT.getZeroBased());
//
//        String expectedMessage = String.format(EditAppointmentCommand.MESSAGE_EDIT_APPOINTMENT_SUCCESS, editedAppointment);
//
//        Model expectedModel = new ModelManager(new AppointmentBook(model.getAppointmentBook()), new UserPrefs());
//
//        AppointmentCommandTestUtil.assertAppointmentCommandSuccess(editAppointmentCommand, model,
//                expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {

    }

    @Test
    public void execute_duplicateAppointmentUnfilteredList_failure() {

    }

    @Test
    public void execute_duplicateAppointmentFilteredList_failure() {

    }

    @Test
    public void execute_invalidAppointmentIndexUnfilteredList_failure() {

    }

    @Test
    public void execute_invalidAppointmentIndexFilteredList_failure() {

    }

    @Test
    public void equals() {
        // TODO (with appointment builder)
    }

}
