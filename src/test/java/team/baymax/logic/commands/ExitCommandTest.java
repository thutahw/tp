package team.baymax.logic.commands;

import static team.baymax.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.assertPatientCommandSuccess;

import org.junit.jupiter.api.Test;

import team.baymax.commons.core.index.Index;
import team.baymax.model.Model;
import team.baymax.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(
                MESSAGE_EXIT_ACKNOWLEDGEMENT,
                false,
                true,
                Index.fromOneBased(1));
        assertPatientCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
