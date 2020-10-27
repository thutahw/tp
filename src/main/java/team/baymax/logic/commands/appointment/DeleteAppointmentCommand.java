package team.baymax.logic.commands.appointment;

import static java.util.Objects.requireNonNull;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DATETIME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_ID;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NAME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.List;

import team.baymax.commons.core.Messages;
import team.baymax.commons.core.index.Index;
import team.baymax.commons.core.time.DateTime;
import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.SameDatetimeAndPatientPredicate;
import team.baymax.model.listmanagers.PatientManager;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.Nric;
import team.baymax.model.patient.Patient;
import team.baymax.model.util.uniquelist.exceptions.ElementNotFoundException;

public class DeleteAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "deleteappt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the appointment identified by the patient's ID or name, and date-time of the appointment.\n"
            + "Parameters: "
            + PREFIX_ID + "PATIENT_ID "
            + "(OR "
            + PREFIX_NRIC + "PATIENT_NRIC "
            + "OR "
            + PREFIX_NAME + "PATIENT_NAME) "
            + PREFIX_DATETIME + "DATETIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "1 "
            + PREFIX_DATETIME + "11-10-2020 12:30 ";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Deleted Appointment: %1$s";

    private final Index targetIndex;
    private final Name name;
    private final Nric nric;
    private final DateTime dateTime;

    /**
     * Creates a DeleteAppointmentCommand for when Patient is supplied as an Index in the displayed list
     * @param targetIndex
     * @param dateTime
     */
    public DeleteAppointmentCommand(Index targetIndex, DateTime dateTime) {
        requireNonNull(targetIndex);
        requireNonNull(dateTime);

        this.targetIndex = targetIndex;
        this.dateTime = dateTime;
        this.name = null;
        this.nric = null;
    }

    /**
     * Creates a DeleteAppointmentCommand for when Patient is specified by their nric
     * @param nric
     * @param dateTime
     */
    public DeleteAppointmentCommand(Nric nric, DateTime dateTime) {
        requireNonNull(nric);
        requireNonNull(dateTime);

        this.nric = nric;
        this.dateTime = dateTime;
        this.name = null;
        this.targetIndex = null;
    }

    /**
     * Creates a DeleteAppointmentCommand for when Patient is specified by their name
     * @param name
     * @param dateTime
     */
    public DeleteAppointmentCommand(Name name, DateTime dateTime) {
        requireNonNull(name);
        requireNonNull(dateTime);

        this.name = name;
        this.dateTime = dateTime;
        this.targetIndex = null;
        this.nric = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Patient patientOfAppointment;

        if (targetIndex != null) {
            List<Patient> lastShownList = model.getFilteredPatientList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
            }

            patientOfAppointment = lastShownList.get(targetIndex.getZeroBased());
        } else if (nric != null) {
            PatientManager patientManager = (PatientManager) model.getPatientManager();
            patientOfAppointment = patientManager.getPatientByNric(nric);
        } else if (name != null) {
            PatientManager patientManager = (PatientManager) model.getPatientManager();
            patientOfAppointment = patientManager.getPatientByName(name);
        } else {
            throw new CommandException(Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
        }

        SameDatetimeAndPatientPredicate predicate = new SameDatetimeAndPatientPredicate(dateTime, patientOfAppointment);

        Appointment apptToDelete;

        try {
            apptToDelete = model.findAppointmentByPredicate(predicate);

            model.deleteAppointment(apptToDelete);
            model.getFilteredAppointmentList();
        } catch (ElementNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS, apptToDelete), getTabNumber());
    }

    @Override
    public Index getTabNumber() {
        return Index.fromOneBased(3);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAppointmentCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteAppointmentCommand) other).targetIndex)
                && getTabNumber().equals(((DeleteAppointmentCommand) other).getTabNumber())); // state check
    }
}
