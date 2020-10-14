package seedu.address.logic.commands.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.time.DateTime;
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

    public static final String COMMAND_WORD = "addAppt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to the appointment book. "
            + "Parameters: "
            + PREFIX_ID + "PATIENT_ID "
            + PREFIX_DATETIME + "DATETIME "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "1 "
            + PREFIX_DATETIME + "11-10-2020 12:30 "
            + PREFIX_DESCRIPTION + "Removal of braces. "
            + PREFIX_TAG + "DrGoh "
            + PREFIX_TAG + "1HR";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the "
            + "appointment book";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "The patient at the specified index does not exist";

    private final Index patientIndex;
    private final DateTime dateTime;
    private final Description description;
    private final Set<Tag> tags;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(Index patientIndex, DateTime dateTime, Description description, Set<Tag> tags) {
        requireAllNonNull(patientIndex, dateTime, description, tags);
        this.patientIndex = patientIndex;
        this.dateTime = dateTime;
        this.description = description;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (patientIndex.getZeroBased() >= model.getFilteredPatientList().size()) {
            throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
        }

        Patient patient = model.getFilteredPatientList().get(patientIndex.getZeroBased());
        Appointment toAdd = new Appointment(patient, dateTime, AppointmentStatus.UPCOMING, description, tags);

        if (model.hasAppointment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        model.addAppointment(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), getTabNumber());
    }

    @Override
    public Index getTabNumber() {
        return Index.fromOneBased(4);
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
