package team.baymax.logic.commands.general;

import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.model.Model;
import team.baymax.model.util.TabId;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Baymax as requested ...";

    public static final TabId TAB_ID = TabId.DASHBOARD;

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true, getTabId());
    }

    @Override
    public TabId getTabId() {
        return TAB_ID;
    }
}
