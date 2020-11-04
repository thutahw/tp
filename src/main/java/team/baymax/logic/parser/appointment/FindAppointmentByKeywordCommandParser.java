package team.baymax.logic.parser.appointment;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import team.baymax.logic.commands.appointment.FindAppointmentCommand;
import team.baymax.logic.parser.Parser;
import team.baymax.logic.parser.exceptions.ParseException;
import team.baymax.model.appointment.AppointmentContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindAppointmentCommand object
 */
public class FindAppointmentByKeywordCommandParser implements Parser<FindAppointmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindAppointmentCommand
     * and returns a FindAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindAppointmentCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAppointmentCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");

        return new FindAppointmentCommand(new AppointmentContainsKeywordsPredicate(Arrays.asList(keywords)));
    }
}
