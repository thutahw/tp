package team.baymax.logic.commands.appointment;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DATETIME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_ID;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NAME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NRIC;
import static team.baymax.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import java.util.List;

import team.baymax.commons.core.Messages;
import team.baymax.commons.core.index.Index;
import team.baymax.commons.core.time.DateTime;
import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentStatus;
import team.baymax.model.appointment.SameDatetimeAndPatientPredicate;
import team.baymax.model.listmanagers.PatientManager;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.Nric;
import team.baymax.model.patient.Patient;
import team.baymax.model.util.uniquelist.exceptions.ElementNotFoundException;

public class MarkAppointmentDoneCommand extends Command {

    public static final String COMMAND_WORD = "markdone";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks an appointment as done. "
            + "Parameters: "
            + PREFIX_ID + "APPOINTMENT_INDEX "
            + "OR "
            + PREFIX_DATETIME + "DATETIME "
            + "(" + PREFIX_NAME + "NAME OR "
            + PREFIX_NRIC + "NRIC)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATETIME + "10-10-2020 14:00 "
            + PREFIX_NAME + "Alex Yeoh. "
            + "OR " + COMMAND_WORD + " "
            + PREFIX_ID + "1 ";

    public static final String MESSAGE_MARK_AS_DONE_SUCCESS = "Appointment marked as done: %1$s";

    // appointment is directly specified using index
    private final Index apptIndex;

    // appointment is specified by its dateTime and patient's nric / name
    private final DateTime dateTime;
    private final Nric nric;
    private final Name name;

    /**
     * Creates a MarkAppointmentDoneCommand to mark the {@code Appointment} at {@code Index apptIndex} in the
     * displayed appointment list as done
     */
    public MarkAppointmentDoneCommand(Index apptIndex) {
        requireNonNull(apptIndex);
        this.apptIndex = apptIndex;
        this.dateTime = null;
        this.nric = null;
        this.name = null;
    }

    /**
     * Creates a MarkAppointmentDoneCommand to mark the {@code Appointment} with the specified
     * {@code dateTime} and {@code Patient} identified by {@code nric} as done
     */
    public MarkAppointmentDoneCommand(Nric nric, DateTime dateTime) {
        requireAllNonNull(nric, dateTime);
        this.apptIndex = null;
        this.dateTime = dateTime;
        this.nric = nric;
        this.name = null;
    }

    /**
     * Creates a MarkAppointmentDoneCommand to mark the {@code Appointment} with the specified
     * {@code dateTime} and {@code Patient} identified by {@code name} as done
     */
    public MarkAppointmentDoneCommand(Name name, DateTime dateTime) {
        requireAllNonNull(name, dateTime);
        this.apptIndex = null;
        this.dateTime = dateTime;
        this.nric = null;
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Patient patientOfAppointment;

        Appointment appointmentToEdit;

        if (apptIndex != null) {
            List<Appointment> lastShownList = model.getFilteredAppointmentList();

            if (apptIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
            }

            appointmentToEdit = lastShownList.get(apptIndex.getZeroBased());
        } else if (dateTime != null && (nric != null || name != null)) {
            // find appointment specified by dateTime and patient

            if (nric != null) {
                PatientManager patientManager = (PatientManager) model.getPatientManager();
                patientOfAppointment = patientManager.getPatientByNric(nric);
            } else if (name != null) {
                PatientManager patientManager = (PatientManager) model.getPatientManager();
                patientOfAppointment = patientManager.getPatientByName(name);
            } else {
                throw new CommandException(Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
            }

            SameDatetimeAndPatientPredicate predicate =
                    new SameDatetimeAndPatientPredicate(dateTime, patientOfAppointment);
            try {
                appointmentToEdit = model.findAppointmentByPredicate(predicate);
            } catch (ElementNotFoundException e) {
                throw new CommandException(Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
            }
        } else {
            throw new CommandException(Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
        }

        Appointment markedAsDoneAppointment = new Appointment(appointmentToEdit.getPatient(),
                appointmentToEdit.getDateTime(), AppointmentStatus.DONE,
                appointmentToEdit.getDescription(), appointmentToEdit.getTags());

        model.setAppointment(appointmentToEdit, markedAsDoneAppointment);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(String.format(MESSAGE_MARK_AS_DONE_SUCCESS, markedAsDoneAppointment), getTabNumber());
    }

    @Override
    public Index getTabNumber() {
        return Index.fromOneBased(4);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAppointmentDoneCommand)) {
            return false;
        }

        // state check
        MarkAppointmentDoneCommand m = (MarkAppointmentDoneCommand) other;
        return apptIndex.equals(m.apptIndex)
                && dateTime.equals(m.dateTime)
                && nric.equals(m.nric)
                && name.equals(m.name);
    }
}
