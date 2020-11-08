package team.baymax.logic.parser.calendar;

import static java.util.Objects.requireNonNull;

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

        month = ParserUtil.parseMonth(args);

        return new MonthCommand(month);
    }
}
