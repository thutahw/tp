package seedu.address.logic.commands.patient;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientManager;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.listmanagers.AppointmentManager;
import seedu.address.model.listmanagers.PatientManager;
import seedu.address.model.userprefs.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyModelManager_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyModelManager_success() {
        Model model = new ModelManager(getTypicalPatientManager(), new AppointmentManager(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalPatientManager(), new AppointmentManager(), new UserPrefs());
        expectedModel.setPatientManager(new PatientManager());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
