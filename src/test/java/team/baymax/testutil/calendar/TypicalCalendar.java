package team.baymax.testutil.calendar;

import team.baymax.model.modelmanagers.CalendarManager;
import team.baymax.model.util.datetime.Day;
import team.baymax.model.util.datetime.Month;
import team.baymax.model.util.datetime.Year;

public class TypicalCalendar {

    public static CalendarManager getTypicalCalendarManager() {
        CalendarManager calendarManager = new CalendarManager();

        // Set to 01-01-2021
        calendarManager.setDay(new Day(1));
        calendarManager.setMonth(new Month(2)); // 28 days in february
        calendarManager.setYear(new Year(2021)); // non-leap-year
        return calendarManager;
    }
}
