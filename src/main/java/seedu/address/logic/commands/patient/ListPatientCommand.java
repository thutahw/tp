package seedu.address.logic.commands.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all patients in the appointment book to the user.
 */
public class ListPatientCommand extends Command {

    public static final String COMMAND_WORD = "listPatient";

    public static final String MESSAGE_SUCCESS = "Listed all patients";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        return new CommandResult(MESSAGE_SUCCESS, getTabNumber());
    }

    @Override
    public Index getTabNumber() {
        return Index.fromOneBased(3);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListPatientCommand // instanceof handles nulls
                && getTabNumber().equals(((ListPatientCommand) other).getTabNumber()));
    }
}
