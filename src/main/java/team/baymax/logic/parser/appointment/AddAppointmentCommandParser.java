package team.baymax.logic.parser.appointment;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DATETIME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DURATION;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NRIC;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TAG;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TIME;
import static team.baymax.logic.parser.ParserUtil.arePrefixesPresent;

import java.util.Optional;
import java.util.Set;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.appointment.AddAppointmentCommand;
import team.baymax.logic.parser.ArgumentMultimap;
import team.baymax.logic.parser.ArgumentTokenizer;
import team.baymax.logic.parser.Parser;
import team.baymax.logic.parser.ParserUtil;
import team.baymax.logic.parser.exceptions.ParseException;
import team.baymax.model.appointment.Description;
import team.baymax.model.patient.Nric;
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
                ArgumentTokenizer.tokenize(args, PREFIX_NRIC, PREFIX_DATETIME, PREFIX_TIME, PREFIX_DURATION,
                        PREFIX_DESCRIPTION, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_DURATION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getValue(PREFIX_NRIC).isPresent()
                && argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_INDEX_AND_NRIC_BOTH_EMPTY));
        }

        if (!argMultimap.getValue(PREFIX_DATETIME).isPresent()
                && !argMultimap.getValue(PREFIX_TIME).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_DATETIME_AND_TIME_BOTH_EMPTY));
        }

        Optional<Index> patientIndex;
        Optional<Nric> patientNric;
        Optional<DateTime> dateTime;
        Optional<Time> time;

        try {
            patientIndex = Optional.ofNullable(ParserUtil.parseIndex(argMultimap.getPreamble()));
        } catch (ParseException pe) {
            patientIndex = Optional.empty();
        }

        try {
            patientNric = Optional.ofNullable(ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).orElse("")));
        } catch (ParseException pe) {
            patientNric = Optional.empty();
        }

        try {
            dateTime = Optional.ofNullable(
                    ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).orElse("")));
        } catch (ParseException pe) {
            dateTime = Optional.empty();
        }

        try {
            time = Optional.ofNullable(
                    ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).orElse("")));
        } catch (ParseException pe) {
            time = Optional.empty();
        }

        Duration duration = ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        return new AddAppointmentCommand(patientIndex, patientNric, dateTime, time, duration, description, tagList);

    }
}
