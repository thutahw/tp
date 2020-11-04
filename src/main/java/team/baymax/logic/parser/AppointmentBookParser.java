package team.baymax.logic.parser;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.appointment.AddAppointmentCommand;
import team.baymax.logic.commands.appointment.DeleteAppointmentCommand;
import team.baymax.logic.commands.appointment.EditAppointmentCommand;
import team.baymax.logic.commands.appointment.FindAppointmentCommand;
import team.baymax.logic.commands.appointment.ListAppointmentCommand;
import team.baymax.logic.commands.appointment.ListPatientAppointmentsCommand;
import team.baymax.logic.commands.appointment.MarkAppointmentDoneCommand;
import team.baymax.logic.commands.appointment.MarkAppointmentMissedCommand;
import team.baymax.logic.commands.calendar.DayCommand;
import team.baymax.logic.commands.calendar.MonthCommand;
import team.baymax.logic.commands.calendar.YearCommand;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.logic.commands.general.ClearCommand;
import team.baymax.logic.commands.general.ExitCommand;
import team.baymax.logic.commands.general.HelpCommand;
import team.baymax.logic.commands.general.TabCommand;
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
import team.baymax.logic.parser.appointment.MarkAppointmentMissedCommandParser;
import team.baymax.logic.parser.calendar.DayCommandParser;
import team.baymax.logic.parser.calendar.MonthCommandParser;
import team.baymax.logic.parser.calendar.YearCommandParser;
import team.baymax.logic.parser.exceptions.ParseException;
import team.baymax.logic.parser.patient.AddPatientCommandParser;
import team.baymax.logic.parser.patient.DeletePatientCommandParser;
import team.baymax.logic.parser.patient.EditPatientCommandParser;
import team.baymax.logic.parser.patient.FindPatientCommandParser;
import team.baymax.logic.parser.patient.RemarkPatientCommandParser;
import team.baymax.model.util.TabId;

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
    public Command parseCommand(String userInput) throws ParseException, CommandException {
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

        case MarkAppointmentMissedCommand.COMMAND_WORD:
            return new MarkAppointmentMissedCommandParser().parse(arguments);

        case FindAppointmentCommand.COMMAND_WORD:
            return new FindAppointmentByKeywordCommandParser().parse(arguments);

        case DayCommand.COMMAND_WORD:
            return new DayCommandParser().parse(arguments);

        case MonthCommand.COMMAND_WORD:
            return new MonthCommandParser().parse(arguments);

        case YearCommand.COMMAND_WORD:
            return new YearCommandParser().parse(arguments);

        case TabCommand.COMMAND_WORD_DASHBOARD:
            return new TabCommand(Index.fromOneBased(TabId.DASHBOARD.getTabNumber()));

        case TabCommand.COMMAND_WORD_CALENDAR:
            return new TabCommand(Index.fromOneBased(TabId.CALENDAR.getTabNumber()));

        case TabCommand.COMMAND_WORD_SCHEDULE:
            return new TabCommand(Index.fromOneBased(TabId.SCHEDULE.getTabNumber()));

        case TabCommand.COMMAND_WORD_APPOINTMENT:
            return new TabCommand(Index.fromOneBased(TabId.APPOINTMENT.getTabNumber()));

        case TabCommand.COMMAND_WORD_PATIENT:
            return new TabCommand(Index.fromOneBased(TabId.PATIENT.getTabNumber()));

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
