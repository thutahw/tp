package team.baymax.logic.commands;

import team.baymax.commons.core.index.Index;
import team.baymax.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Baymax as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true, getTabNumber());
    }

    @Override
    public Index getTabNumber() {
        return Index.fromOneBased(1);
    }

}
