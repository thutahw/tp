package team.baymax.logic.commands.appointment;

import static java.util.Objects.requireNonNull;
import static team.baymax.logic.parser.CliSyntax.PREFIX_INDEX;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NAME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.List;

import team.baymax.commons.core.Messages;
import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.Nric;
import team.baymax.model.patient.Patient;
import team.baymax.model.patient.PatientHasAppointmentPredicate;
import team.baymax.model.util.TabId;

/**
 * Lists all the appointments of the chosen patient to the user.
 */
public class ListPatientAppointmentsCommand extends Command {

    public static final String COMMAND_WORD = "listapptof";
    public static final TabId TAB_ID = TabId.APPOINTMENT;

    public static final String MESSAGE_SUCCESS = "Listed all the appointments of the patient";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all the appointments of the patient identified by the index number "
            + "used in the displayed patient list, a name or an nric number.\n"
            + "Parameters: "
            + PREFIX_INDEX + " INDEX "
            + "(OR "
            + PREFIX_NRIC + "PATIENT_NRIC "
            + "OR "
            + PREFIX_NAME + "PATIENT_NAME) \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Alex Yeoh";

    private final Index targetIndex;
    private final Nric nric;
    private final Name name;

    /**
     * Creates a ListPatientAppointmentsCommand with the specified {@code Index}
     */
    public ListPatientAppointmentsCommand (Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.nric = null;
        this.name = null;
    }

    /**
     * Creates a ListPatientAppointmentsCommand with the specified {@code Nric}
     */
    public ListPatientAppointmentsCommand (Nric nric) {
        requireNonNull(nric);
        this.targetIndex = null;
        this.nric = nric;
        this.name = null;
    }

    /**
     * Creates a ListPatientAppointmentsCommand with the specified {@code Name}
     */
    public ListPatientAppointmentsCommand (Name name) {
        requireNonNull(name);
        this.targetIndex = null;
        this.nric = null;
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Patient patientChosen;

        if (targetIndex != null) {
            List<Patient> lastShownList = model.getFilteredPatientList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
            }

            patientChosen = lastShownList.get(targetIndex.getZeroBased());
        } else if (nric != null) {
            PatientManager patientManager = (PatientManager) model.getPatientManager();
            patientChosen = patientManager.getPatient(nric);
        } else if (name != null) {
            PatientManager patientManager = (PatientManager) model.getPatientManager();
            patientChosen = patientManager.getPatient(name);
        } else {
            throw new CommandException(Messages.MESSAGE_PATIENT_NOT_FOUND);
        }

        model.updateFilteredAppointmentList(new PatientHasAppointmentPredicate(patientChosen));
        return new CommandResult(MESSAGE_SUCCESS, getTabId());
    }

    @Override
    public TabId getTabId() {
        return TAB_ID;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListPatientAppointmentsCommand // instanceof handles nulls
                && targetIndex.equals(((ListPatientAppointmentsCommand) other).targetIndex)); // state check
    }
}
