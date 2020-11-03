package team.baymax.logic.commands.patient;

import static team.baymax.testutil.patient.TypicalPatients.getTypicalPatientManager;

import org.junit.jupiter.api.Test;

import team.baymax.logic.commands.CommandTestUtil;
import team.baymax.logic.commands.general.ClearCommand;
import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.modelmanagers.AppointmentManager;
import team.baymax.model.modelmanagers.CalendarManager;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.userprefs.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyModelManager_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandTestUtil.assertCommandSuccess(new ClearCommand(),
                model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyModelManager_success() {
        Model model = new ModelManager(getTypicalPatientManager(), new AppointmentManager(), new UserPrefs(),
                new CalendarManager());
        Model expectedModel = new ModelManager(getTypicalPatientManager(), new AppointmentManager(), new UserPrefs(),
                new CalendarManager());
        expectedModel.setPatientManager(new PatientManager());

        CommandTestUtil.assertCommandSuccess(new ClearCommand(),
                model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
