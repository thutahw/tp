package team.baymax.model.util.datetime;
import java.text.DateFormatSymbols;

public class DateTimeUtil {

    private static int[] numOfDaysInAMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private static int numOfDaysInFebLeapYear = 29;


    public static String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }

    public static int getNumOfDays(Month month, Year year) {
        if (month.getValue() == 2) {
            if (isLeapYear(year)) {
                return numOfDaysInFebLeapYear;
            }
        }

        return numOfDaysInAMonth[month.getValue() - 1];
    }

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
