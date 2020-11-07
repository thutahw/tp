package team.baymax.logic.commands.calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.baymax.testutil.Assert.assertThrows;

import team.baymax.logic.commands.Command;
import team.baymax.logic.commands.exceptions.CommandException;
import team.baymax.model.Model;
import team.baymax.model.calendar.AppointmentCalendar;
import team.baymax.model.modelmanagers.CalendarManager;

public class CalendarCommandTestUtil {
    public static final int DAY_1 = 1;
    public static final int DAY_28 = 28;
    public static final int DAY_29 = 29;
    public static final int DAY_30 = 30;
    public static final int DAY_31 = 31;

    public static final int MONTH_JAN = 1;
    public static final int MONTH_FEB = 2;
    public static final int MONTH_MAY = 5;

    public static final int COMMON_YEAR = 2021;
    public static final int LEAP_YEAR = 2020;


    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the calendar manager, appointment calendar remain unchanged
     */
    public static void assertCalendarCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        CalendarManager expectedCalendarManager = new CalendarManager(actualModel.getCalendarManager());
        AppointmentCalendar expectedAppointmentCalendar = new AppointmentCalendar(
                actualModel.getAppointmentCalendar().getDay(),
                actualModel.getAppointmentCalendar().getMonth(),
                actualModel.getAppointmentCalendar().getYear());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedCalendarManager, actualModel.getCalendarManager());
        assertEquals(expectedAppointmentCalendar, actualModel.getAppointmentCalendar());
    }
}
