package team.baymax.logic.parser.appointment;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NAME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NRIC;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.appointment.ListPatientAppointmentsCommand;
import team.baymax.logic.parser.ArgumentMultimap;
import team.baymax.logic.parser.ArgumentTokenizer;
import team.baymax.logic.parser.Parser;
import team.baymax.logic.parser.ParserUtil;
import team.baymax.logic.parser.exceptions.ParseException;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.Nric;

/**
 * Parses input arguments and creates a new ListPatientAppointmentsCommand object
 */
public class ListPatientAppointmentsCommandParser implements Parser<ListPatientAppointmentsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListPatientAppointmentsCommand
     * and returns a ListPatientAppointmentsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ListPatientAppointmentsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NRIC);

        if (!argMultimap.getPreamble().isEmpty()) {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            return new ListPatientAppointmentsCommand(index);
        } else if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            return new ListPatientAppointmentsCommand(name);
        } else if (argMultimap.getValue(PREFIX_NRIC).isPresent()) {
            Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
            return new ListPatientAppointmentsCommand(nric);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListPatientAppointmentsCommand.MESSAGE_USAGE));
        }
    }
}
