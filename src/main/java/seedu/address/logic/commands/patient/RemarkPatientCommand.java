package seedu.address.logic.commands.patient;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Remark;

/**
 * Changes the remark of an existing patient.
 */
public class RemarkPatientCommand extends PatientCommand {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Patient: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Patient: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the remark of the patient identified "
            + "by the index number used in the last patient listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "r/ [REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "r/ Likes to swim.";

    private final Index index;
    private final Remark remark;

    /**
     * @param index of the patient in the filtered patient list to edit the remark
     * @param remark of the patient to be updated to
     */
    public RemarkPatientCommand(Index index, Remark remark) {
        requireAllNonNull(index, remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Patient> lastShownList = model.getFilteredPatientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patientToEdit = lastShownList.get(index.getZeroBased());
        Patient editedPatient = new Patient(patientToEdit.getNric(), patientToEdit.getName(), patientToEdit.getPhone(),
                patientToEdit.getGender(), patientToEdit.getTags(), remark);

        model.setPatient(patientToEdit, editedPatient);
        model.updateFilteredPatientList(Model.PREDICATE_SHOW_ALL_PATIENTS);

        return new CommandResult(generateSuccessMessage(editedPatient), TAB_NUMBER);
    }

    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code patientToEdit}.
     */
    private String generateSuccessMessage(Patient patientToEdit) {
        String message = !remark.value.isEmpty() ? MESSAGE_ADD_REMARK_SUCCESS : MESSAGE_DELETE_REMARK_SUCCESS;
        return String.format(message, patientToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkPatientCommand)) {
            return false;
        }

        // state check
        RemarkPatientCommand e = (RemarkPatientCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
    }
}
