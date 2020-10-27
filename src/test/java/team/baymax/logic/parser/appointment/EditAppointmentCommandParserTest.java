package team.baymax.logic.parser.appointment;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESC_TAG_DIABETIC;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESC_TAG_LTP;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_TAG_LTP;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_TAG_DIABETIC;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TAG;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static team.baymax.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static team.baymax.testutil.TypicalIndexes.INDEX_THIRD_APPOINTMENT;

import org.junit.jupiter.api.Test;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.appointment.EditAppointmentCommand;
import team.baymax.logic.commands.appointment.EditAppointmentDescriptor;
import team.baymax.testutil.EditAppointmentDescriptorBuilder;

public class EditAppointmentCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAppointmentCommand.MESSAGE_USAGE);

    private EditAppointmentCommandParser parser = new EditAppointmentCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DESC_TAG_DIABETIC, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditAppointmentCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_DESC_TAG_DIABETIC, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_DESC_TAG_DIABETIC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_APPOINTMENT;
        String userInput = targetIndex.getOneBased() + VALID_DESC_TAG_DIABETIC;
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withTags(VALID_TAG_DIABETIC).build();
        EditAppointmentCommand expectedCommand = new EditAppointmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + VALID_DESC_TAG_LTP;
        descriptor = new EditAppointmentDescriptorBuilder().withTags(VALID_TAG_LTP).build();
        expectedCommand = new EditAppointmentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_APPOINTMENT;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder().withTags().build();
        EditAppointmentCommand expectedCommand = new EditAppointmentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
