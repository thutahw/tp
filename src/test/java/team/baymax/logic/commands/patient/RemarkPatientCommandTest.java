package team.baymax.logic.commands.patient;

import org.junit.jupiter.api.Test;

import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.listmanagers.AppointmentManager;
import team.baymax.model.listmanagers.PatientManager;
import team.baymax.model.patient.Patient;
import team.baymax.model.patient.Remark;
import team.baymax.model.userprefs.UserPrefs;
import team.baymax.testutil.PatientBuilder;
import team.baymax.testutil.TypicalIndexes;
import team.baymax.testutil.TypicalPatients;

class RemarkPatientCommandTest {
    private static final String REMARK_STUB = "Some remark";
    private final Model model = new ModelManager(TypicalPatients.getTypicalPatientManager(),
            new AppointmentManager(), new UserPrefs());

    @Test
    void execute_addRemarkUnfilteredList_success() {
        Patient firstPatient = model.getFilteredPatientList().get(TypicalIndexes.INDEX_FIRST_PATIENT.getZeroBased());
        Patient editedPatient = new PatientBuilder(firstPatient).withRemark(REMARK_STUB).build();

        RemarkPatientCommand remarkPatientCommand = new RemarkPatientCommand(TypicalIndexes.INDEX_FIRST_PATIENT,
                new Remark(editedPatient.getRemark().value));

        String expectedMessage = String.format(RemarkPatientCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new PatientManager(model.getPatientManager()),
                new AppointmentManager(), new UserPrefs());
        expectedModel.setPatient(firstPatient, editedPatient);

        PatientCommandTestUtil.assertPatientCommandSuccess(remarkPatientCommand, model, expectedMessage, expectedModel);
    }
}
