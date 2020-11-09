package team.baymax.logic.commands.appointment;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DATETIME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import team.baymax.commons.core.Messages;
import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentIdenticalPredicate;
import team.baymax.model.appointment.SameDatetimeAndPatientPredicate;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.Patient;
import team.baymax.model.util.TabId;
import team.baymax.model.util.datetime.DateTime;
import team.baymax.model.util.uniquelist.exceptions.ElementNotFoundException;

public class MarkAppointmentMissedCommand extends Command {

    public static final String COMMAND_WORD = "missed";
    public static final TabId TAB_ID = TabId.NONE;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks an appointment as missed by either "
            + "the displayed index of the appointment "
            + "OR the datetime of the appointment plus the name of the patient.\n"
            + "Parameters: INDEX (or " + PREFIX_DATETIME + "DATETIME " + PREFIX_NAME + "NAME)\n"
            + "For example, " + COMMAND_WORD + " 1\n"
            + "Alternatively, " + COMMAND_WORD + " "
            + PREFIX_DATETIME + "10-11-2020 12:30 "
            + PREFIX_NAME + "Alex";

    public static final String MESSAGE_MARK_AS_MISSED_SUCCESS = "Appointment marked as missed: %1$s";

    private final Index index;
    private final DateTime dateTime;
    private final Name patientName;

    /**
     * Creates a MarkAppointmentMissedCommand to mark the {@code Appointment} at {@code Index index} in the
     * displayed appointment list as missed
     */
    public MarkAppointmentMissedCommand(Index index) {
        requireNonNull(index);
        this.index = index;
        this.dateTime = null;
        this.patientName = null;
    }

    /**
     * Creates a MarkAppointmentMissedCommand to mark the {@code Appointment} with the specified
     * {@code dateTime} and {@code Patient} identified by {@code patientName} as missed.
     */
    public MarkAppointmentMissedCommand(DateTime dateTime, Name patientName) {
        requireAllNonNull(dateTime, patientName);
        this.dateTime = dateTime;
        this.patientName = patientName;
        this.index = null;
    }

    private boolean isFindByIndex() {
        return index != null && dateTime == null && patientName == null;
    }

    private boolean isFindByNameAndTime() {
        return dateTime != null && patientName != null && index == null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Patient patient;
        Appointment appointmentToEdit;

        if (isFindByIndex()) {
            List<Appointment> lastShownList = model.getFilteredAppointmentList();

            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
            }
            appointmentToEdit = lastShownList.get(index.getZeroBased());
        } else if (isFindByNameAndTime()) {
            try {
                patient = model.getPatient(patientName);
                SameDatetimeAndPatientPredicate predicate = new SameDatetimeAndPatientPredicate(dateTime, patient);
                appointmentToEdit = model.findAppointmentByPredicate(predicate);
            } catch (ElementNotFoundException e) {
                throw new CommandException(Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
            }
        } else {
            throw new CommandException(Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
        }

        Appointment markedAsMissedAppointment = new Appointment(appointmentToEdit.getPatient(),
                appointmentToEdit.getDateTime(), appointmentToEdit.getDuration(),
                appointmentToEdit.getDescription(), appointmentToEdit.getTags(), true);

        model.setAppointment(appointmentToEdit, markedAsMissedAppointment);
        model.updateFilteredAppointmentList(new AppointmentIdenticalPredicate(markedAsMissedAppointment));

        return new CommandResult(String.format(MESSAGE_MARK_AS_MISSED_SUCCESS, markedAsMissedAppointment), getTabId());
    }

    @Override
    public TabId getTabId() {
        return TAB_ID;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAppointmentMissedCommand)) {
            return false;
        }

        // state check
        MarkAppointmentMissedCommand m = (MarkAppointmentMissedCommand) other;
        return index == null
            ? dateTime.equals(m.dateTime)
                && patientName.equals(m.patientName)
            : index.equals(m.index);
    }
}
