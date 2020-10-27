package team.baymax.logic.parser;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import team.baymax.logic.commands.ClearCommand;
import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.ExitCommand;
import team.baymax.logic.commands.HelpCommand;
import team.baymax.logic.commands.appointment.AddAppointmentCommand;
import team.baymax.logic.commands.appointment.DeleteAppointmentCommand;
import team.baymax.logic.commands.appointment.EditAppointmentCommand;
import team.baymax.logic.commands.appointment.FindAppointmentByKeywordCommand;
import team.baymax.logic.commands.appointment.ListAppointmentCommand;
import team.baymax.logic.commands.appointment.ListPatientAppointmentsCommand;
import team.baymax.logic.commands.appointment.MarkAppointmentDoneCommand;
import team.baymax.logic.commands.patient.AddPatientCommand;
import team.baymax.logic.commands.patient.DeletePatientCommand;
import team.baymax.logic.commands.patient.EditPatientCommand;
import team.baymax.logic.commands.patient.FindPatientCommand;
import team.baymax.logic.commands.patient.ListPatientCommand;
import team.baymax.logic.commands.patient.RemarkPatientCommand;
import team.baymax.logic.parser.appointment.AddAppointmentCommandParser;
import team.baymax.logic.parser.appointment.DeleteAppointmentCommandParser;
import team.baymax.logic.parser.appointment.EditAppointmentCommandParser;
import team.baymax.logic.parser.appointment.FindAppointmentByKeywordCommandParser;
import team.baymax.logic.parser.appointment.ListPatientAppointmentsCommandParser;
import team.baymax.logic.parser.appointment.MarkAppointmentDoneCommandParser;
import team.baymax.logic.parser.exceptions.ParseException;
import team.baymax.logic.parser.patient.AddPatientCommandParser;
import team.baymax.logic.parser.patient.DeletePatientCommandParser;
import team.baymax.logic.parser.patient.EditPatientCommandParser;
import team.baymax.logic.parser.patient.FindPatientCommandParser;
import team.baymax.logic.parser.patient.RemarkPatientCommandParser;

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

        case ListPatientAppointmentsCommand.COMMAND_WORD:
            return new ListPatientAppointmentsCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case RemarkPatientCommand.COMMAND_WORD:
            return new RemarkPatientCommandParser().parse(arguments);

        case AddAppointmentCommand.COMMAND_WORD:
            return new AddAppointmentCommandParser().parse(arguments);

        case ListAppointmentCommand.COMMAND_WORD:
            return new ListAppointmentCommand();

        case EditAppointmentCommand.COMMAND_WORD:
            return new EditAppointmentCommandParser().parse(arguments);

        case DeleteAppointmentCommand.COMMAND_WORD:
            return new DeleteAppointmentCommandParser().parse(arguments);

        case MarkAppointmentDoneCommand.COMMAND_WORD:
            return new MarkAppointmentDoneCommandParser().parse(arguments);

        case FindAppointmentByKeywordCommand.COMMAND_WORD:
            return new FindAppointmentByKeywordCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
