package team.baymax.logic.commands.patient;

import static java.util.Objects.requireNonNull;
import static team.baymax.logic.parser.CliSyntax.PREFIX_GENDER;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NAME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NRIC;
import static team.baymax.logic.parser.CliSyntax.PREFIX_PHONE;
import static team.baymax.logic.parser.CliSyntax.PREFIX_REMARK;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TAG;

import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.CommandResult;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.patient.Patient;
import team.baymax.model.patient.PatientIdenticalPredicate;
import team.baymax.model.util.TabId;

/**
 * Adds a patient to the appointment book.
 */
public class AddPatientCommand extends Command {

    public static final String COMMAND_WORD = "addpatient";

    public static final TabId TAB_ID = TabId.PATIENT;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient to the appointment book. "
            + "Parameters: "
            + PREFIX_NRIC + "Nric "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_GENDER + "GENDER "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S9771234F "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_GENDER + "M "
            + PREFIX_TAG + "asthmatic "
            + PREFIX_TAG + "LTP";

    public static final String MESSAGE_SUCCESS = "New patient added: \n%1$s";
    public static final String MESSAGE_DUPLICATE_PATIENT = "A patient with this NRIC already exists in the "
            + "appointment book";

    private final Patient toAdd;

    /**
     * Creates an AddPatientCommand to add the specified {@code Patient}
     */
    public AddPatientCommand(Patient patient) {
        requireNonNull(patient);
        toAdd = patient;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPatient(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PATIENT);
        }

        model.addPatient(toAdd);

        model.updateFilteredPatientList(new PatientIdenticalPredicate(toAdd));

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), getTabId());
    }

    public Patient getToAdd() {
        return toAdd;
    }

    @Override
    public TabId getTabId() {
        return TAB_ID;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPatientCommand // instanceof handles nulls
                && toAdd.equals(((AddPatientCommand) other).toAdd));
    }
}
