package team.baymax.logic.commands.calendar;

import static team.baymax.logic.commands.CommandTestUtil.assertCommandSuccess;
import static team.baymax.logic.commands.calendar.CalendarCommandTestUtil.VALID_DAY;
import static team.baymax.testutil.calendar.TypicalCalendar.getTypicalCalendarManager;
import static team.baymax.testutil.patient.TypicalPatients.getTypicalPatientManager;

import org.junit.jupiter.api.Test;
import team.baymax.model.Model;
import team.baymax.model.ModelManager;
import team.baymax.model.appointment.AppointmentMatchesDatePredicate;
import team.baymax.model.modelmanagers.AppointmentManager;
import team.baymax.model.modelmanagers.PatientManager;
import team.baymax.model.userprefs.UserPrefs;
import team.baymax.model.util.datetime.Date;
import team.baymax.model.util.datetime.Day;

public class DayCommandTest {

    private Model model = new ModelManager(new PatientManager(), new AppointmentManager(), new UserPrefs(),
            getTypicalCalendarManager());

    @Test
    public void execute_validDay_success() {
        Day validDay = new Day(VALID_DAY);

        DayCommand dayCommand = new DayCommand(validDay);

        Model expectedModel = new ModelManager(new PatientManager(), new AppointmentManager(), new UserPrefs(),
                model.getCalendarManager());

        expectedModel.setDay(validDay);

        Date date = Date.fromCalendar(model.getAppointmentCalendar());
        expectedModel.updateFilteredAppointmentList(new AppointmentMatchesDatePredicate(date));

        String expectedMessage = String.format(DayCommand.MESSAGE_SUCCESS, date);

        assertCommandSuccess(dayCommand, model, expectedMessage, expectedModel);
    }

}
