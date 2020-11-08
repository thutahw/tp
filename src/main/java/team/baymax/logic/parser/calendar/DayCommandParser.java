package team.baymax.logic.parser.calendar;

import static java.util.Objects.requireNonNull;

import team.baymax.logic.commands.calendar.DayCommand;
import team.baymax.logic.parser.Parser;
import team.baymax.logic.parser.ParserUtil;
import team.baymax.logic.parser.exceptions.ParseException;
import team.baymax.model.util.datetime.Day;

public class DayCommandParser implements Parser<DayCommand> {

    @Override
    public DayCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Day day;

        day = ParserUtil.parseDayOfMonth(args);

        return new DayCommand(day);
    }
}
