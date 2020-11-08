package team.baymax.logic.parser.appointment;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.INVALID_DESC_DATETIME;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.INVALID_DESC_DESCRIPTION;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.INVALID_DESC_TAG;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESC_DATETIME_1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESC_TAG_1HR;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESC_TAG_DRGOH;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_ID1;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TAG;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.baymax.testutil.patient.PatientUtil.NAME_DESC_AMY;

import org.junit.jupiter.api.Test;

import team.baymax.logic.commands.appointment.EditAppointmentCommand;
import team.baymax.model.appointment.Description;
import team.baymax.model.tag.Tag;
import team.baymax.model.util.datetime.DateTime;

public class EditAppointmentCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAppointmentCommand.MESSAGE_USAGE);

    private EditAppointmentCommandParser parser = new EditAppointmentCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DESC_DATETIME_1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, VALID_ID1, EditAppointmentCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid datetime
        assertParseFailure(parser, VALID_ID1 + INVALID_DESC_DATETIME, DateTime.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, VALID_ID1 + INVALID_DESC_TAG, Tag.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, VALID_ID1 + INVALID_DESC_DESCRIPTION, Description.MESSAGE_CONSTRAINTS);


        // invalid datetime followed by valid tag
        assertParseFailure(parser, VALID_ID1 + INVALID_DESC_DATETIME + VALID_DESC_TAG_1HR,
                DateTime.MESSAGE_CONSTRAINTS);

        // valid datetime followed by invalid tag.
        assertParseFailure(parser, VALID_ID1 + VALID_DESC_DATETIME_1 + INVALID_DESC_TAG, Tag.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Appointment} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, VALID_ID1 + VALID_DESC_TAG_1HR + VALID_DESC_TAG_DRGOH + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_ID1 + VALID_DESC_TAG_1HR + TAG_EMPTY + VALID_DESC_TAG_DRGOH,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_ID1 + TAG_EMPTY + VALID_DESC_TAG_1HR + VALID_DESC_TAG_DRGOH,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, VALID_ID1 + INVALID_DESC_TAG + INVALID_DESC_DESCRIPTION
                        + VALID_DESC_DATETIME_1, Description.MESSAGE_CONSTRAINTS);
    }
}
