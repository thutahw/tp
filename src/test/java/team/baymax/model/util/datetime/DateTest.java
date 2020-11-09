package team.baymax.model.util.datetime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.calendar.TypicalDays.FIRST;
import static team.baymax.testutil.calendar.TypicalMonths.JANUARY;
import static team.baymax.testutil.calendar.TypicalYears.YEAR_2020;
import static team.baymax.testutil.datetime.TypicalDates.DATE1;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import team.baymax.model.calendar.AppointmentCalendar;

class DateTest {

    @Test
    void isValidDate_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));
    }

    @Test
    void isValidDate_invalidDate() {
        assertFalse(Date.isValidDate("ABC123"));
        assertFalse(Date.isValidDate("01/02/2020"));
        assertFalse(Date.isValidDate("40/12/2021"));
        assertFalse(Date.isValidDate("31/13/2021"));
        assertFalse(Date.isValidDate("32-02-2020"));
        assertFalse(Date.isValidDate("12-12-2020 14:00"));
    }

    @Test
    void isValidDate_validDate() {
        assertTrue(Date.isValidDate("01-02-2020"));
        assertTrue(Date.isValidDate("30-01-2021"));
    }

    @Test
    void from_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Date.fromString(null));
    }

    @Test
    void from_validDate() {
        assertEquals(Date.fromString("01-01-2020"),
                new Date(LocalDate.parse("2020-01-01")));
    }

    @Test
    void fromCalendar_nullAppointmentCalendar_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Date.fromCalendar(null));
    }

    @Test
    void fromCalendar_validAppointmentCalendar() {
        AppointmentCalendar test = new AppointmentCalendar(new Date(FIRST, JANUARY, YEAR_2020));
        assertEquals(Date.fromCalendar(test), Date.fromString("01-01-2020"));
    }

    @Test
    void getDate_localDateReturned() {
        assertEquals(DATE1.getDate(), LocalDate.parse("2020-01-01"));
    }

    @Test
    void getYear_yearReturned() {
        assertEquals(DATE1.getYear(), YEAR_2020);
    }

    @Test
    void getMonth_monthReturned() {
        assertEquals(DATE1.getMonth(), JANUARY);
    }

    @Test
    void getDay_dayReturned() {
        assertEquals(DATE1.getDay(), FIRST);
    }

    @Test
    void equals_invalidDate_returnsFalse() {
        assertNotEquals(null, DATE1);
        assertNotEquals(1, DATE1);
        assertNotEquals("01-01-2020", DATE1);
    }

    @Test
    void equals_sameDate_returnsTrue() {
        assertEquals(DATE1, Date.fromString("01-01-2020"));
    }

    @Test
    void toString_validDate_returnsCorrectString() {
        assertEquals(DATE1.toString(), "01 Jan 2020");
    }
}
