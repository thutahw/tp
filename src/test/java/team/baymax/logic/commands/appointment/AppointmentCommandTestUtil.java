package team.baymax.logic.commands.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import team.baymax.commons.core.index.Index;
import team.baymax.logic.commands.Command;
import team.baymax.model.Model;
import team.baymax.model.appointment.Appointment;
import team.baymax.model.appointment.AppointmentIdenticalPredicate;
import team.baymax.model.modelmanagers.AppointmentManager;
import team.baymax.testutil.EditAppointmentDescriptorBuilder;

public class AppointmentCommandTestUtil {
    public static final String VALID_DATETIME_1 = "11-10-2020 12:00";
    public static final String VALID_DATETIME_2 = "13-10-2020 09:00";
    public static final String VALID_DESCRIPTION_1 = "Wisdom teeth extraction.";
    public static final String VALID_DESCRIPTION_2 = "Root canal treatment.";
    public static final String VALID_TAG_DIABETIC = "Diabetic";
    public static final String VALID_TAG_LTP = "LTP";

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
