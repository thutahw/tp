package team.baymax.logic.commands.appointment;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DATETIME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.Optional;

import team.baymax.commons.core.Messages;
import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.SameDatetimeAndPatientPredicate;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.Patient;
import team.baymax.model.util.TabId;
import team.baymax.model.util.datetime.DateTime;

public class DeleteAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Cancels the appointment identified by the appointment's index displayed in the list.\n"
            + "Alternatively: use the datetime and the patient's name to identify the appointment to be cancelled.\n"
            + "Parameters: INDEX or (" + PREFIX_DATETIME + "DATETIME " + PREFIX_NAME + "NAME" + ")\n"
            + "Example: " + COMMAND_WORD + " 1 \n"
            + "Alternatively: " + COMMAND_WORD + " "
            + PREFIX_DATETIME + "11-10-2020 12:30 " + PREFIX_NAME + "Alex ";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Deleted Appointment: %1$s";

    private static final TabId TAB_ID = TabId.APPOINTMENT;

    private final Optional<Index> targetIndex;
    private final Optional<DateTime> dateTime;
    private final Optional<Name> name;

    /**
     * Creates a {@code DeleteAppointmentCommand} which deletes an appointment by its displayed list index.
     *
     */
    public DeleteAppointmentCommand(Optional<Index> targetIndex) {
        requireNonNull(targetIndex);

        this.targetIndex = targetIndex;
        this.dateTime = Optional.empty();
        this.name = Optional.empty();
    }

    /**
     * Creates a {@code DeleteAppointmentCommand} which deletes an appointment by {@code dateTime} and {@code name}.
     *
     */
    public DeleteAppointmentCommand(Optional<DateTime> dateTime, Optional<Name> name) {
        requireAllNonNull(dateTime, name);

        this.dateTime = dateTime;
        this.name = name;
        this.targetIndex = Optional.empty();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Appointment toDelete;

        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (!targetIndex.isEmpty() && dateTime.isEmpty() && name.isEmpty()) {
            if (targetIndex.get().getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
            }
            toDelete = model.getFilteredAppointmentList().get(targetIndex.get().getZeroBased());
        } else if (targetIndex.isEmpty() && !dateTime.isEmpty() && !name.isEmpty()) {
            Patient patientOfAppointment = model.getPatient(name.get());
            SameDatetimeAndPatientPredicate predicate = new SameDatetimeAndPatientPredicate(dateTime.get(),
                    patientOfAppointment);
            toDelete = model.findAppointmentByPredicate(predicate);
        } else {
            throw new CommandException(Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
        }

        model.deleteAppointment(toDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS, toDelete), getTabId());
    }

    @Override
    public TabId getTabId() {
        return TAB_ID;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAppointmentCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteAppointmentCommand) other).targetIndex)
                && dateTime.equals(((DeleteAppointmentCommand) other).dateTime)
                && name.equals(((DeleteAppointmentCommand) other).name)); // state check
    }
}
