package team.baymax.logic.commands.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DATETIME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DURATION;
import static team.baymax.logic.parser.CliSyntax.PREFIX_INDEX;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TAG;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TIME;
import static team.baymax.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentIdenticalPredicate;
import team.baymax.model.modelmanagers.AppointmentManager;
import team.baymax.testutil.appointment.EditAppointmentDescriptorBuilder;

public class AppointmentCommandTestUtil {

    public static final String VALID_ID1 = "1";
    public static final String VALID_ID2 = "2";
    public static final String VALID_ID3 = "3";

    public static final String VALID_DATETIME_1 = "11-10-2020 19:00";
    public static final String VALID_DATETIME_2 = "13-10-2020 09:00";

    public static final String VALID_TIME_2PM = "14:00";

    public static final String VALID_DURATION_30 = "30";
    public static final String VALID_DURATION_40 = "40";
    public static final String VALID_DURATION_50 = "50";
    public static final String VALID_DURATION_60 = "60";
    public static final String VALID_DURATION_90 = "90";

    public static final String VALID_DESCRIPTION_1 = "Wisdom teeth extraction.";
    public static final String VALID_DESCRIPTION_2 = "Root canal treatment.";

    public static final String VALID_TAG_1HR = "1HR";
    public static final String VALID_TAG_DRGOH = "DrGoh";

    public static final String VALID_DESC_ID1 = " " + PREFIX_INDEX + VALID_ID1;
    public static final String VALID_DESC_ID2 = " " + PREFIX_INDEX + VALID_ID2;
    public static final String VALID_DESC_ID3 = " " + PREFIX_INDEX + VALID_ID3;

    public static final String VALID_DESC_DURATION_30 = " " + PREFIX_DURATION + VALID_DURATION_30;
    public static final String VALID_DESC_DURATION_40 = " " + PREFIX_DURATION + VALID_DURATION_40;
    public static final String VALID_DESC_DURATION_50 = " " + PREFIX_DURATION + VALID_DURATION_50;
    public static final String VALID_DESC_DURATION_60 = " " + PREFIX_DURATION + VALID_DURATION_60;
    public static final String VALID_DESC_DURATION_90 = " " + PREFIX_DURATION + VALID_DURATION_90;

    public static final String VALID_DESC_DATETIME_1 = " " + PREFIX_DATETIME + VALID_DATETIME_1;
    public static final String VALID_DESC_DATETIME_2 = " " + PREFIX_DATETIME + VALID_DATETIME_2;

    public static final String VALID_DESC_TIME_2PM = " " + PREFIX_TIME + VALID_TIME_2PM;

    public static final String VALID_DESC_DESCRIPTION_1 = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_1;
    public static final String VALID_DESC_DESCRIPTION_2 = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_2;
    public static final String VALID_DESC_TAG_1HR = " " + PREFIX_TAG + VALID_TAG_1HR;
    public static final String VALID_DESC_TAG_DRGOH = " " + PREFIX_TAG + VALID_TAG_DRGOH;

    public static final String INVALID_DESC_ZERO_ID = " " + PREFIX_INDEX + "0";
    public static final String INVALID_DESC_NEGATIVE_ID = " " + PREFIX_INDEX + "-1";
    public static final String INVALID_DESC_DATETIME = " " + PREFIX_DATETIME + "12-10-20200 14:00";
    public static final String INVALID_DESC_TIME = " " + PREFIX_TIME + "14:001";
    public static final String INVALID_DESC_DESCRIPTION = " " + PREFIX_DESCRIPTION + "";
    public static final String INVALID_DESC_TAG = " " + PREFIX_TAG + "1HR*";


    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditAppointmentDescriptor DESC_APPT1;
    public static final EditAppointmentDescriptor DESC_APPT2;

    static {
        DESC_APPT1 = new EditAppointmentDescriptorBuilder().withDateTime(VALID_DATETIME_1)
                .withDescription(VALID_DESCRIPTION_1).withTags(VALID_TAG_1HR).build();
        DESC_APPT2 = new EditAppointmentDescriptorBuilder().withDateTime(VALID_DATETIME_2)
                .withDescription(VALID_DESCRIPTION_2).withTags(VALID_TAG_DRGOH).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the appointment manager, filtered appointment list and selected appointment in {@code actualModel}
     * remain unchanged
     */
    public static void assertAppointmentCommandFailure(Command command, Model actualModel, String expectedMessage) {
        AppointmentManager expectedAppointmentManager = new AppointmentManager(actualModel.getAppointmentManager());
        List<Appointment> expectedFilteredList = new ArrayList<>(actualModel.getFilteredAppointmentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAppointmentManager, actualModel.getAppointmentManager());
        assertEquals(expectedFilteredList, actualModel.getFilteredAppointmentList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the appointment at the given {@code targetIndex} in the
     * {@code model}'s appointment book.
     */
    public static void showAppointmentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredAppointmentList().size());

        Appointment appointment = model.getFilteredAppointmentList().get(targetIndex.getZeroBased());
        model.updateFilteredAppointmentList(new AppointmentIdenticalPredicate(appointment));
        assertEquals(1, model.getFilteredAppointmentList().size());
    }
}
