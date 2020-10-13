package seedu.address.logic.commands.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDateTime;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentStatus;
import seedu.address.model.appointment.Description;
import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;

public class AddAppointmentCommand extends Command {

    public static final Index TAB_NUMBER = Index.fromOneBased(4);
    public static final String COMMAND_WORD = "addAppt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to the appointment book. "
            + "Parameters: "
            + PREFIX_ID + "ID "
            + PREFIX_DATETIME + "DATETIME "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "1 "
            + PREFIX_DATETIME + "2020-10-11 12:30PM "
            + PREFIX_DESCRIPTION + "Removal of braces. "
            + PREFIX_TAG + "DrGoh "
            + PREFIX_TAG + "1HR";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the "
            + "appointment book";

    private final Index patientIndex;
    private final LocalDateTime dateTime;
    private final Description description;
    private final Set<Tag> tags;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(Index patientIndex, LocalDateTime dateTime, Description description, Set<Tag> tags) {
        requireAllNonNull(patientIndex, dateTime, description, tags);
        this.patientIndex = patientIndex;
        this.dateTime = dateTime;
        this.description = description;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Patient patient = model.getFilteredPatientList().get(patientIndex.getZeroBased());
        Appointment toAdd = new Appointment(patient, dateTime, tags, description, AppointmentStatus.UPCOMING);

        if (model.hasAppointment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        model.addAppointment(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), TAB_NUMBER);
    }

    @Override
    public Index getTabNumber() {
        return TAB_NUMBER;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppointmentCommand // instanceof handles nulls
                && patientIndex.equals(((AddAppointmentCommand) other).patientIndex)
                && dateTime.equals(((AddAppointmentCommand) other).dateTime)
                && description.equals(((AddAppointmentCommand) other).description)
                && tags.equals(((AddAppointmentCommand) other).tags));
    }
}
