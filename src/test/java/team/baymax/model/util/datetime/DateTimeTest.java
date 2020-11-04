package team.baymax.model.util.datetime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import team.baymax.testutil.Assert;

class DateTimeTest {

    @Test
    void isValidDateTime_nullDateTime_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> DateTime.isValidDateTime(null));
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
        Assert.assertThrows(NullPointerException.class, () -> DateTime.from(null, Time.fromString("12:00")));
        Assert.assertThrows(NullPointerException.class, () -> DateTime.from(Date.fromString("01-01-2020"), null));
        Assert.assertThrows(NullPointerException.class, () -> DateTime.from(null, null));

    }

    @Test
    void from_validDateAndTime() {
        assertEquals(DateTime.fromString("01-01-2020 12:00"),
                DateTime.from(Date.fromString("01-01-2020"), Time.fromString("12:00")));

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
}
