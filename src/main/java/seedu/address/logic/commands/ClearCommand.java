package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.patient.PatientCommand;
import seedu.address.model.AppointmentBook;
import seedu.address.model.Model;

/**
 * Clears the appointment book.
 */
public class ClearCommand extends PatientCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Appointment book has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAppointmentBook(new AppointmentBook());
        return new CommandResult(MESSAGE_SUCCESS, TAB_NUMBER);
    }
}
