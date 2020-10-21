package team.baymax.logic.commands;

import static java.util.Objects.requireNonNull;

import team.baymax.commons.core.index.Index;
import team.baymax.model.Model;
import team.baymax.model.modelmanagers.AppointmentManager;
import team.baymax.model.modelmanagers.PatientManager;

/**
 * Clears the patient manager and appointment manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Patients and appointments have been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setPatientManager(new PatientManager());
        model.setAppointmentManager(new AppointmentManager());
        return new CommandResult(MESSAGE_SUCCESS, getTabNumber());
    }

    @Override
    public Index getTabNumber() {
        return Index.fromOneBased(1);
    }
}
