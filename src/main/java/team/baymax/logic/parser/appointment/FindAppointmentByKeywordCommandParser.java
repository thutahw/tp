package team.baymax.logic.parser.appointment;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import team.baymax.logic.commands.appointment.FindAppointmentByKeywordCommand;
import team.baymax.logic.parser.Parser;
import team.baymax.logic.parser.exceptions.ParseException;
import team.baymax.model.appointment.AppointmentContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new FindAppointmentByKeywordCommand object
 */
public class FindAppointmentByKeywordCommandParser implements Parser<FindAppointmentByKeywordCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindAppointmentByKeywordCommand
     * and returns a FindAppointmentByKeywordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindAppointmentByKeywordCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAppointmentByKeywordCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");

        return new FindAppointmentByKeywordCommand(new AppointmentContainsKeywordPredicate(Arrays.asList(keywords)));
    }
}
