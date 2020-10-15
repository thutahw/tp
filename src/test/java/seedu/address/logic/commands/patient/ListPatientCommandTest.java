package seedu.address.logic.commands.patient;

import static seedu.address.logic.commands.patient.PatientCommandTestUtil.showPatientAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.listmanagers.AppointmentManager;
import seedu.address.model.userprefs.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListPatientCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPatientManager(), new AppointmentManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getPatientManager(), new AppointmentManager(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        PatientCommandTestUtil.assertPatientCommandSuccess(new ListPatientCommand(), model,
                ListPatientCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);
        PatientCommandTestUtil.assertPatientCommandSuccess(new ListPatientCommand(), model,
                ListPatientCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
