package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPatients.AMY;
import static seedu.address.testutil.TypicalPatients.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.patient.AddPatientCommand;
import seedu.address.logic.parser.patient.AddPatientCommandParser;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PatientBuilder;

public class AddPatientCommandParserTest {
    private AddPatientCommandParser parser = new AddPatientCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Patient expectedPatient = new PatientBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + GENDER_DESC_BOB + TAG_DESC_FRIEND + REMARK_DESC_BOB, new AddPatientCommand(expectedPatient));

        // multiple names - last name accepted
        assertParseSuccess(parser, NRIC_DESC_BOB + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + GENDER_DESC_BOB + TAG_DESC_FRIEND + REMARK_DESC_BOB, new AddPatientCommand(expectedPatient));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + GENDER_DESC_BOB + TAG_DESC_FRIEND + REMARK_DESC_BOB, new AddPatientCommand(expectedPatient));

        // multiple genders - last gender accepted
        assertParseSuccess(parser, NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_AMY
                + GENDER_DESC_BOB + TAG_DESC_FRIEND + REMARK_DESC_BOB, new AddPatientCommand(expectedPatient));

        // multiple remarks - last remark accepted
        assertParseSuccess(parser, NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + TAG_DESC_FRIEND + REMARK_DESC_AMY + REMARK_DESC_BOB, new AddPatientCommand(expectedPatient));

        // multiple tags - all accepted
        Patient expectedPatientMultipleTags = new PatientBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + REMARK_DESC_BOB,
                new AddPatientCommand(expectedPatientMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Patient expectedPatient = new PatientBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NRIC_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY + GENDER_DESC_AMY
                        + REMARK_DESC_AMY, new AddPatientCommand(expectedPatient));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPatientCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB + REMARK_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + GENDER_DESC_BOB + REMARK_DESC_BOB,
                expectedMessage);

        // missing gender prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_GENDER_BOB + REMARK_DESC_BOB,
                expectedMessage);

        // missing remark prefix
        // assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB + VALID_REMARK_BOB,
        //        expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_GENDER_BOB + VALID_REMARK_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, NRIC_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB + GENDER_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + REMARK_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NRIC_DESC_BOB + NAME_DESC_BOB + INVALID_PHONE_DESC + GENDER_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + REMARK_DESC_BOB, Phone.MESSAGE_CONSTRAINTS);

        // invalid gender
        assertParseFailure(parser, NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_GENDER_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + REMARK_DESC_BOB, Gender.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND + REMARK_DESC_BOB, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, NRIC_DESC_BOB + INVALID_NAME_DESC + INVALID_PHONE_DESC
                        + GENDER_DESC_BOB + REMARK_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                        + GENDER_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + REMARK_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPatientCommand.MESSAGE_USAGE));
    }
}
