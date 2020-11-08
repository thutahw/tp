package team.baymax.logic.parser.appointment;

import static team.baymax.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.INVALID_DESC_DESCRIPTION;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.PREAMBLE_WHITESPACE;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DATETIME_1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESCRIPTION_1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESC_DATETIME_1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESC_DESCRIPTION_1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESC_DURATION_30;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESC_TAG_1HR;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESC_TAG_DRGOH;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DESC_TIME_2PM;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_DURATION_30;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_ID1;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_TAG_1HR;
import static team.baymax.logic.commands.appointment.AppointmentCommandTestUtil.VALID_TAG_DRGOH;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseFailure;
import static team.baymax.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static team.baymax.testutil.appointment.TypicalAppointments.ALICE_APT;
import static team.baymax.testutil.patient.PatientUtil.VALID_NAME_BOB;
import static team.baymax.testutil.patient.TypicalPatientIndexes.INDEX_FIRST_PATIENT;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import team.baymax.logic.commands.appointment.AddAppointmentCommand;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.Description;
import team.baymax.model.util.datetime.Duration;
import team.baymax.testutil.appointment.AppointmentBuilder;

public class AddAppointmentCommandParserTest {
    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        Appointment expectedAppointment = new AppointmentBuilder(ALICE_APT)
                .withDateTime(VALID_DATETIME_1)
                .withDuration(new Duration(Integer.parseInt(VALID_DURATION_30)))
                .withDescription(VALID_DESCRIPTION_1)
                .withTags(VALID_TAG_DRGOH)
                .build();

        // white space only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_ID1 + VALID_DESC_DATETIME_1
                + VALID_DESC_DURATION_30 + VALID_DESC_DESCRIPTION_1 + VALID_DESC_TAG_DRGOH,
                new AddAppointmentCommand(Optional.of(INDEX_FIRST_PATIENT), Optional.empty(),
                        Optional.of(expectedAppointment.getDateTime()),
                        Optional.empty(), expectedAppointment.getDuration(),
                        expectedAppointment.getDescription(), expectedAppointment.getTags()));

        // all valid inputs
        assertParseSuccess(parser, VALID_ID1 + VALID_DESC_DATETIME_1
                        + VALID_DESC_DURATION_30 + VALID_DESC_DESCRIPTION_1 + VALID_DESC_TAG_DRGOH,
                new AddAppointmentCommand(Optional.of(INDEX_FIRST_PATIENT), Optional.empty(),
                        Optional.of(expectedAppointment.getDateTime()),
                        Optional.empty(), expectedAppointment.getDuration(),
                        expectedAppointment.getDescription(), expectedAppointment.getTags()));

        // multiple tags - all accepted
        Appointment expectedAppointmentMultipleTags = new AppointmentBuilder(ALICE_APT)
                .withDateTime(VALID_DATETIME_1)
                .withDuration(new Duration(Integer.parseInt(VALID_DURATION_30)))
                .withDescription(VALID_DESCRIPTION_1)
                .withTags(VALID_TAG_DRGOH, VALID_TAG_1HR)
                .build();

        assertParseSuccess(parser, VALID_ID1 + VALID_DESC_DATETIME_1 + VALID_DESC_DURATION_30
                        + VALID_DESC_DESCRIPTION_1 + VALID_DESC_TAG_DRGOH + VALID_DESC_TAG_1HR,
                new AddAppointmentCommand(Optional.of(INDEX_FIRST_PATIENT), Optional.empty(),
                        Optional.of(expectedAppointmentMultipleTags.getDateTime()),
                        Optional.empty(), expectedAppointmentMultipleTags.getDuration(),
                        expectedAppointmentMultipleTags.getDescription(), expectedAppointmentMultipleTags.getTags()));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {

        Appointment expectedAppointmentWithoutTag = new AppointmentBuilder(ALICE_APT)
                .withDateTime(VALID_DATETIME_1)
                .withDuration(new Duration(Integer.parseInt(VALID_DURATION_30)))
                .withDescription(VALID_DESCRIPTION_1)
                .withTags()
                .build();

        // zero tags
        // assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_ID1 + VALID_DESC_DATETIME_1
        //                + VALID_DESC_TIME_2PM + VALID_DESC_DURATION_30 + VALID_DESC_DESCRIPTION_1,
        //        new AddAppointmentCommand(Optional.of(INDEX_FIRST_PATIENT), Optional.empty(),
        //                Optional.of(expectedAppointmentWithoutTag.getDateTime()),
        //                Optional.of(expectedAppointmentWithoutTag.getTime()),
        //                expectedAppointmentWithoutTag.getDuration(),
        //                expectedAppointmentWithoutTag.getDescription(), expectedAppointmentWithoutTag.getTags()));

        Appointment expectedAppointmentWithoutTime = new AppointmentBuilder(ALICE_APT)
                .withDateTime(VALID_DATETIME_1)
                .withDuration(new Duration(Integer.parseInt(VALID_DURATION_30)))
                .withDescription(VALID_DESCRIPTION_1)
                .withTags(VALID_TAG_1HR)
                .build();

        // time not provided
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_ID1 + VALID_DESC_DATETIME_1
                        + VALID_DESC_DURATION_30 + VALID_DESC_DESCRIPTION_1 + VALID_DESC_TAG_1HR,
                new AddAppointmentCommand(Optional.of(INDEX_FIRST_PATIENT), Optional.empty(),
                        Optional.of(expectedAppointmentWithoutTime.getDateTime()),
                        Optional.empty(), expectedAppointmentWithoutTime.getDuration(),
                        expectedAppointmentWithoutTime.getDescription(), expectedAppointmentWithoutTime.getTags()));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE);

        // missing ID prefix
        // assertParseFailure(parser, VALID_DESC_DATETIME_1 + VALID_DESC_DURATION_30 + VALID_DESC_DESCRIPTION_1
        //        + VALID_DESC_TAG_DRGOH, expectedMessage);

        // missing datetime prefix and time prefix
        // assertParseFailure(parser, VALID_ID1 + VALID_DESC_DURATION_30 + VALID_DESC_DESCRIPTION_1
        //        + VALID_DESC_TAG_DRGOH, expectedMessage);

        // missing duration prefix
        assertParseFailure(parser, VALID_ID1 + VALID_DESC_DATETIME_1 + VALID_DESC_DESCRIPTION_1
                + VALID_DESC_TAG_DRGOH, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, VALID_ID1 + VALID_DESC_DATETIME_1 + VALID_DESC_DURATION_30
                + VALID_DESC_TAG_DRGOH, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid description
        assertParseFailure(parser, VALID_ID1 + VALID_DESC_DATETIME_1
                + VALID_DESC_TIME_2PM + VALID_DESC_DURATION_30 + INVALID_DESC_DESCRIPTION,
                Description.MESSAGE_CONSTRAINTS);

        // invalid datetime
        //assertParseFailure(parser, VALID_ID1 + INVALID_DESC_DATETIME
        //        + VALID_DESC_TIME_2PM + VALID_DESC_DURATION_30 + VALID_DESC_DESCRIPTION_1,
        //        DateTime.MESSAGE_CONSTRAINTS);

        // invalid time
        // assertParseFailure(parser, VALID_ID1 + VALID_DESC_DATETIME_1 + INVALID_DESC_TIME
        // + VALID_DESC_DURATION_30 + VALID_DESC_DESCRIPTION_1, Time.MESSAGE_CONSTRAINTS);

        //assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_ID1 + VALID_DESC_DATETIME_1
        //        + VALID_DESC_TIME_2PM + VALID_DESC_DURATION_30 + VALID_DESC_DESCRIPTION_1,
        //        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE));
    }
}
