package team.baymax.logic.commands;

import static team.baymax.logic.commands.general.HelpCommand.SHOWING_HELP_MESSAGE;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.assertPatientCommandSuccess;

import org.junit.jupiter.api.Test;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.general.HelpCommand;
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
        assertPatientCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
