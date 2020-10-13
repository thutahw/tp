package seedu.address.logic.commands.patient;

<<<<<<< HEAD
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientManager;
=======
import static seedu.address.logic.commands.patient.PatientCommandTestUtil.assertPatientCommandFailure;
import static seedu.address.testutil.TypicalPatients.getTypicalAppointmentBook;
>>>>>>> ba12137bef33d2a8d2ac76085dc45493a22cb1c9

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.listmanagers.AppointmentManager;
import seedu.address.model.patient.Patient;
import seedu.address.model.userprefs.UserPrefs;
import seedu.address.testutil.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddPatientCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPatientManager(), new AppointmentManager(), new UserPrefs());
    }

    @Test
    public void execute_newPatient_success() {
        Patient validPatient = new PatientBuilder().build();

        Model expectedModel = new ModelManager(model.getPatientManager(), new AppointmentManager(), new UserPrefs());
        expectedModel.addPatient(validPatient);

        PatientCommandTestUtil.assertPatientCommandSuccess(new AddPatientCommand(validPatient), model,
                String.format(AddPatientCommand.MESSAGE_SUCCESS, validPatient), expectedModel);
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() {
<<<<<<< HEAD
        Patient patientInList = model.getPatientManager().getReadOnlyList().get(0);
        assertCommandFailure(new AddPatientCommand(patientInList), model, AddPatientCommand.MESSAGE_DUPLICATE_PATIENT);
=======
        Patient patientInList = model.getAppointmentBook().getPatientList().get(0);
        assertPatientCommandFailure(new AddPatientCommand(patientInList),
                model, AddPatientCommand.MESSAGE_DUPLICATE_PATIENT);
>>>>>>> ba12137bef33d2a8d2ac76085dc45493a22cb1c9
    }

}
