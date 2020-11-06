package team.baymax.logic.commands.calendar;

import static team.baymax.logic.commands.CommandTestUtil.assertCommandSuccess;
import static team.baymax.logic.commands.calendar.CalendarCommandTestUtil.COMMON_YEAR;
import static team.baymax.logic.commands.calendar.CalendarCommandTestUtil.DAY_1;
import static team.baymax.logic.commands.calendar.CalendarCommandTestUtil.DAY_31;
import static team.baymax.logic.commands.calendar.CalendarCommandTestUtil.MONTH_FEB;
import static team.baymax.logic.commands.calendar.CalendarCommandTestUtil.MONTH_JAN;
import static team.baymax.logic.commands.calendar.CalendarCommandTestUtil.MONTH_MAY;
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

public class MonthCommandTest {

    private Model model = new ModelManager(new PatientManager(), new AppointmentManager(), new UserPrefs(),
            getTypicalCalendarManager());

    @Test
    public void execute_validMonth_success() {
        Month validMonth = new Month(MONTH_MAY);

        MonthCommand monthCommand = new MonthCommand(validMonth);

        Model expectedModel = new ModelManager(new PatientManager(), new AppointmentManager(), new UserPrefs(),
                model.getCalendarManager());

        expectedModel.setDay(new Day(DAY_1));
        expectedModel.setMonth(validMonth);

        Date date = Date.fromCalendar(model.getAppointmentCalendar());
        expectedModel.updateFilteredAppointmentList(new AppointmentMatchesDatePredicate(date));

        String expectedMessage = String.format(MonthCommand.MESSAGE_SUCCESS, date.getMonth(), date.getYear());

        assertCommandSuccess(monthCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_switchMonth_success() {

        model.setMonth(new Month(MONTH_JAN));
        model.setYear(new Year(COMMON_YEAR));
        model.setDay(new Day(DAY_31));

        Month feb = new Month(MONTH_FEB);
        MonthCommand monthCommand = new MonthCommand(feb);

        Model expectedModel = new ModelManager(new PatientManager(), new AppointmentManager(), new UserPrefs(),
                model.getCalendarManager());
        expectedModel.setDay(new Day(DAY_1));
        expectedModel.setMonth(new Month(MONTH_FEB));

        Date date = Date.fromCalendar(model.getAppointmentCalendar());
        expectedModel.updateFilteredAppointmentList(new AppointmentMatchesDatePredicate(date));

        String expectedMessage = String.format(MonthCommand.MESSAGE_SUCCESS, date.getMonth(), date.getYear());

        assertCommandSuccess(monthCommand, model, expectedMessage, expectedModel);
    }
}
