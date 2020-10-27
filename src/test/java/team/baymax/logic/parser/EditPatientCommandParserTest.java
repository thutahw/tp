package team.baymax.logic.parser;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.GENDER_DESC_AMY;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.GENDER_DESC_BOB;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.INVALID_GENDER_DESC;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.INVALID_NAME_DESC;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.INVALID_PHONE_DESC;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.INVALID_TAG_DESC;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.NAME_DESC_AMY;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.PHONE_DESC_AMY;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.PHONE_DESC_BOB;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.TAG_DESC_FRIEND;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.TAG_DESC_HUSBAND;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_GENDER_AMY;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_GENDER_BOB;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_NAME_AMY;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_PHONE_AMY;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_PHONE_BOB;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_TAG_DIABETIC;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_TAG_LTP;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TAG;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static team.baymax.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static team.baymax.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static team.baymax.testutil.TypicalIndexes.INDEX_THIRD_PATIENT;

import org.junit.jupiter.api.Test;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.patient.EditPatientCommand;
import team.baymax.logic.parser.patient.EditPatientCommandParser;
import team.baymax.model.patient.Gender;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.Phone;
import team.baymax.model.tag.Tag;
import team.baymax.testutil.EditPatientDescriptorBuilder;

public class EditPatientCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPatientCommand.MESSAGE_USAGE);

    private EditPatientCommandParser parser = new EditPatientCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditPatientCommand.MESSAGE_NOT_EDITED);

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
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_GENDER_DESC, Gender.MESSAGE_CONSTRAINTS); // invalid gender
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid gender
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + GENDER_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_GENDER_DESC + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PATIENT;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + GENDER_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB)
                .withGender(VALID_GENDER_AMY)
                .withTags(VALID_TAG_DIABETIC, VALID_TAG_LTP)
                .build();
        EditPatientCommand expectedCommand = new EditPatientCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PATIENT;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + GENDER_DESC_AMY;

        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB)
                .withGender(VALID_GENDER_AMY).build();
        EditPatientCommand expectedCommand = new EditPatientCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PATIENT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
                .withName(VALID_NAME_AMY).build();
        EditPatientCommand expectedCommand = new EditPatientCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditPatientCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // gender
        userInput = targetIndex.getOneBased() + GENDER_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withGender(VALID_GENDER_AMY).build();
        expectedCommand = new EditPatientCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditPatientDescriptorBuilder().withTags(VALID_TAG_LTP).build();
        expectedCommand = new EditPatientCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PATIENT;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + GENDER_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + GENDER_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + GENDER_DESC_BOB + TAG_DESC_HUSBAND;

        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB)
                .withGender(VALID_GENDER_BOB)
                .withTags(VALID_TAG_LTP, VALID_TAG_DIABETIC)
                .build();
        EditPatientCommand expectedCommand = new EditPatientCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PATIENT;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).build();
        EditPatientCommand expectedCommand = new EditPatientCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + GENDER_DESC_BOB + INVALID_PHONE_DESC
                + PHONE_DESC_BOB;
        descriptor = new EditPatientDescriptorBuilder().withPhone(VALID_PHONE_BOB).withGender(VALID_GENDER_BOB)
                .build();
        expectedCommand = new EditPatientCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PATIENT;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withTags().build();
        EditPatientCommand expectedCommand = new EditPatientCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
