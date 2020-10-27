package team.baymax.logic.commands.appointment;

import static java.util.Objects.requireNonNull;

import java.util.List;

import team.baymax.commons.core.Messages;
import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.patient.Patient;
import team.baymax.model.patient.PatientHasAppointmentPredicate;

/**
 * Lists all the appointments of the chosen patient to the user.
 */
public class ListPatientAppointmentsCommand extends Command {

    public static final String COMMAND_WORD = "listapptby";

    public static final String MESSAGE_SUCCESS = "Listed all the appointments of the patient";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all the appointments of the patient identified by the index number "
            + "used in the displayed patient list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    /**
     * Creates a ListPatientAppointmentsCommand with the specified {@code Index}
     */
    public ListPatientAppointmentsCommand (Index targetIndex) {
        assert targetIndex != null;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPatientList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patientChosen = lastShownList.get(targetIndex.getZeroBased());
        model.updateFilteredAppointmentList(new PatientHasAppointmentPredicate(patientChosen));

        return new CommandResult(MESSAGE_SUCCESS, getTabNumber());
    }

    @Override
    public Index getTabNumber() {
        return Index.fromOneBased(4);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListPatientAppointmentsCommand // instanceof handles nulls
                && targetIndex.equals(((ListPatientAppointmentsCommand) other).targetIndex)
                && getTabNumber().equals(((ListPatientAppointmentsCommand) other).getTabNumber())); // state check
    }
}
