package team.baymax.logic.parser.calendar;

import static team.baymax.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static team.baymax.testutil.calendar.TypicalDays.FIRST;

import org.junit.jupiter.api.Test;

import team.baymax.logic.commands.calendar.DayCommand;
import team.baymax.model.util.datetime.Day;

public class DayCommandParserTest {

    private DayCommandParser parser = new DayCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, "1", new DayCommand(FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty string
        assertParseFailure(parser, "", Day.MESSAGE_CONSTRAINTS);

        // space character only
        assertParseFailure(parser, " ", Day.MESSAGE_CONSTRAINTS);

        // non-integer
        assertParseFailure(parser, "a", Day.MESSAGE_CONSTRAINTS);

        // non-positive integer
        assertParseFailure(parser, "-1", Day.MESSAGE_CONSTRAINTS);

        // zero
        assertParseFailure(parser, "0", Day.MESSAGE_CONSTRAINTS);

        // integer exceeds maximum limit
        assertParseFailure(parser, "32", Day.MESSAGE_CONSTRAINTS);
    }

}
