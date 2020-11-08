package team.baymax.model.util.datetime;

import static java.util.Objects.requireNonNull;
import static team.baymax.commons.util.CollectionUtil.requireAllNonNull;

import java.text.DateFormatSymbols;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Calendar;

public class DateTimeUtil {
    private static int[] numOfDaysInAMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private static int numOfDaysInFebLeapYear = 29;

    public static Day getCurrentDay() {
        return new Day(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    public static Month getCurrentMonth() {
        return new Month(Calendar.getInstance().get(Calendar.MONTH) + 1);
    }

    public static Year getCurrentYear() {
        return new Year(Calendar.getInstance().get(Calendar.YEAR));
    }

    public static Date getCurrentDate() {
        return new Date(getCurrentDay(), getCurrentMonth(), getCurrentYear());
    }


    public static DateTime getCurrentDateTime() {
        return new DateTime(LocalDateTime.now());
    }

    public static Date getClosestValidDate(Day day, Month month, Year year) {
        requireAllNonNull(day, month, year);

        try {
            return new Date(day, month, year);
        } catch (DateTimeException e) {
            int maxNumOfDays = DateTimeUtil.getNumOfDays(month, year);
            return new Date(new Day(maxNumOfDays), month, year);
        }
    }

    public static String getMonthStringFromInt(int num) {
        requireNonNull(num);

        assert num >= 1 && num <= 12 : "invalid integer for month";

        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        return months[num - 1];
    }

    public static int getMonthIntFromString(String monthString) {
        requireNonNull(monthString);
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        String[] shortMonths = dfs.getShortMonths();

        for (int i = 0; i < months.length; i++) {
            if (monthString.equalsIgnoreCase(months[i])) {
                return i + 1;
            }
        }

        for (int i = 0; i < shortMonths.length; i++) {
            if (monthString.equalsIgnoreCase(shortMonths[i])) {
                return i + 1;
            }
        }

        return 0;
    }

    public static int getNumOfDays(Month month, Year year) {
        if (month.getValue() == 2) {
            if (isLeapYear(year)) {
                return numOfDaysInFebLeapYear;
            }
        }

        return numOfDaysInAMonth[month.getValue() - 1];
    }

    /**
     * Returns true if the supplied {@code year} is a leap year.
     *
     */
    public static boolean isLeapYear(Year year) {
        int value = year.getValue();
        if (value % 4 == 0) {
            if (value % 100 == 0) {
                return (value % 400 == 0);
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
