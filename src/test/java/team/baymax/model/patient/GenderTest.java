package team.baymax.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GenderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Gender(null));
    }

    @Test
    public void constructor_invalidGender_throwsIllegalArgumentException() {
        String invalidGender = "";
        assertThrows(IllegalArgumentException.class, () -> new Gender(invalidGender));
    }

    @Test
    public void isValidGender() {

        // blank gender -> invalid
        assertFalse(Gender.isValidGender("")); // empty string
        assertFalse(Gender.isValidGender(" ")); // spaces only

        // contains number -> invalid
        assertFalse(Gender.isValidGender("0"));
        assertFalse(Gender.isValidGender("M0"));

        // contains symbols -> invalid
        assertFalse(Gender.isValidGender("@*"));
        assertFalse(Gender.isValidGender("M@"));

        // lower case gender -> valid
        assertTrue(Gender.isValidGender("f"));
        assertTrue(Gender.isValidGender("m"));

        // upper case gender
        assertTrue(Gender.isValidGender("F"));
        assertTrue(Gender.isValidGender("M"));
    }
}
