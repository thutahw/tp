package team.baymax.logic.parser;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.GENDER_DESC_AMY;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.GENDER_DESC_BOB;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.INVALID_GENDER_DESC;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.INVALID_NAME_DESC;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.INVALID_PHONE_DESC;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.INVALID_TAG_DESC;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.NAME_DESC_AMY;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.NAME_DESC_BOB;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.NRIC_DESC_AMY;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.NRIC_DESC_BOB;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.PHONE_DESC_AMY;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.PHONE_DESC_BOB;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.PREAMBLE_NON_EMPTY;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.PREAMBLE_WHITESPACE;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.REMARK_DESC_AMY;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.REMARK_DESC_BOB;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.TAG_DESC_FRIEND;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.TAG_DESC_HUSBAND;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_GENDER_BOB;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_NAME_BOB;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_PHONE_BOB;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_REMARK_BOB;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_TAG_FRIEND;
import static team.baymax.logic.commands.patient.PatientCommandTestUtil.VALID_TAG_HUSBAND;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static team.baymax.testutil.TypicalPatients.AMY;
import static team.baymax.testutil.TypicalPatients.BOB;

import org.junit.jupiter.api.Test;

import team.baymax.logic.commands.patient.AddPatientCommand;
import team.baymax.logic.parser.patient.AddPatientCommandParser;
import team.baymax.model.patient.Gender;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.Patient;
import team.baymax.model.patient.Phone;
import team.baymax.model.tag.Tag;
import team.baymax.testutil.PatientBuilder;

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
