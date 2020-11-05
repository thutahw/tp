package team.baymax.logic.commands.general;

import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.model.Model;
import team.baymax.model.util.TabId;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = String.format("Enter '%s' to see our User Guide.", COMMAND_WORD);

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public static final TabId TAB_ID = TabId.INFO;

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false, getTabId());
    }

    @Override
    public TabId getTabId() {
        return TAB_ID;
    }
}
