package team.baymax.logic.parser.appointment;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.INVALID_DESC_DATETIME;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESC_DATETIME_1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_ID1;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static team.baymax.testutil.appointment.TypicalAppointmentIndexes.INDEX_FIRST_APPOINTMENT;
import static team.baymax.testutil.datetime.TypicalDateTimes.DATETIME5;
import static team.baymax.testutil.patient.TypicalFirstNames.FIRST_NAME_ALICE;
import static team.baymax.testutil.patient.TypicalFirstNames.VALID_FIRST_NAME_ALICE;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import team.baymax.logic.commands.appointment.DeleteAppointmentCommand;
import team.baymax.model.util.datetime.DateTime;

public class DeleteAppointmentCommandParserTest {

    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    @Test
    public void parse_validArgsIndex_returnsDeleteCommand() {
        assertParseSuccess(parser, VALID_ID1, new DeleteAppointmentCommand(Optional.of(INDEX_FIRST_APPOINTMENT)));
    }

    @Test
    public void parse_invalidArgsIndex_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsDateTimeAndName_returnsDeleteCommand() {

        assertParseSuccess(parser, VALID_DESC_DATETIME_1 + VALID_FIRST_NAME_ALICE,
                new DeleteAppointmentCommand(Optional.of(DATETIME5), Optional.of(FIRST_NAME_ALICE)));

    }

    @Test
    public void parse_validNameInvalidDateTime_throwsParseException() {

        assertParseFailure(parser, INVALID_DESC_DATETIME + VALID_FIRST_NAME_ALICE,
                DateTime.MESSAGE_CONSTRAINTS);
    }

    //    @Test
    //    public void parse_validDateTimeInvalidName_throwsParseException() {
    //
    //        assertParseFailure(parser, VALID_DESC_DATETIME_1 + INVALID_FIRST_NAME_ALICE,
    //                Name.MESSAGE_CONSTRAINTS);
    //    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAppointmentCommand.MESSAGE_USAGE);
        // empty input
        assertParseFailure(parser, " ", expectedMessage);

        // missing name
        assertParseFailure(parser, VALID_DESC_DATETIME_1, expectedMessage);

        // missing datetime
        assertParseFailure(parser, VALID_FIRST_NAME_ALICE, expectedMessage);

    }

}
