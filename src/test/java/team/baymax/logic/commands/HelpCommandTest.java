package team.baymax.logic.commands;

import static team.baymax.logic.commands.CommandTestUtil.assertCommandSuccess;
import static team.baymax.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import team.baymax.commons.core.index.Index;
import team.baymax.model.Model;
import team.baymax.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(
                SHOWING_HELP_MESSAGE,
                true,
                false,
                Index.fromOneBased(1));
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
