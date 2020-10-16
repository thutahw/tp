package team.baymax.logic.commands.appointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AppointmentCommandTestUtil;
import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.userprefs.UserPrefs;

public class ListAppointmentCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
//        model = new ModelManager(getTypicalAppointmentBook(), new UserPrefs());
//        expectedModel = new ModelManager(model.getAppointmentBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
//        AppointmentCommandTestUtil.assertAppointmentCommandSuccess(new ListAppointmentCommand(), model,
//                ListAppointmentCommand.MESSAGE_SUCCESS, expectedModel);
    }

    // TODO
    @Test
    public void execute_listIsFiltered_showsEverything() {

    }

}
