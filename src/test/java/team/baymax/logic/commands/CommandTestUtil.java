package team.baymax.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;

public class CommandTestUtil {
    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);

            System.out.println("Actual command result message:");
            System.out.println(result.getFeedbackToUser());
            System.out.println("Expected command result message:");
            System.out.println(expectedCommandResult.getFeedbackToUser());

            System.out.println("Calendar manager:");
            System.out.println(actualModel.getCalendarManager().equals(expectedModel.getCalendarManager()));
            System.out.println("Appointment manager:");
            System.out.println(actualModel.getAppointmentManager().equals(expectedModel.getAppointmentManager()));
            System.out.println("Patient manager:");
            System.out.println(actualModel.getPatientManager().equals(expectedModel.getPatientManager()));
            System.out.println("Filtered patient list:");
            System.out.println(actualModel.getFilteredPatientList().equals(expectedModel.getFilteredPatientList()));
            System.out.println("Filtered appointment list:");
            System.out.println(actualModel.getFilteredAppointmentList().get(0));
            System.out.println(expectedModel.getFilteredAppointmentList().get(0));

            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link CommandTestUtil#assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, command.getTabId());
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }
}
