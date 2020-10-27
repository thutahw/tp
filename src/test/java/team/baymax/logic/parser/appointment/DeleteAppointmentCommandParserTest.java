package team.baymax.logic.parser.appointment;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESC_DATETIME_1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESC_ID1;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static team.baymax.testutil.TypicalDateTimes.DATETIME6;
import static team.baymax.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;

import org.junit.jupiter.api.Test;

import team.baymax.logic.commands.appointment.DeleteAppointmentCommand;

public class DeleteAppointmentCommandParserTest {

    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {

        assertParseSuccess(parser, VALID_DESC_ID1 + VALID_DESC_DATETIME_1,
                new DeleteAppointmentCommand(INDEX_FIRST_APPOINTMENT, DATETIME6));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }
}
