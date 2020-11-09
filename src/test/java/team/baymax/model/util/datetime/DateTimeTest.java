package team.baymax.model.util.datetime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.datetime.TypicalDateTimes.DATETIME1;
import static team.baymax.testutil.datetime.TypicalDateTimes.DATETIME1_DUPLICATE;
import static team.baymax.testutil.datetime.TypicalDateTimes.DATETIME2;
import static team.baymax.testutil.datetime.TypicalDateTimes.DATETIME3;
import static team.baymax.testutil.datetime.TypicalDateTimes.DATETIME4;
import static team.baymax.testutil.datetime.TypicalDateTimes.DATETIME5;
import static team.baymax.testutil.datetime.TypicalDates.DATE1;
import static team.baymax.testutil.datetime.TypicalDates.DATE2;
import static team.baymax.testutil.datetime.TypicalTimes.TIME1;
import static team.baymax.testutil.datetime.TypicalTimes.TIME2;
import static team.baymax.testutil.datetime.TypicalTimes.TIME3;

import org.junit.jupiter.api.Test;

class DateTimeTest {

    @Test
    void constructor() {
        assertEquals(new DateTime(), DATETIME1);
    }

    @Test
    void isValidDateTime_nullDateTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> DateTime.isValidDateTime(null));
    }

    @Test
    void isValidDateTime_invalidDateTime() {
        assertFalse(DateTime.isValidDateTime("ABC123"));
        assertFalse(DateTime.isValidDateTime("01/02/2020"));
        assertFalse(DateTime.isValidDateTime("40/12/2021"));
        assertFalse(DateTime.isValidDateTime("31/13/2021"));
        assertFalse(DateTime.isValidDateTime("01-02-2020"));

        assertFalse(DateTime.isValidDateTime("14:00"));
        assertFalse(DateTime.isValidDateTime("12-12-2020 25:03"));
        assertFalse(DateTime.isValidDateTime("12-12-2020 25:03:02"));
        assertFalse(DateTime.isValidDateTime("12-12-2020 02:45 AM"));
    }

    @Test
    void isValidDateTime_validDateTime() {
        assertTrue(DateTime.isValidDateTime("01-02-2020 12:00"));
        assertTrue(DateTime.isValidDateTime("30-01-2021 23:59"));
    }

    @Test
    void from_nullDateAndTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> DateTime.from(null, Time.fromString("12:00")));
        assertThrows(NullPointerException.class, () -> DateTime.from(Date.fromString("01-01-2020"), null));
        assertThrows(NullPointerException.class, () -> DateTime.from(null, null));

    }

    @Test
    void from_validDateAndTime() {
        assertEquals(DateTime.fromString("01-01-2020 12:00"),
                DateTime.from(Date.fromString("01-01-2020"), Time.fromString("12:00")));

    }

    @Test
    void getStorageFormat_returnsCorrectFormat() {
        assertEquals("01-01-2020 20:00", DATETIME2.getStorageFormat());
        assertEquals("01-01-2020 20:01", DATETIME3.getStorageFormat());
    }

    @Test
    void getDay() {
        assertNotEquals(DATETIME3.getDay(), DATETIME4.getDay());
        assertEquals(DATETIME1.getDay(), DATETIME4.getDay());
    }

    @Test
    void getMonth() {
        assertNotEquals(DATETIME1.getMonth(), DATETIME5.getMonth());
        assertEquals(DATETIME1.getMonth(), DATETIME1.getMonth());
    }

    @Test
    void getYear() {
        assertNotEquals(DATETIME1.getYear(), DATETIME5.getYear());
        assertEquals(DATETIME1.getYear(), DATETIME1.getYear());
    }

    @Test
    void getDate() {
        assertNotEquals(DATE1, DATETIME1.getDate());
        assertEquals(DATE2, DATETIME4.getDate());
    }

    @Test
    void getTime() {
        assertNotEquals(TIME1, DATETIME2.getTime());
        assertEquals(TIME2, DATETIME1.getTime());
        assertEquals(TIME3, DATETIME5.getTime());
    }

    @Test
    void plusMinutes() {
        assertEquals(DateTime.fromString("01-01-2020 12:00").plusMinutes(new Duration(45)),
                DateTime.fromString("01-01-2020 12:45"));
        assertEquals(DateTime.fromString("01-01-2020 23:00").plusMinutes(new Duration(120)),
                DateTime.fromString("02-01-2020 01:00"));
        assertNotEquals(DateTime.fromString("01-01-2020 12:00").plusMinutes(new Duration(12)),
                DateTime.fromString("01-01-2020 12:00"));
    }

    @Test
    void isAfter() {
        DateTime before = DateTime.fromString("01-01-2020 12:00");
        DateTime middle = DateTime.fromString("01-01-2020 18:00");
        DateTime after = DateTime.fromString("01-02-2020 12:00");

        assertTrue(middle.isAfter(before));
        assertTrue(after.isAfter(middle));
        assertFalse(before.isAfter(before));
        assertFalse(before.isAfter(after));

    }

    @Test
    void isBefore() {
        DateTime before = DateTime.fromString("01-01-2020 12:00");
        DateTime middle = DateTime.fromString("01-01-2020 18:00");
        DateTime after = DateTime.fromString("01-02-2020 12:00");

        assertFalse(before.isBefore(before));
        assertTrue(before.isBefore(middle));
        assertTrue(middle.isBefore(after));
        assertFalse(after.isBefore(before));
    }

    @Test
    void isEqual() {
        DateTime before = DateTime.fromString("01-01-2020 12:00");
        DateTime after = DateTime.fromString("01-02-2020 12:00");

        assertTrue(before.isEqual(before));
        assertTrue(before.isEqual(DateTime.fromString("01-01-2020 12:00")));
        assertFalse(before.isEqual(after));
    }

    @Test
    void compareTo_nullDateTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> DATETIME1.compareTo(null));
    }

    @Test
    void compareTo_validDateTime() {
        assertTrue(DATETIME1.compareTo(DATETIME2) > 0);
        assertTrue(DATETIME3.compareTo(DATETIME4) < 0);
        assertTrue(DATETIME1.compareTo(DATETIME1_DUPLICATE) == 0);
    }

    @Test
    void hashCode_sameDateTime_equal() {
        assertEquals(DATETIME1.hashCode(), DATETIME1_DUPLICATE.hashCode());
    }

    @Test
    void hashCode_differentDateTime_notEqual() {
        assertNotEquals(DATETIME2.hashCode(), DATETIME3.hashCode());
    }

    @Test
    void toString_validDateTime() {
        assertEquals(DATETIME1.toString(), "12 Dec 2020, 11:59PM");
        assertNotEquals(DATETIME4.toString(), "12-01-2020 10:00");
    }
}
