package team.baymax.logic.commands.appointment;

import static team.baymax.testutil.TypicalAppointments.getTypicalAppointmentManager;
import static team.baymax.testutil.TypicalPatients.getTypicalPatientManager;

import org.junit.jupiter.api.Test;

import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.userprefs.UserPrefs;

public class EditAppointmentCommandTest {

    private Model model = new ModelManager(getTypicalPatientManager(), getTypicalAppointmentManager(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        // TODO (with AppointmentBuilder)
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        // TODO (with AppointmentBuilder)
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        // TODO
    }

    @Test
    public void execute_filteredList_success() {
        // TODO
    }

    @Test
    public void execute_duplicateAppointmentUnfilteredList_failure() {
        // TODO
    }

    @Test
    public void execute_duplicateAppointmentFilteredList_failure() {
        // TODO
    }

    @Test
    public void execute_invalidAppointmentIndexUnfilteredList_failure() {
        // TODO
    }

    @Test
    public void execute_invalidAppointmentIndexFilteredList_failure() {
        // TODO
    }

    @Test
    public void equals() {
        // TODO (with appointment builder)
    }

}
