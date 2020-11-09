package team.baymax.logic.commands.appointment;

import static java.util.Objects.requireNonNull;

import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.model.Model;
import team.baymax.model.appointment.AppointmentContainsKeywordsPredicate;
import team.baymax.model.util.TabId;

/**
 * Lists all appointments containing a keyword in its description or tags
 */
public class FindAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "findappt";
    public static final TabId TAB_ID = TabId.APPOINTMENT;

    public static final String MESSAGE_APPOINTMENTS_LISTED_SUCCESS = "%1$d appointments listed!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all appointments whose description or tags "
            + "contain the given keyword.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "For example, " + COMMAND_WORD + " xray";

    private final AppointmentContainsKeywordsPredicate predicate;

    /**
     * Creates a @{code FindAppointmentCommand} that will filter the appointment list to
     * those appointments that contain certain keywords in their description or tags
     * @param predicate  predicate to check whether an appointment contains certain keywords
     */
    public FindAppointmentCommand(AppointmentContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
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
                || (other instanceof FindAppointmentCommand // instanceof handles nulls
                && predicate.equals(((FindAppointmentCommand) other).predicate)); // state check
    }
}
