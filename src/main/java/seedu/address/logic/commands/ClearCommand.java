package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.patient.PatientCommand;
import seedu.address.model.Model;
import seedu.address.model.listmanagers.AppointmentManager;
import seedu.address.model.listmanagers.PatientManager;

/**
 * Clears the patient manager and appointment manager.
 */
public class ClearCommand extends PatientCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Patients and appointments have been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setPatientManager(new PatientManager());
        model.setAppointmentManager(new AppointmentManager());
        return new CommandResult(MESSAGE_SUCCESS, TAB_NUMBER);
    }
}
