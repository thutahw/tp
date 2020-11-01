package team.baymax.logic.commands.appointment;

import static team.baymax.testutil.appointment.TypicalAppointments.getTypicalAppointmentManager;
import static team.baymax.testutil.patient.TypicalPatients.getTypicalPatientManager;

import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.modelmanagers.CalendarManager;
import team.baymax.model.userprefs.UserPrefs;

public class MarkAppointmentDoneCommandTest {

    private Model model = new ModelManager(getTypicalPatientManager(), getTypicalAppointmentManager(),
            new UserPrefs(), new CalendarManager());

}
