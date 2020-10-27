package team.baymax.logic.parser.appointment;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESCRIPTION_1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_ID1;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static team.baymax.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;

import org.junit.jupiter.api.Test;

import team.baymax.logic.commands.appointment.ListPatientAppointmentsCommand;

public class ListPatientAppointmentsCommandParserTest {

    private ListPatientAppointmentsCommandParser parser = new ListPatientAppointmentsCommandParser();

    @Test
    public void parse_validField_success() {

        assertParseSuccess(parser, VALID_ID1, new ListPatientAppointmentsCommand(INDEX_FIRST_PATIENT));
    }

    @Test
    public void parse_invalidInput_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListPatientAppointmentsCommand.MESSAGE_USAGE);

        assertParseFailure(parser, VALID_DESCRIPTION_1, expectedMessage);
    }

}
