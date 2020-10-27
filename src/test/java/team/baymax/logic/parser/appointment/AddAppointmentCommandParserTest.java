package team.baymax.logic.parser.appointment;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DATETIME_1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESCRIPTION_1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESC_DATETIME_1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESC_DESCRIPTION_1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESC_ID1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_ID1;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.PREAMBLE_WHITESPACE;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static team.baymax.testutil.TypicalDateTimes.DATETIME6;
import static team.baymax.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import team.baymax.logic.commands.appointment.AddAppointmentCommand;
import team.baymax.model.appointment.Description;

public class AddAppointmentCommandParserTest {

    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_DESC_ID1 + VALID_DESC_DATETIME_1
                + VALID_DESC_DESCRIPTION_1, new AddAppointmentCommand(INDEX_FIRST_PATIENT, DATETIME6,
                new Description(VALID_DESCRIPTION_1), new HashSet<>()));

        // zero tags
        assertParseSuccess(parser, VALID_DESC_ID1 + VALID_DESC_DATETIME_1
                + VALID_DESC_DESCRIPTION_1, new AddAppointmentCommand(INDEX_FIRST_PATIENT, DATETIME6,
                new Description(VALID_DESCRIPTION_1), new HashSet<>()));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE);

        // missing ID prefix
        assertParseFailure(parser, VALID_DESC_DATETIME_1 + VALID_DESC_DESCRIPTION_1, expectedMessage);

        // missing DATETIME prefix
        assertParseFailure(parser, VALID_DESC_ID1 + VALID_DESC_DESCRIPTION_1, expectedMessage);

        // missing DESCRIPTION prefix
        assertParseFailure(parser, VALID_DESC_ID1 + VALID_DESC_DATETIME_1, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_ID1 + VALID_DATETIME_1 + VALID_DESCRIPTION_1, expectedMessage);
    }

}
