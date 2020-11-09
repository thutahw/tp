package team.baymax.logic.commands.calendar;

import static team.baymax.logic.commands.CommandTestUtil.assertCommandSuccess;
import static team.baymax.logic.commands.calendar.CalendarCommandTestUtil.COMMON_YEAR;
import static team.baymax.logic.commands.calendar.CalendarCommandTestUtil.DAY_29;
import static team.baymax.logic.commands.calendar.CalendarCommandTestUtil.LEAP_YEAR;
import static team.baymax.logic.commands.calendar.CalendarCommandTestUtil.MONTH_JAN_INT;
import static team.baymax.logic.commands.calendar.CalendarCommandTestUtil.MONTH_MAY_INT;
import static team.baymax.testutil.calendar.TypicalCalendar.getTypicalCalendarManager;

import org.junit.jupiter.api.Test;

import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.appointment.AppointmentMatchesDatePredicate;
import team.baymax.model.modelmanagers.AppointmentManager;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.userprefs.UserPrefs;
import team.baymax.model.util.datetime.Date;
import team.baymax.model.util.datetime.Day;
import team.baymax.model.util.datetime.Month;
import team.baymax.model.util.datetime.Year;

public class YearCommandTest {

    private Model model = new ModelManager(new PatientManager(), new AppointmentManager(), new UserPrefs(),
            getTypicalCalendarManager());

    @Test
    public void execute_validYear_success() {
        Year validYear = new Year(COMMON_YEAR);

        YearCommand yearCommand = new YearCommand(validYear);

        Model expectedModel = new ModelManager(new PatientManager(), new AppointmentManager(), new UserPrefs(),
                model.getCalendarManager());

        expectedModel.setMonth(new Month(MONTH_JAN_INT));
        expectedModel.setYear(validYear);

        Date date = Date.fromCalendar(model.getAppointmentCalendar());
        expectedModel.updateFilteredAppointmentList(new AppointmentMatchesDatePredicate(date));

        String expectedMessage = String.format(YearCommand.MESSAGE_SUCCESS, date.getMonth(), date.getYear());

        assertCommandSuccess(yearCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_switchYear_success() {
        // initialise to a leap year
        model.setMonth(new Month(MONTH_MAY_INT));
        model.setYear(new Year(LEAP_YEAR));
        model.setDay(new Day(DAY_29));

        // set to a common year -> expect switch to first month of the year
        Year commonYear = new Year(COMMON_YEAR);
        YearCommand yearCommand = new YearCommand(commonYear);

        Model expectedModel = new ModelManager(new PatientManager(), new AppointmentManager(), new UserPrefs(),
                model.getCalendarManager());
        expectedModel.setYear(new Year(COMMON_YEAR));
        expectedModel.setMonth(new Month(MONTH_JAN_INT));

        Date date = Date.fromCalendar(model.getAppointmentCalendar());
        expectedModel.updateFilteredAppointmentList(new AppointmentMatchesDatePredicate(date));

        String expectedMessage = String.format(YearCommand.MESSAGE_SUCCESS, date.getMonth(), date.getYear());

        assertCommandSuccess(yearCommand, model, expectedMessage, expectedModel);
    }
}
