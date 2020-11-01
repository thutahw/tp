package team.baymax.logic.commands.patient;

import static team.baymax.logic.commands.CommandTestUtil.assertCommandSuccess;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.assertPatientCommandFailure;
import static team.baymax.testutil.patient.TypicalPatients.getTypicalPatientManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.modelmanagers.AppointmentManager;
import team.baymax.model.modelmanagers.CalendarManager;
import team.baymax.model.patient.Patient;
import team.baymax.model.patient.PatientIdenticalPredicate;
import team.baymax.model.userprefs.UserPrefs;
import team.baymax.testutil.patient.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddPatientCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPatientManager(), new AppointmentManager(), new UserPrefs(),
                new CalendarManager());
    }

    @Test
    public void execute_newPatient_success() {
        Patient validPatient = new PatientBuilder().build();

        String expectedMessage = String.format(AddPatientCommand.MESSAGE_SUCCESS, validPatient);

        Model expectedModel = new ModelManager(model.getPatientManager(), new AppointmentManager(), new UserPrefs(),
                new CalendarManager());
        expectedModel.addPatient(validPatient);
        expectedModel.updateFilteredPatientList(new PatientIdenticalPredicate(validPatient));

        assertCommandSuccess(new AddPatientCommand(validPatient), model,
                expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() {
        Patient patientInList = model.getPatientManager().getReadOnlyList().get(0);
        assertPatientCommandFailure(new AddPatientCommand(patientInList),
                model, AddPatientCommand.MESSAGE_DUPLICATE_PATIENT);
    }

}
