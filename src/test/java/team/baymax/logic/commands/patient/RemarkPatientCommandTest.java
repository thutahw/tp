package team.baymax.logic.commands.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import team.baymax.logic.commands.CommandTestUtil;
import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.modelmanagers.AppointmentManager;
import team.baymax.model.modelmanagers.CalendarManager;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.patient.Patient;
import team.baymax.model.patient.PatientIdenticalPredicate;
import team.baymax.model.patient.Remark;
import team.baymax.model.userprefs.UserPrefs;
import team.baymax.testutil.patient.PatientBuilder;
import team.baymax.testutil.patient.TypicalPatientIndexes;
import team.baymax.testutil.patient.TypicalPatients;

class RemarkPatientCommandTest {
    private static final String REMARK_STUB = "Some remark";
    private final Model model = new ModelManager(TypicalPatients.getTypicalPatientManager(),
            new AppointmentManager(), new UserPrefs(), new CalendarManager());

    @Test
    void execute_addRemarkUnfilteredList_success() {
        Patient firstPatient = model.getFilteredPatientList()
                .get(TypicalPatientIndexes.INDEX_FIRST_PATIENT.getZeroBased());
        Patient editedPatient = new PatientBuilder(firstPatient).withRemark(REMARK_STUB).build();

        RemarkPatientCommand remarkPatientCommand = new RemarkPatientCommand(
                TypicalPatientIndexes.INDEX_FIRST_PATIENT,
                new Remark(editedPatient.getRemark().getValue()));

        String expectedMessage = String.format(RemarkPatientCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new PatientManager(model.getPatientManager()),
                new AppointmentManager(), new UserPrefs(), new CalendarManager());
        expectedModel.setPatient(firstPatient, editedPatient);
        expectedModel.updateFilteredPatientList(new PatientIdenticalPredicate(editedPatient));

        CommandTestUtil.assertCommandSuccess(remarkPatientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Patient patient = new PatientBuilder().build();
        RemarkPatientCommand remarkPatientCommandA = new RemarkPatientCommand(
                TypicalPatientIndexes.INDEX_FIRST_PATIENT,
                new Remark(REMARK_STUB));
        RemarkPatientCommand remarkPatientCommandB = new RemarkPatientCommand(
                TypicalPatientIndexes.INDEX_SECOND_PATIENT,
                patient.getRemark());
        // null -> returns False
        assertFalse(remarkPatientCommandA.equals(null));
        // different type -> returns False
        assertFalse(remarkPatientCommandA.equals(1));
        // this -> returns True
        assertTrue(remarkPatientCommandA.equals(remarkPatientCommandA));
        // remark command but with different state-> returns False
        assertFalse(remarkPatientCommandA.equals(remarkPatientCommandB));
        // remark command with same state -> returns True
        assertTrue(remarkPatientCommandB.equals(new RemarkPatientCommand(TypicalPatientIndexes.INDEX_SECOND_PATIENT,
                patient.getRemark())));
    }
}
