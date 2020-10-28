package team.baymax.logic.parser.appointment;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DATETIME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DURATION;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TAG;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TIME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_INDEX;
import static team.baymax.logic.parser.ParserUtil.arePrefixesPresent;

import java.util.Set;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.appointment.AddAppointmentCommand;
import team.baymax.logic.parser.ArgumentMultimap;
import team.baymax.logic.parser.ArgumentTokenizer;
import team.baymax.logic.parser.Parser;
import team.baymax.logic.parser.ParserUtil;
import team.baymax.logic.parser.exceptions.ParseException;
import team.baymax.model.appointment.Description;
import team.baymax.model.tag.Tag;
import team.baymax.model.util.datetime.DateTime;
import team.baymax.model.util.datetime.Duration;
import team.baymax.model.util.datetime.Time;

/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_DATETIME, PREFIX_TIME, PREFIX_DURATION,
                        PREFIX_DESCRIPTION, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_DESCRIPTION, PREFIX_DURATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        Index id = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        Duration duration = ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            DateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
            return new AddAppointmentCommand(id, dateTime, duration, tagList, description);
        } else if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
            return new AddAppointmentCommand(id, time, duration, tagList, description);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

    }
}
