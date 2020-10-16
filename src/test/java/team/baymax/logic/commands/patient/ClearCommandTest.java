package team.baymax.logic.commands.patient;

import static team.baymax.testutil.TypicalPatients.getTypicalPatientManager;

import org.junit.jupiter.api.Test;

import team.baymax.logic.commands.ClearCommand;
import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.listmanagers.AppointmentManager;
import team.baymax.model.listmanagers.PatientManager;
import team.baymax.model.userprefs.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyModelManager_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        PatientCommandTestUtil.assertPatientCommandSuccess(new ClearCommand(),
                model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyModelManager_success() {
        Model model = new ModelManager(getTypicalPatientManager(), new AppointmentManager(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalPatientManager(), new AppointmentManager(), new UserPrefs());
        expectedModel.setPatientManager(new PatientManager());

        PatientCommandTestUtil.assertPatientCommandSuccess(new ClearCommand(),
                model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
