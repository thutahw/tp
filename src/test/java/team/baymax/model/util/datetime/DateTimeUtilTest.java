package team.baymax.model.util.datetime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.calendar.TypicalMonths.FEBRUARY;
import static team.baymax.testutil.calendar.TypicalMonths.JANUARY;
import static team.baymax.testutil.calendar.TypicalYears.YEAR_2020;
import static team.baymax.testutil.calendar.TypicalYears.YEAR_2021;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class DateTimeUtilTest {
    @Test
    void getMonthForInt_validInt() {
        //check it it should be 1 or 2
        assertEquals(DateTimeUtil.getMonthStringFromInt(1), JANUARY.toString());
    }

    @Test
    void getNumOfDays_nullMonthYear_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> DateTimeUtil.getNumOfDays(null, YEAR_2020));
        assertThrows(NullPointerException.class, () -> DateTimeUtil.getNumOfDays(FEBRUARY, null));
    }

    @Test
    void getNumOfDays_validMonthYear() {
        assertEquals(29, DateTimeUtil.getNumOfDays(FEBRUARY, YEAR_2020));
        assertEquals(28, DateTimeUtil.getNumOfDays(FEBRUARY, YEAR_2021));
        assertEquals(31, DateTimeUtil.getNumOfDays(JANUARY, YEAR_2020));
    }

    @Test
    void isLeapYear_nullYear_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> DateTimeUtil.isLeapYear(null));
    }

    @Test
    void isLeapYear_validYear() {
        assertTrue(DateTimeUtil.isLeapYear(YEAR_2020));
        assertFalse(DateTimeUtil.isLeapYear(YEAR_2021));
    }

    @Test
    void getCurrentDateTime() {
        assertEquals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mma")),
                DateTimeUtil.getCurrentDateTime().toString());
    }
}
