package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.appointment.AddAppointmentCommand;
import seedu.address.logic.commands.patient.AddPatientCommand;
import seedu.address.logic.commands.patient.DeletePatientCommand;
import seedu.address.logic.commands.patient.EditPatientCommand;
import seedu.address.logic.commands.patient.FindPatientCommand;
import seedu.address.logic.commands.patient.ListPatientCommand;
import seedu.address.logic.commands.patient.RemarkPatientCommand;
import seedu.address.logic.parser.appointment.AddAppointmentCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.patient.AddPatientCommandParser;
import seedu.address.logic.parser.patient.DeletePatientCommandParser;
import seedu.address.logic.parser.patient.EditPatientCommandParser;
import seedu.address.logic.parser.patient.FindPatientCommandParser;
import seedu.address.logic.parser.patient.RemarkPatientCommandParser;


/**
 * Parses user input.
 */
public class AppointmentBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddPatientCommand.COMMAND_WORD:
            return new AddPatientCommandParser().parse(arguments);

        case EditPatientCommand.COMMAND_WORD:
            return new EditPatientCommandParser().parse(arguments);

        case DeletePatientCommand.COMMAND_WORD:
            return new DeletePatientCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindPatientCommand.COMMAND_WORD:
            return new FindPatientCommandParser().parse(arguments);

        case ListPatientCommand.COMMAND_WORD:
            return new ListPatientCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case RemarkPatientCommand.COMMAND_WORD:
            return new RemarkPatientCommandParser().parse(arguments);

        case AddAppointmentCommand.COMMAND_WORD:
            return new AddAppointmentCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
