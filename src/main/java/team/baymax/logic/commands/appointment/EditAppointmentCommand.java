package team.baymax.logic.commands.appointment;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;
import static team.baymax.logic.commands.appointment.AddAppointmentCommand.MESSAGE_CLASH_APPOINTMENT;
import static team.baymax.logic.commands.appointment.AddAppointmentCommand.MESSAGE_INVALID_DURATION;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DATETIME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DURATION;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;

import team.baymax.commons.core.Messages;
import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentIdenticalPredicate;
import team.baymax.model.appointment.Description;
import team.baymax.model.patient.Patient;
import team.baymax.model.tag.Tag;
import team.baymax.model.util.TabId;
import team.baymax.model.util.datetime.DateTime;
import team.baymax.model.util.datetime.Duration;

/**
 * Edits the details (date-and-time, description, tags) of an existing appointment in the appointment book.
 */
public class EditAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "editappt";
    public static final TabId TAB_ID = TabId.APPOINTMENT;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the appointment identified "
            + "by the index number used in the displayed appointment list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DATETIME + "DATETIME] "
            + "[" + PREFIX_DURATION + "DURATION] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATETIME + "24-10-2020 11:00 ";

    public static final String MESSAGE_EDIT_APPOINTMENT_SUCCESS = "Edited Appointment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the "
            + "appointment book.";

    private final Index index;
    private final EditAppointmentDescriptor editAppointmentDescriptor;

    /**
     * Creates an @{code EditAppointmentCommand} to edit the specified {@code Appointment} to the appointment book.
     * @param index of the appointment in the filtered appointment list to edit
     * @param editAppointmentDescriptor details to edit the appointment with
     */
    public EditAppointmentCommand(Index index, EditAppointmentDescriptor editAppointmentDescriptor) {
        requireAllNonNull(index, editAppointmentDescriptor);

        this.index = index;
        this.editAppointmentDescriptor = new EditAppointmentDescriptor(editAppointmentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        Appointment appointmentToEdit = lastShownList.get(index.getZeroBased());
        Appointment editedAppointment = createEditedAppointment(appointmentToEdit, editAppointmentDescriptor);

        if (!appointmentToEdit.isSame(editedAppointment) && model.hasAppointment(editedAppointment)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        if (editAppointmentDescriptor.isDateTimeEdited()
                && model.doesAppointmentClash(editedAppointment, appointmentToEdit)) {
            throw new CommandException(MESSAGE_CLASH_APPOINTMENT);
        }

        DateTime dateTime = editedAppointment.getDateTime();

        if (dateTime.extendsUntilNextDay(editedAppointment.getDuration())) {
            throw new CommandException(MESSAGE_INVALID_DURATION);
        }

        model.setAppointment(appointmentToEdit, editedAppointment);
        model.updateFilteredAppointmentList(new AppointmentIdenticalPredicate(editedAppointment));

        return new CommandResult(String.format(MESSAGE_EDIT_APPOINTMENT_SUCCESS, editedAppointment), getTabId());
    }

    @Override
    public TabId getTabId() {
        return TAB_ID;
    }

    /**
     * Creates and returns a {@code Appointment} with the details of {@code appointmentToEdit}
     * edited with {@code editAppointmentDescriptor}.
     */
    private static Appointment createEditedAppointment(Appointment appointmentToEdit,
                                                       EditAppointmentDescriptor editAppointmentDescriptor) {
        assert appointmentToEdit != null;

        Patient unchangedPatient = appointmentToEdit.getPatient();
        Boolean unchangedIsMissed = appointmentToEdit.checkIfMissed();
        DateTime updatedDateTime = editAppointmentDescriptor.getDateTime()
                .orElse(appointmentToEdit.getDateTime());
        Duration updatedDuration = editAppointmentDescriptor.getDuration()
                .orElse(appointmentToEdit.getDuration());
        Set<Tag> updatedTags = editAppointmentDescriptor.getTags()
                .orElse(appointmentToEdit.getTags());
        Description updatedDescription = editAppointmentDescriptor.getDescription()
                .orElse(appointmentToEdit.getDescription());

        return new Appointment(unchangedPatient, updatedDateTime, updatedDuration, updatedDescription,
                updatedTags, unchangedIsMissed);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAppointmentCommand)) {
            return false;
        }

        // state check
        EditAppointmentCommand e = (EditAppointmentCommand) other;
        return index.equals(e.index)
                && editAppointmentDescriptor.equals(e.editAppointmentDescriptor);
    }
}
