package team.baymax.logic.commands.appointment;

import static team.baymax.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.listmanagers.AppointmentManager;
import team.baymax.model.listmanagers.PatientManager;
import team.baymax.model.userprefs.UserPrefs;

public class ListAppointmentCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new PatientManager(), new AppointmentManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getPatientManager(), model.getAppointmentManager(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListAppointmentCommand(), model,
                ListAppointmentCommand.MESSAGE_SUCCESS, expectedModel);
    }

    // TODO
    @Test
    public void execute_listIsFiltered_showsEverything() {

    }

}
