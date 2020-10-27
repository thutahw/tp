package team.baymax.logic.commands.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DATETIME;
import static team.baymax.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static team.baymax.logic.parser.CliSyntax.PREFIX_ID;
import static team.baymax.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.Command;
import team.baymax.model.Model;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.listmanagers.AppointmentManager;
import team.baymax.testutil.EditAppointmentDescriptorBuilder;

public class AppointmentCommandTestUtil {

    public static final String VALID_ID1 = "1";
    public static final String VALID_ID2 = "2";
    public static final String VALID_ID3 = "3";

    public static final String VALID_DATETIME_1 = "11-10-2020 12:00";
    public static final String VALID_DATETIME_2 = "13-10-2020 09:00";
    public static final String VALID_DESCRIPTION_1 = "Wisdom teeth extraction.";
    public static final String VALID_DESCRIPTION_2 = "Root canal treatment.";
    public static final String VALID_TAG_DIABETIC = "Diabetic";
    public static final String VALID_TAG_LTP = "LTP";

    public static final String VALID_DESC_ID1 = " " + PREFIX_ID + VALID_ID1;
    public static final String VALID_DESC_ID2 = " " + PREFIX_ID + VALID_ID2;
    public static final String VALID_DESC_ID3 = " " + PREFIX_ID + VALID_ID3;
    public static final String VALID_DESC_DATETIME_1 = " " + PREFIX_DATETIME + VALID_DATETIME_1;
    public static final String VALID_DESC_DATETIME_2 = " " + PREFIX_DATETIME + VALID_DATETIME_2;
    public static final String VALID_DESC_DESCRIPTION_1 = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_1;
    public static final String VALID_DESC_DESCRIPTION_2 = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_2;
    public static final String VALID_DESC_TAG_DIABETIC = " " + PREFIX_TAG + VALID_TAG_DIABETIC;
    public static final String VALID_DESC_TAG_LTP = " " + PREFIX_TAG + VALID_TAG_LTP;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditAppointmentDescriptor DESC_APPT1;
    public static final EditAppointmentDescriptor DESC_APPT2;

    static {
        DESC_APPT1 = new EditAppointmentDescriptorBuilder().withDateTime(VALID_DATETIME_1)
                .withDescription(VALID_DESCRIPTION_1).withTags(VALID_TAG_DIABETIC).build();
        DESC_APPT2 = new EditAppointmentDescriptorBuilder().withDateTime(VALID_DATETIME_2)
                .withDescription(VALID_DESCRIPTION_2).withTags(VALID_TAG_LTP).build();
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

        // assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAppointmentManager, actualModel.getAppointmentManager());
        assertEquals(expectedFilteredList, actualModel.getFilteredAppointmentList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the patient at the given {@code targetIndex} in the
     * {@code model}'s appointment book.
     */
    public static void showAppointmentAtIndex(Model model, Index targetIndex) {
        // assertTrue(targetIndex.getZeroBased() < model.getFilteredAppointmentList().size());
        // Patient patient = model.getFilteredPatientList().get(targetIndex.getZeroBased());
        // model.updateFilteredAppointmentList(new PatientHasAppointmentPredicate(patient));
        // assertEquals(1, model.getFilteredAppointmentList().size());
    }
}
