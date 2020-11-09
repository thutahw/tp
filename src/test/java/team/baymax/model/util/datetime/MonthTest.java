package team.baymax.model.util.datetime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.calendar.TypicalMonths.FEBRUARY;
import static team.baymax.testutil.calendar.TypicalMonths.JANUARY;

import org.junit.jupiter.api.Test;

public class MonthTest {
    @Test
    void newMonth_invalidInt_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Month(13));
        assertThrows(IllegalArgumentException.class, () -> new Month(0));
        assertThrows(IllegalArgumentException.class, () -> new Month(-1));
    }

    @Test
    void newMonth_invalidString_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Month(""));
        assertThrows(IllegalArgumentException.class, () -> new Month("invalid"));
        assertThrows(IllegalArgumentException.class, () -> new Month("jan feb"));
    }

    @Test
    void isValidMonth_invalidInt_returnsFalse() {
        assertFalse(Month.isValidMonth(-1));
        assertFalse(Month.isValidMonth(13));
        assertFalse(Month.isValidMonth(0));
    }

    @Test
    void isValidMonth_invalidString_returnsFalse() {
        assertFalse(Month.isValidMonth(""));
        assertFalse(Month.isValidMonth("invalid"));
        assertFalse(Month.isValidMonth("jan feb"));
    }

    @Test
    void isValidMonth_validInt_returnsTrue() {
        assertTrue(Month.isValidMonth(1));
        assertTrue(Month.isValidMonth(12));
    }

    @Test
    void isValidMonth_validString_returnsTrue() {
        assertTrue(Month.isValidMonth("January"));
        assertTrue(Month.isValidMonth("january"));
        assertTrue(Month.isValidMonth("Oct"));
        assertTrue(Month.isValidMonth("oct"));
    }

    @Test
    void getValue_validMonth() {
        assertEquals(FEBRUARY.getValue(), 2);
    }

    @Test
    void equals_differentObject_returnsFalse() {
        assertNotEquals(null, FEBRUARY);
        assertNotEquals("31", FEBRUARY);
    }

    @Test
    void equals_sameNumber_returnsTrue() {
        assertEquals(new Month(1), JANUARY);
        assertEquals(new Month(2), FEBRUARY);
    }

    @Test
    void equals_sameName_returnsTrue() {
        assertEquals(new Month("January"), JANUARY);
        assertEquals(new Month("February"), FEBRUARY);
        assertEquals(new Month("Jan"), JANUARY);
        assertEquals(new Month("Feb"), FEBRUARY);
    }
}
