package team.baymax.logic.parser.calendar;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import team.baymax.logic.commands.calendar.YearCommand;
import team.baymax.logic.parser.Parser;
import team.baymax.logic.parser.ParserUtil;
import team.baymax.logic.parser.exceptions.ParseException;
import team.baymax.model.calendar.Year;

public class YearCommandParser implements Parser<YearCommand> {

    @Override
    public YearCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Year year;

        try {
            year = ParserUtil.parseYear(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    YearCommand.MESSAGE_USAGE), pe);
        }

        return new YearCommand(year);
    }
}
