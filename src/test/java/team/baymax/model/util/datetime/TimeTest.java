package team.baymax.model.util.datetime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.datetime.TypicalTimes.TIME1;
import static team.baymax.testutil.datetime.TypicalTimes.TIME2;
import static team.baymax.testutil.datetime.TypicalTimes.TIME3;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TimeTest {
    @Test
    void constructor() {
        assertEquals(new Time(LocalTime.NOON), TIME3);
    }

    @Test
    void constructor_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Time(null));
    }

    @Test
    void isValidTime_nullString_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Time.isValidTime(null));
    }

    @Test
    void isValidTime_invalidString_returnsFalse() {
        assertFalse(Time.isValidTime("Not a time."));
        assertFalse(Time.isValidTime("24:01"));
        assertFalse(Time.isValidTime("23:59."));
        assertFalse(Time.isValidTime("00:60"));
    }

    @Test
    void isValidTime_validString_returnsTrue() {
        assertTrue(Time.isValidTime("00:00"));
        assertTrue(Time.isValidTime("23:59"));
        assertTrue(Time.isValidTime("12:00"));
    }

    @Test
    void getTime_sameLocalTime_returnsFalse() {
        assertEquals(LocalTime.MIDNIGHT, TIME1.getTime());
        assertEquals(LocalTime.of(23, 59), TIME2.getTime());
        assertEquals(LocalTime.NOON, TIME3.getTime());
    }

    @Test
    void toString_returnsCorrectOutput() {
        assertEquals("12:00 AM", TIME1.toString());
        assertEquals("11:59 PM", TIME2.toString());
        assertEquals("12:00 PM", TIME3.toString());
    }

    @Test
    void equals_differentInput_returnsFalse() {
        assertNotEquals(null, TIME1);
        assertNotEquals("00:00", TIME1);
        assertNotEquals("12:00 AM", TIME1);
    }

    @Test
    void equals_sameInput_returnsTrue() {
        assertEquals(Time.fromString("00:00"), TIME1);
        assertEquals(Time.fromString("23:59"), TIME2);
        assertEquals(Time.fromString("12:00"), TIME3);
    }
}
