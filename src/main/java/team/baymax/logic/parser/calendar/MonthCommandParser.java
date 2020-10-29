package team.baymax.logic.parser.calendar;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import team.baymax.logic.commands.calendar.MonthCommand;
import team.baymax.logic.parser.Parser;
import team.baymax.logic.parser.ParserUtil;
import team.baymax.logic.parser.exceptions.ParseException;
import team.baymax.model.util.datetime.Month;

public class MonthCommandParser implements Parser<MonthCommand> {

    @Override
    public MonthCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Month month;

        try {
            month = ParserUtil.parseMonth(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MonthCommand.MESSAGE_USAGE), pe);
        }

        return new MonthCommand(month);
    }
}
