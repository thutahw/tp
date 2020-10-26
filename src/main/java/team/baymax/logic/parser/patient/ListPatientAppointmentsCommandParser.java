package team.baymax.logic.parser.patient;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.patient.ListPatientAppointmentsCommand;
import team.baymax.logic.parser.Parser;
import team.baymax.logic.parser.ParserUtil;
import team.baymax.logic.parser.exceptions.ParseException;

public class ListPatientAppointmentsCommandParser implements Parser<ListPatientAppointmentsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListPatientAppointmentsCommand
     * and returns a ListPatientAppointmentsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ListPatientAppointmentsCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ListPatientAppointmentsCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListPatientAppointmentsCommand.MESSAGE_USAGE), pe);
        }
    }
}
