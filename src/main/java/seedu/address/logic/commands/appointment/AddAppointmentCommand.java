package seedu.address.logic.commands.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.patient.AddPatientCommand;
import seedu.address.model.appointment.Appointment;

public class AddAppointmentCommand {
    public static final String COMMAND_WORD = "addAppt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to the appointment book. "
            + "Parameters: "
            + PREFIX_ID + "ID "
            + PREFIX_DATETIME + "DATETIME "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "1 "
            + PREFIX_DATETIME + "2020-10-11 12pm "
            + PREFIX_DESCRIPTION + "Removal of braces. "
            + PREFIX_TAG + "Dr. Goh "
            + PREFIX_TAG + "1HR";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This appointment already exists in the appointment book";

    private final Appointment toAdd;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(Appointment appointment) {
        requireNonNull(appointment);
        toAdd = appointment;
    }

    //    @Override
    //    public CommandResult execute(Model model) throws CommandException {
    //        requireNonNull(model);
    //
    //        if (model.hasPerson(toAdd)) {
    //            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
    //        }
    //
    //        model.addPerson(toAdd);
    //        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), TAB_NUMBER);
    //    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPatientCommand // instanceof handles nulls
                && toAdd.equals(((AddAppointmentCommand) other).toAdd));
    }
}
