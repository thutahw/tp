package team.baymax.logic.commands.patient;

import static java.util.Objects.requireNonNull;
import static team.baymax.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.model.Model;
import team.baymax.model.util.TabId;

/**
 * Lists all patients in the appointment book to the user.
 */
public class ListPatientCommand extends Command {

    public static final String COMMAND_WORD = "listpatients";

    public static final String MESSAGE_SUCCESS = "Listed all patients";
    public static final TabId TAB_ID = TabId.PATIENT;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        return new CommandResult(MESSAGE_SUCCESS, getTabId());
    }

    @Override
    public TabId getTabId() {
        return TAB_ID;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof ListPatientCommand; // instanceof handles nulls
    }
}
