package seedu.address.logic.commands.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all patients in the appointment book to the user.
 */
public class ListPatientCommand extends PatientCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all patients";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        return new CommandResult(MESSAGE_SUCCESS, TAB_NUMBER);
    }
}
