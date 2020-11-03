package team.baymax.logic.commands.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.logic.commands.CommandTestUtil.assertCommandSuccess;
import static team.baymax.testutil.appointment.TypicalAppointments.getTypicalAppointmentManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.modelmanagers.CalendarManager;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.userprefs.UserPrefs;

public class ListAppointmentCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new PatientManager(), getTypicalAppointmentManager(),
                new UserPrefs(), new CalendarManager());
        expectedModel = new ModelManager(new PatientManager(), model.getAppointmentManager(),
                new UserPrefs(), new CalendarManager());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListAppointmentCommand(), model,
                ListAppointmentCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {

    }

    @Test
    public void equals() {
        ListAppointmentCommand listAppointmentCommand = new ListAppointmentCommand();
        // null -> returns False
        assertFalse(listAppointmentCommand.equals(null));
        // different types -> returns False
        assertFalse(listAppointmentCommand.equals(1));
        // same type -> returns True
        assertTrue(listAppointmentCommand.equals(new ListAppointmentCommand()));
        // this -> returns True
        assertTrue(listAppointmentCommand.equals(listAppointmentCommand));
    }

}
