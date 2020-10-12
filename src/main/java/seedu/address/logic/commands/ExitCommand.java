package seedu.address.logic.commands;

import seedu.address.logic.commands.patient.PatientCommand;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends PatientCommand {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true, TAB_NUMBER);
    }

}
