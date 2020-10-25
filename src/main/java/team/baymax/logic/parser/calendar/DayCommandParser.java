package team.baymax.logic.parser.calendar;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import team.baymax.logic.commands.calendar.DayCommand;
import team.baymax.logic.parser.Parser;
import team.baymax.logic.parser.ParserUtil;
import team.baymax.logic.parser.exceptions.ParseException;
import team.baymax.model.calendar.Day;

public class DayCommandParser implements Parser<DayCommand> {

    @Override
    public DayCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Day day;

        try {
            day = ParserUtil.parseDayOfMonth(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DayCommand.MESSAGE_USAGE), pe);
        }

        return new DayCommand(day);
    }
}
