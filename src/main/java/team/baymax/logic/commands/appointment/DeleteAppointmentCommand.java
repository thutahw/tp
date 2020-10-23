package team.baymax.logic.commands.appointment;

import team.baymax.commons.core.Messages;
import team.baymax.commons.core.index.Index;
import team.baymax.commons.core.time.DateTime;
import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.patient.DeletePatientCommand;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentStatus;
import team.baymax.model.appointment.Description;
import team.baymax.model.listmanagers.PatientManager;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.Nric;
import team.baymax.model.patient.Patient;
import team.baymax.model.tag.Tag;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DATETIME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_ID;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NAME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NRIC;

public class DeleteAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "deleteAppt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the appointment identified by the patient's ID or name, and date-time of the appointment.\n"
            + "Parameters: "
            + PREFIX_ID + "PATIENT_ID "
            // TODO: edit to nric
            + "(OR "
            + PREFIX_NRIC + "PATIENT_NRIC "
            + "OR "
            + PREFIX_NAME + "PATIENT_NAME) "
            + PREFIX_DATETIME + "DATETIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "1 "
            + PREFIX_DATETIME + "11-10-2020 12:30 ";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Deleted Appointment: %1$s";

    private final Index targetIndex;
    private final Name name;
    private final Nric nric;
    private final DateTime dateTime;

    public DeleteAppointmentCommand(Index targetIndex, DateTime dateTime) {
        requireNonNull(targetIndex);
        requireNonNull(dateTime);

        this.targetIndex = targetIndex;
        this.dateTime = dateTime;
        this.name = null;
        this.nric = null;
    }

    public DeleteAppointmentCommand(Nric nric, DateTime dateTime) {
        requireNonNull(nric);
        requireNonNull(dateTime);

        this.nric = nric;
        this.dateTime = dateTime;
        this.name = null;
        this.targetIndex = null;
    }

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
            PatientManager patientManager = (PatientManager)model.getPatientManager();
            patientOfAppointment = patientManager.getPatientByNric(nric);
        } else if (name != null) {
            PatientManager patientManager = (PatientManager)model.getPatientManager();
            patientOfAppointment = patientManager.getPatientByName(name);
        } else {
            throw new CommandException(Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
        }

        // create new appointment with the Date Time specified
        Appointment apptToDelete = new Appointment(patientOfAppointment, dateTime);

        if (model.hasAppointment(apptToDelete)) {
            // hasAppointment checks if apptToDelete isSame as some appointment in the appointmentList
            // isSame returns true if the appointments have the same Patient and DateTime
            // TODO: FIND APPOINTMENT as in actually get that appointment w the same dateTime & Patient
            model.deleteAppointment(apptToDelete);
            Predicate<Appointment> PREDICATE_MATCH_APPOINTMENT = ()
            model.getFilteredAppointmentList()
            // TODO: actually this might throw an ElementNotFoundException that idk if we need to catch
        } else {
            throw new CommandException(Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
        }
        // TODO: the deleted appointment displayed won't have the remarks and tags and correct status
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
//                && targetIndex.equals(((DeletePatientCommand) other).targetIndex)
                && getTabNumber().equals(((DeletePatientCommand) other).getTabNumber())); // state check
    }
}
