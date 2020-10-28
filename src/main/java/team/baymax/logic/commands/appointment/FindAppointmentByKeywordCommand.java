package team.baymax.logic.commands.appointment;

import static java.util.Objects.requireNonNull;

import team.baymax.commons.core.Messages;
import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.model.Model;
import team.baymax.model.appointment.AppointmentContainsKeywordPredicate;
/**
 * Lists all appointments containing a keyword in its description or tags
 */
public class FindAppointmentByKeywordCommand extends Command {

    public static final String COMMAND_WORD = "findapptkeyword";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all appointments whose description or tags "
            + "contain the given keyword. "
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " xray";

    private final AppointmentContainsKeywordPredicate predicate;

    public FindAppointmentByKeywordCommand(AppointmentContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAppointmentList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_APPOINTMENTS_LISTED_OVERVIEW,
                        model.getFilteredAppointmentList().size()), getTabNumber());
    }

    @Override
    public Index getTabNumber() {
        return Index.fromOneBased(4);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindAppointmentByKeywordCommand // instanceof handles nulls
                && predicate.equals(((FindAppointmentByKeywordCommand) other).predicate))
                && getTabNumber().equals(((FindAppointmentByKeywordCommand) other).getTabNumber()); // state check
    }
}
