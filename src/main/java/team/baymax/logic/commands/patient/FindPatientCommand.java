package team.baymax.logic.commands.patient;

import static java.util.Objects.requireNonNull;

import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.model.Model;
import team.baymax.model.patient.NameContainsKeywordsPredicate;
import team.baymax.model.util.TabId;

/**
 * Finds and lists all patients in the appointment book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPatientCommand extends Command {

    public static final String COMMAND_WORD = "findpatient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all patients whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "For example, " + COMMAND_WORD + " alice bob charlie";

    public static final String MESSAGE_PATIENTS_LISTED_SUCCESS = "%1$d patient(s) listed!";

    public static final TabId TAB_ID = TabId.PATIENT;

    private final NameContainsKeywordsPredicate predicate;

    public FindPatientCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPatientList(predicate);
        return new CommandResult(String.format(MESSAGE_PATIENTS_LISTED_SUCCESS,
                model.getFilteredPatientList().size()), getTabId());
    }

    @Override
    public TabId getTabId() {
        return TAB_ID;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPatientCommand // instanceof handles nulls
                && predicate.equals(((FindPatientCommand) other).predicate)); // state check
    }
}
