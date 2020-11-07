package team.baymax.logic.parser.calendar;

import static team.baymax.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static team.baymax.testutil.calendar.TypicalMonths.JANUARY;

import org.junit.jupiter.api.Test;

import team.baymax.logic.commands.calendar.MonthCommand;
import team.baymax.model.util.datetime.Month;

public class MonthCommandParserTest {

    private MonthCommandParser parser = new MonthCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, "1", new MonthCommand(JANUARY));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty string
        assertParseFailure(parser, "", Month.MESSAGE_CONSTRAINTS);

        // space character only
        assertParseFailure(parser, " ", Month.MESSAGE_CONSTRAINTS);

        // non-integer
        assertParseFailure(parser, "a", Month.MESSAGE_CONSTRAINTS);

        // non-positive integer
        assertParseFailure(parser, "-1", Month.MESSAGE_CONSTRAINTS);

        // zero
        assertParseFailure(parser, "0", Month.MESSAGE_CONSTRAINTS);

        // integer exceeds maximum limit
        assertParseFailure(parser, "13", Month.MESSAGE_CONSTRAINTS);
    }

}
