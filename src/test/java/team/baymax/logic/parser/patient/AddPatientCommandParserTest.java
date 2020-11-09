package team.baymax.logic.parser.patient;

import static team.baymax.commons.core.Messages.MESSAGE_DUPLICATE_PARAM;
import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.logic.parser.CliSyntax.PREFIX_GENDER;
import static team.baymax.logic.parser.CliSyntax.PREFIX_NAME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_PHONE;
import static team.baymax.logic.parser.CliSyntax.PREFIX_REMARK;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static team.baymax.testutil.patient.PatientUtil.GENDER_DESC_AMY;
import static team.baymax.testutil.patient.PatientUtil.GENDER_DESC_BOB;
import static team.baymax.testutil.patient.PatientUtil.INVALID_GENDER_DESC;
import static team.baymax.testutil.patient.PatientUtil.INVALID_NAME_DESC;
import static team.baymax.testutil.patient.PatientUtil.INVALID_PHONE_DESC;
import static team.baymax.testutil.patient.PatientUtil.INVALID_TAG_DESC;
import static team.baymax.testutil.patient.PatientUtil.NAME_DESC_AMY;
import static team.baymax.testutil.patient.PatientUtil.NAME_DESC_BOB;
import static team.baymax.testutil.patient.PatientUtil.NRIC_DESC_AMY;
import static team.baymax.testutil.patient.PatientUtil.NRIC_DESC_BOB;
import static team.baymax.testutil.patient.PatientUtil.PHONE_DESC_AMY;
import static team.baymax.testutil.patient.PatientUtil.PHONE_DESC_BOB;
import static team.baymax.testutil.patient.PatientUtil.PREAMBLE_NON_EMPTY;
import static team.baymax.testutil.patient.PatientUtil.PREAMBLE_WHITESPACE;
import static team.baymax.testutil.patient.PatientUtil.REMARK_DESC_AMY;
import static team.baymax.testutil.patient.PatientUtil.REMARK_DESC_BOB;
import static team.baymax.testutil.patient.PatientUtil.TAG_DESC_DIABETIC;
import static team.baymax.testutil.patient.PatientUtil.TAG_DESC_LTP;
import static team.baymax.testutil.patient.PatientUtil.VALID_GENDER_BOB;
import static team.baymax.testutil.patient.PatientUtil.VALID_NAME_BOB;
import static team.baymax.testutil.patient.PatientUtil.VALID_PHONE_BOB;
import static team.baymax.testutil.patient.PatientUtil.VALID_REMARK_BOB;
import static team.baymax.testutil.patient.PatientUtil.VALID_TAG_DIABETIC;
import static team.baymax.testutil.patient.PatientUtil.VALID_TAG_LTP;
import static team.baymax.testutil.patient.TypicalPatients.AMY;
import static team.baymax.testutil.patient.TypicalPatients.BOB;

import org.junit.jupiter.api.Test;

import team.baymax.logic.commands.patient.AddPatientCommand;
import team.baymax.model.patient.Gender;
import team.baymax.model.patient.Name;
import team.baymax.model.patient.Patient;
import team.baymax.model.patient.Phone;
import team.baymax.model.tag.Tag;
import team.baymax.testutil.patient.PatientBuilder;

public class AddPatientCommandParserTest {
    private AddPatientCommandParser parser = new AddPatientCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Patient expectedPatient = new PatientBuilder(BOB).withTags(VALID_TAG_LTP).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + GENDER_DESC_BOB + TAG_DESC_LTP + REMARK_DESC_BOB, new AddPatientCommand(expectedPatient));

        // multiple names - throws command error
        assertParseFailure(parser, NRIC_DESC_BOB + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + GENDER_DESC_BOB + TAG_DESC_LTP + REMARK_DESC_BOB,
                String.format(MESSAGE_DUPLICATE_PARAM, PREFIX_NAME.getType()));

        // multiple phones - throws command error
        assertParseFailure(parser, NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + GENDER_DESC_BOB + TAG_DESC_LTP + REMARK_DESC_BOB,
                String.format(MESSAGE_DUPLICATE_PARAM, PREFIX_PHONE.getType()));

        // multiple genders - throws command error
        assertParseFailure(parser, NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_AMY
                + GENDER_DESC_BOB + TAG_DESC_LTP + REMARK_DESC_BOB,
                String.format(MESSAGE_DUPLICATE_PARAM, PREFIX_GENDER.getType()));

        // multiple remarks - throws command error
        assertParseFailure(parser, NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + TAG_DESC_LTP + REMARK_DESC_AMY + REMARK_DESC_BOB,
                String.format(MESSAGE_DUPLICATE_PARAM, PREFIX_REMARK.getType()));

        // multiple tags - all accepted
        Patient expectedPatientMultipleTags = new PatientBuilder(BOB).withTags(VALID_TAG_LTP, VALID_TAG_DIABETIC)
                .build();
        assertParseSuccess(parser, NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + TAG_DESC_DIABETIC + TAG_DESC_LTP + REMARK_DESC_BOB,
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

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_GENDER_BOB + VALID_REMARK_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, NRIC_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB + GENDER_DESC_BOB
                + TAG_DESC_DIABETIC + TAG_DESC_LTP + REMARK_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NRIC_DESC_BOB + NAME_DESC_BOB + INVALID_PHONE_DESC + GENDER_DESC_BOB
                + TAG_DESC_DIABETIC + TAG_DESC_LTP + REMARK_DESC_BOB, Phone.MESSAGE_CONSTRAINTS);

        // invalid gender
        assertParseFailure(parser, NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_GENDER_DESC
                + TAG_DESC_DIABETIC + TAG_DESC_LTP + REMARK_DESC_BOB, Gender.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + GENDER_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_LTP + REMARK_DESC_BOB, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, NRIC_DESC_BOB + INVALID_NAME_DESC + INVALID_PHONE_DESC
                        + GENDER_DESC_BOB + REMARK_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NRIC_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                        + GENDER_DESC_BOB + TAG_DESC_DIABETIC + TAG_DESC_LTP + REMARK_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPatientCommand.MESSAGE_USAGE));
    }
}
