package team.baymax.logic.parser.appointment;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DATETIME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_ID;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NAME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NRIC;

import team.baymax.commons.core.index.Index;
import team.baymax.commons.core.time.DateTime;
import team.baymax.logic.commands.appointment.MarkAppointmentDoneCommand;
import team.baymax.logic.parser.ArgumentMultimap;
import team.baymax.logic.parser.ArgumentTokenizer;
import team.baymax.logic.parser.Parser;
import team.baymax.logic.parser.ParserUtil;
import team.baymax.logic.parser.exceptions.ParseException;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.Nric;

public class MarkAppointmentDoneCommandParser implements Parser<MarkAppointmentDoneCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MarkAppointmentDoneCommand
     * and returns a MarkAppointmentDoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkAppointmentDoneCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_DATETIME, PREFIX_NAME, PREFIX_NRIC);

        if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ID).get());
            return new MarkAppointmentDoneCommand(index);
        } else if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            DateTime dt;
            try {
                dt = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(pe.getMessage()), pe);
            }
            return new MarkAppointmentDoneCommand(name, dt);
        } else if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
            DateTime dt;
            try {
                dt = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(pe.getMessage()), pe);
            }
            return new MarkAppointmentDoneCommand(nric, dt);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAppointmentDoneCommand.MESSAGE_USAGE));
        }
    }
}
