package seedu.address.logic.commands;

import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;
import static seedu.address.logic.commands.patient.PatientCommandTestUtil.assertPatientCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

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
