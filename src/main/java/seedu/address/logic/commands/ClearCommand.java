package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.model.AppointmentBook;
import seedu.address.model.Model;

/**
 * Clears the appointment book.
 */
public class ClearCommand extends Command {

    public static final Index TAB_NUMBER = Index.fromOneBased(1);
    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Appointment book has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAppointmentBook(new AppointmentBook());
        return new CommandResult(MESSAGE_SUCCESS, TAB_NUMBER);
    }

    @Override
    public Index getTabNumber() {
        return TAB_NUMBER;
    }
}
