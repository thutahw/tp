package team.baymax.model.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static team.baymax.testutil.Assert.assertThrows;
import static team.baymax.testutil.patient.TypicalNrics.NRIC_1;
import static team.baymax.testutil.patient.TypicalNrics.NRIC_2;

import org.junit.jupiter.api.Test;

public class NricTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Nric(null));
    }

    @Test
    public void constructor_invalidNric_throwsIllegalArgumentException() {
        String invalidNric = "S977F";
        assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void isValidNric() {
        // Empty -> invalid
        assertFalse(Nric.isValidNric(""));
        assertFalse(Nric.isValidNric(" "));

        // First letter not neither 'S', 'T', 'F' nor 'G' -> invalid
        assertFalse(Nric.isValidNric("A9774487F"));

        // Less than 7 numerical digits -> invalid
        assertFalse(Nric.isValidNric("S9487F"));
        assertFalse(Nric.isValidNric("S977448F"));
        assertFalse(Nric.isValidNric("S97744876F"));

        // Last letter is non-alphabet -> invalid
        assertFalse(Nric.isValidNric("S977448$"));
        assertFalse(Nric.isValidNric("S9774488"));
        assertFalse(Nric.isValidNric("S977448 "));

        // First letter non-capitalized -> valid
        assertTrue(Nric.isValidNric("s9774487F"));

        // Last letter is non-capitalized -> valid
        assertTrue(Nric.isValidNric("S9774487f"));
    }

    @Test
    public void equals() {
        // same nric
        assertEquals(new Nric(NRIC_1), new Nric(NRIC_1));

        // different nric
        assertNotEquals(new Nric(NRIC_1), new Nric(NRIC_2));
    }
}
