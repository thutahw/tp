package team.baymax.logic.commands.appointment;

import static java.util.Objects.requireNonNull;
import static team.baymax.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.model.Model;

/**
 * Lists all patients in the appointment book to the user.
 */
public class ListAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "listAppt";

    public static final String MESSAGE_SUCCESS = "Listed all appointments";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(MESSAGE_SUCCESS, getTabNumber());
    }

    @Override
    public Index getTabNumber() {
        return Index.fromOneBased(4);
    }
}
