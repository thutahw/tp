package team.baymax.logic.parser.calendar;

import static java.util.Objects.requireNonNull;

import team.baymax.logic.commands.calendar.YearCommand;
import team.baymax.logic.parser.Parser;
import team.baymax.logic.parser.ParserUtil;
import team.baymax.logic.parser.exceptions.ParseException;
import team.baymax.model.util.datetime.Year;

public class YearCommandParser implements Parser<YearCommand> {

    @Override
    public YearCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Year year;

        year = ParserUtil.parseYear(args);

        return new YearCommand(year);
    }
}
