package seedu.address.logic.commands.patient;

import static seedu.address.logic.commands.PatientCommandTestUtil.assertPatientCommandFailure;
import static seedu.address.logic.commands.PatientCommandTestUtil.assertPatientCommandSuccess;
import static seedu.address.testutil.TypicalPatients.getTypicalAppointmentBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PatientCommandTestUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddPatientCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAppointmentBook(), new UserPrefs());
    }

    @Test
    public void execute_newPatient_success() {
        Patient validPatient = new PatientBuilder().build();

        Model expectedModel = new ModelManager(model.getAppointmentBook(), new UserPrefs());
        expectedModel.addPatient(validPatient);

        PatientCommandTestUtil.assertPatientCommandSuccess(new AddPatientCommand(validPatient), model,
                String.format(AddPatientCommand.MESSAGE_SUCCESS, validPatient), expectedModel);
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() {
        Patient patientInList = model.getAppointmentBook().getPatientList().get(0);
        assertPatientCommandFailure(new AddPatientCommand(patientInList), model, AddPatientCommand.MESSAGE_DUPLICATE_PATIENT);
    }

}
