package team.baymax.logic.commands;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;

public class SwitchCommand extends Command {

    public static final int MAX_TAB = 5;
    public static final String MESSAGE_SUCCESS = "Switched to tab: %1$s";
    public static final String MESSAGE_INVALID_TAB_NUMBER = "This tab does not exist.";

    private final Index tabNumber;

    public SwitchCommand(Index tabNumber) {
        this.tabNumber = tabNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (tabNumber.getOneBased() < 1 && tabNumber.getOneBased() > MAX_TAB) {
            throw new CommandException(MESSAGE_INVALID_TAB_NUMBER);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, tabNumber), tabNumber);
    }

    @Override
    public Index getTabNumber() {
        return tabNumber;
    }
}
