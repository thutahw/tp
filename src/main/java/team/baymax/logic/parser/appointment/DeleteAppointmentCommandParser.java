package team.baymax.logic.parser.appointment;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DATETIME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NAME;
import static team.baymax.logic.parser.ParserUtil.arePrefixesPresent;

import java.util.Optional;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.appointment.DeleteAppointmentCommand;
import team.baymax.logic.parser.ArgumentMultimap;
import team.baymax.logic.parser.ArgumentTokenizer;
import team.baymax.logic.parser.Parser;
import team.baymax.logic.parser.ParserUtil;
import team.baymax.logic.parser.exceptions.ParseException;
import team.baymax.model.patient.Name;
import team.baymax.model.util.datetime.DateTime;

public class DeleteAppointmentCommandParser implements Parser<DeleteAppointmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATETIME);

        if (!argMultimap.getPreamble().isEmpty()) {
            try {
                Index targetIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
                return new DeleteAppointmentCommand(Optional.of(targetIndex));
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeleteAppointmentCommand.MESSAGE_USAGE));
            }
        }

        if (arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DATETIME)) {
            DateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            return new DeleteAppointmentCommand(Optional.of(dateTime), Optional.of(name));
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }
}
