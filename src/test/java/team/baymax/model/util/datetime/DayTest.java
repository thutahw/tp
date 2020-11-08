package team.baymax.model.util.datetime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.calendar.TypicalDays.THIRTY_FIRST;

import org.junit.jupiter.api.Test;

public class DayTest {
    @Test
    void newDay_invalidInt_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Day(32));
        assertThrows(IllegalArgumentException.class, () -> new Day(0));
        assertThrows(IllegalArgumentException.class, () -> new Day(-1));
    }

    @Test
    void isValidDay_invalidInt_returnsFalse() {
        assertFalse(Day.isValidDay(-1));
        assertFalse(Day.isValidDay(32));
        assertFalse(Day.isValidDay(0));
    }

    @Test
    void isValidDay_validInt_returnsTrue() {
        assertTrue(Day.isValidDay(1));
        assertTrue(Day.isValidDay(31));
    }

    @Test
    void getValue_validDay() {
        assertEquals(THIRTY_FIRST.getValue(), 31);
    }

    @Test
    void equals_differentObject_returnsFalse() {
        assertNotEquals(null, THIRTY_FIRST);
        assertNotEquals("31", THIRTY_FIRST);
    }

    @Test
    void equals_sameNumber_returnsTrue() {
        assertEquals(new Day(31), THIRTY_FIRST);
    }

    @Test
    void toString_validInt_returnsString() {
        assertEquals(THIRTY_FIRST.toString(), "31");
    }
}
