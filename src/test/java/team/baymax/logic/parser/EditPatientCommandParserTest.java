package team.baymax.logic.parser;

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
import team.baymax.commons.core.Messages;
import team.baymax.logic.commands.patient.PatientCommandTestUtil;

public class EditPatientCommandParserTest {

    private static final String TAG_EMPTY = " " + CliSyntax.PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditPatientCommand.MESSAGE_USAGE);

    private EditPatientCommandParser parser = new EditPatientCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, PatientCommandTestUtil.VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditPatientCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PatientCommandTestUtil.NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PatientCommandTestUtil.NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + PatientCommandTestUtil.INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + PatientCommandTestUtil.INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + PatientCommandTestUtil.INVALID_GENDER_DESC, Gender.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + PatientCommandTestUtil.INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid gender
        assertParseFailure(parser, "1" + PatientCommandTestUtil.INVALID_PHONE_DESC + PatientCommandTestUtil.GENDER_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PatientCommandTestUtil.PHONE_DESC_BOB + PatientCommandTestUtil.INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + PatientCommandTestUtil.TAG_DESC_FRIEND + PatientCommandTestUtil.TAG_DESC_HUSBAND + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + PatientCommandTestUtil.TAG_DESC_FRIEND + TAG_EMPTY + PatientCommandTestUtil.TAG_DESC_HUSBAND,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + PatientCommandTestUtil.TAG_DESC_FRIEND + PatientCommandTestUtil.TAG_DESC_HUSBAND,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + PatientCommandTestUtil.INVALID_NAME_DESC + PatientCommandTestUtil.INVALID_GENDER_DESC + PatientCommandTestUtil.VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PATIENT;
        String userInput = targetIndex.getOneBased() + PatientCommandTestUtil.PHONE_DESC_BOB + PatientCommandTestUtil.TAG_DESC_HUSBAND
                + PatientCommandTestUtil.GENDER_DESC_AMY + PatientCommandTestUtil.NAME_DESC_AMY + PatientCommandTestUtil.TAG_DESC_FRIEND;

        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withName(PatientCommandTestUtil.VALID_NAME_AMY)
                .withPhone(PatientCommandTestUtil.VALID_PHONE_BOB).withGender(PatientCommandTestUtil.VALID_GENDER_AMY)
                .withTags(PatientCommandTestUtil.VALID_TAG_HUSBAND, PatientCommandTestUtil.VALID_TAG_FRIEND).build();
        EditPatientCommand expectedCommand = new EditPatientCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PATIENT;
        String userInput = targetIndex.getOneBased() + PatientCommandTestUtil.PHONE_DESC_BOB + PatientCommandTestUtil.GENDER_DESC_AMY;

        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
                .withPhone(PatientCommandTestUtil.VALID_PHONE_BOB)
                .withGender(PatientCommandTestUtil.VALID_GENDER_AMY).build();
        EditPatientCommand expectedCommand = new EditPatientCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PATIENT;
        String userInput = targetIndex.getOneBased() + PatientCommandTestUtil.NAME_DESC_AMY;
        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
                .withName(PatientCommandTestUtil.VALID_NAME_AMY).build();
        EditPatientCommand expectedCommand = new EditPatientCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PatientCommandTestUtil.PHONE_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withPhone(PatientCommandTestUtil.VALID_PHONE_AMY).build();
        expectedCommand = new EditPatientCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // gender
        userInput = targetIndex.getOneBased() + PatientCommandTestUtil.GENDER_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withGender(PatientCommandTestUtil.VALID_GENDER_AMY).build();
        expectedCommand = new EditPatientCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + PatientCommandTestUtil.TAG_DESC_FRIEND;
        descriptor = new EditPatientDescriptorBuilder().withTags(PatientCommandTestUtil.VALID_TAG_FRIEND).build();
        expectedCommand = new EditPatientCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PATIENT;
        String userInput = targetIndex.getOneBased() + PatientCommandTestUtil.PHONE_DESC_AMY + PatientCommandTestUtil.GENDER_DESC_AMY
                + PatientCommandTestUtil.TAG_DESC_FRIEND + PatientCommandTestUtil.PHONE_DESC_AMY + PatientCommandTestUtil.GENDER_DESC_AMY + PatientCommandTestUtil.TAG_DESC_FRIEND
                + PatientCommandTestUtil.PHONE_DESC_BOB + PatientCommandTestUtil.GENDER_DESC_BOB + PatientCommandTestUtil.TAG_DESC_HUSBAND;

        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withPhone(PatientCommandTestUtil.VALID_PHONE_BOB)
                .withGender(PatientCommandTestUtil.VALID_GENDER_BOB).withTags(PatientCommandTestUtil.VALID_TAG_FRIEND, PatientCommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        EditPatientCommand expectedCommand = new EditPatientCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PATIENT;
        String userInput = targetIndex.getOneBased() + PatientCommandTestUtil.INVALID_PHONE_DESC + PatientCommandTestUtil.PHONE_DESC_BOB;
        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
                .withPhone(PatientCommandTestUtil.VALID_PHONE_BOB).build();
        EditPatientCommand expectedCommand = new EditPatientCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + PatientCommandTestUtil.GENDER_DESC_BOB + PatientCommandTestUtil.INVALID_PHONE_DESC
                + PatientCommandTestUtil.PHONE_DESC_BOB;
        descriptor = new EditPatientDescriptorBuilder().withPhone(PatientCommandTestUtil.VALID_PHONE_BOB).withGender(PatientCommandTestUtil.VALID_GENDER_BOB)
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
