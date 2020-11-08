package team.baymax.logic.parser.calendar;

import static team.baymax.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static team.baymax.testutil.calendar.TypicalYears.YEAR_2020;

import org.junit.jupiter.api.Test;

import team.baymax.logic.commands.calendar.YearCommand;
import team.baymax.model.util.datetime.Year;

public class YearCommandParserTest {

    private YearCommandParser parser = new YearCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, "2020", new YearCommand(YEAR_2020));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty string
        assertParseFailure(parser, "", Year.MESSAGE_CONSTRAINTS);

        // space character only
        assertParseFailure(parser, " ", Year.MESSAGE_CONSTRAINTS);

        // non-integer
        assertParseFailure(parser, "a", Year.MESSAGE_CONSTRAINTS);

        // non-positive integer
        assertParseFailure(parser, "-1", Year.MESSAGE_CONSTRAINTS);

        // zero
        assertParseFailure(parser, "0", Year.MESSAGE_CONSTRAINTS);

        // year before 2000
        assertParseFailure(parser, "1899", Year.MESSAGE_CONSTRAINTS);
    }

}
