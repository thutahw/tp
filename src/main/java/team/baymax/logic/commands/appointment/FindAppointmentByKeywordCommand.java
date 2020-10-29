package team.baymax.logic.commands.appointment;

import static java.util.Objects.requireNonNull;

import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.model.Model;
import team.baymax.model.appointment.AppointmentContainsKeywordPredicate;
import team.baymax.model.util.TabId;

/**
 * Lists all appointments containing a keyword in its description or tags
 */
public class FindAppointmentByKeywordCommand extends Command {

    public static final String COMMAND_WORD = "findappt";
    public static final TabId TAB_ID = TabId.APPOINTMENT;

    public static final String MESSAGE_APPOINTMENTS_LISTED_SUCCESS = "%1$d appointments listed!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all appointments whose description or tags "
            + "contain the given keyword.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "For example, " + COMMAND_WORD + " xray";

    private final AppointmentContainsKeywordPredicate predicate;

    public FindAppointmentByKeywordCommand(AppointmentContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredAppointmentList(predicate);
        return new CommandResult(
                String.format(MESSAGE_APPOINTMENTS_LISTED_SUCCESS,
                        model.getFilteredAppointmentList().size()), getTabId());
    }

    @Override
    public TabId getTabId() {
        return TAB_ID;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindAppointmentByKeywordCommand // instanceof handles nulls
                && predicate.equals(((FindAppointmentByKeywordCommand) other).predicate)); // state check
    }
}
