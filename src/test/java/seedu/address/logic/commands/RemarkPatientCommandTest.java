package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.patient.RemarkPatientCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Remark;
import seedu.address.testutil.PatientBuilder;

class RemarkPatientCommandTest {
    private static final String REMARK_STUB = "Some remark";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_addRemarkUnfilteredList_success() {
        Patient firstPatient = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Patient editedPatient = new PatientBuilder(firstPatient).withRemark(REMARK_STUB).build();

        RemarkPatientCommand remarkPatientCommand = new RemarkPatientCommand(INDEX_FIRST_PERSON,
                new Remark(editedPatient.getRemark().value));

        String expectedMessage = String.format(RemarkPatientCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPatient, editedPatient);

        assertCommandSuccess(remarkPatientCommand, model, expectedMessage, expectedModel);
    }
}
