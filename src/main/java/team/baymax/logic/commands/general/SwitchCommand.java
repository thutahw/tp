package team.baymax.logic.commands.general;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.util.TabId;

public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "tab";
    public static final String MESSAGE_SUCCESS = "Switched to %1$s tab";
    public static final String MESSAGE_INVALID_TAB = "The tab number you've entered is invalid.";

    private final Index tabNumber;

    public SwitchCommand(Index tabNumber) {
        this.tabNumber = tabNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        TabId tabId = TabId.valueOf(tabNumber.getOneBased());
        if (tabId == null) {
            throw new CommandException(MESSAGE_INVALID_TAB);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, tabId), tabId);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchCommand // instanceof handles nulls
                && tabNumber.equals(((SwitchCommand) other).tabNumber));
    }
}
