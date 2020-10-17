package team.baymax.commons.core.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.TypicalDateTimes.DATETIME1;
import static team.baymax.testutil.TypicalDateTimes.DATETIME2;
import static team.baymax.testutil.TypicalDateTimes.DATETIME3;
import static team.baymax.testutil.TypicalDateTimes.DATETIME4;
import static team.baymax.testutil.TypicalDateTimes.DATETIME5;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeTest {

    @Test
    public void fromString_acceptableStringFormat_dateTimeReturned() {
        String acceptableStringDateTime = "12-12-2020 23:59";
        assertEquals(new DateTime(), DateTime.fromString(acceptableStringDateTime));
    }

    @Test
    public void fromString_nonAcceptableStringFormat_illegalArgumentExceptionThrown() {
        String nonAcceptableStringDateTime = "2020-12-12 23:59";
        assertThrows(IllegalArgumentException.class, () -> DateTime.fromString(nonAcceptableStringDateTime));
    }

    @Test
    public void isValidDateTimeString() {
        assertTrue(DateTime.isValidDateTime("13-06-1000 00:34"));
        assertTrue(DateTime.isValidDateTime("22-02-2024 23:33"));

        assertFalse(DateTime.isValidDateTime("22-13-1999 23:33"));
        assertFalse(DateTime.isValidDateTime("22-12-1999 25:00"));
        assertFalse(DateTime.isValidDateTime("32-12-1999 23:33"));
        assertFalse(DateTime.isValidDateTime("22-12-1999 23:63"));
        assertFalse(DateTime.isValidDateTime("This isn't even a date!"));
    }

    @Test
    public void getStorageFormat_dateTimeAsString_stringReturned() {
        assertEquals("12-12-2020 23:59", new DateTime().getStorageFormat());
    }

    @Test
    public void compareToOtherDateTime() {
        assertTrue(0 == DATETIME1.compareTo(DATETIME5));
        assertTrue(0 < DATETIME1.compareTo(DATETIME2));
        assertTrue(0 >DATETIME2.compareTo(DATETIME3));
    }

    @Test
    public void equalsToOtherDateTime() {
        assertTrue(DATETIME1.equals(DATETIME5));

        assertFalse(DATETIME1.equals(DATETIME3));
        assertFalse(DATETIME2.equals(DATETIME4));
        assertFalse(DATETIME3.equals(DATETIME5));
    }

    @Test
    public void hashCode_DefaultObject_valueAsExpected() {
        assertEquals(LocalDateTime.parse("12-12-2020 23:59",
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")).hashCode(), new DateTime().hashCode());
    }

    @Test
    public void toString_typicalDateTimes_valueAsExpected() {
        assertEquals("12 Dec 2020, 11:59PM", DATETIME1.toString());
        assertEquals("01 Jan 2020, 08:00PM", DATETIME2.toString());
        assertEquals("01 Jan 2020, 08:01PM", DATETIME3.toString());
        assertEquals("12 Jan 2020, 10:00AM", DATETIME4.toString());
        assertEquals("12 Dec 2020, 11:59PM", DATETIME5.toString());
    }
}
