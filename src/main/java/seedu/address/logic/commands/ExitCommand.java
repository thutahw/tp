package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final Index TAB_NUMBER = Index.fromOneBased(1);
    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true, TAB_NUMBER);
    }

    @Override
    public Index getTabNumber() {
        return TAB_NUMBER;
    }

}
